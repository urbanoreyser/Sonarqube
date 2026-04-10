package callmezydd.schedulerreport.app;

import callmezydd.schedulerreport.model.LocoDataMongo;
import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.service.LocoMigration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class LocoSchedulerTest {

    @Mock
    private LocoMigration locoMigration;

    @InjectMocks
    private LocoScheduler locoScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFetchAndSaveData_ExistingLocoData() {
        // Arrange
        List<LocoDataMongo> locoMongoList = createMockLocoMongoList();
        when(locoMigration.getAllLocoMongoData()).thenReturn(locoMongoList);
        when(locoMigration.existsLocoDataMySQLById(anyString())).thenReturn(true);

        // Act
        locoScheduler.fetchAndSaveData();

        // Assert
        verify(locoMigration, times(0)).saveLocoDataMySQL(any(LocoDataMySQL.class));
        verify(locoMigration, times(locoMongoList.size())).existsLocoDataMySQLById(anyString());
    }

    @Test
    void testFetchAndSaveData_EmptyLocoMongoList() {
        // Arrange
        when(locoMigration.getAllLocoMongoData()).thenReturn(new ArrayList<>());

        // Act
        locoScheduler.fetchAndSaveData();

        // Assert
        verify(locoMigration, times(0)).saveLocoDataMySQL(any(LocoDataMySQL.class));
        verify(locoMigration, times(0)).existsLocoDataMySQLById(anyString());
    }

    @Test
    void testFetchAndSaveData_ValidLocoCode() {
        // Arrange
        List<LocoDataMongo> locoMongoList = createMockLocoMongoListWithValidLocoCode();
        when(locoMigration.getAllLocoMongoData()).thenReturn(locoMongoList);
        when(locoMigration.existsLocoDataMySQLById(anyString())).thenReturn(false);

        // Act
        locoScheduler.fetchAndSaveData();

        // Assert
        for (LocoDataMongo locoMongo : locoMongoList) {
            verify(locoMigration, times(1)).saveLocoDataMySQL(
                    argThat(argument ->
                            locoMongo.getLocoCode().equals(argument.getLocoCode()) &&
                                    locoMongo.getLocoName().equals(argument.getLocoName()) &&
                                    locoMongo.getLocoDimension().equals(argument.getLocoDimension()) &&
                                    locoMongo.getStatus().equals(argument.getLocoStatus()) &&
                                    locoMongo.getTime().equals(argument.getDateTime())
                    )
            );
        }
        verify(locoMigration, times(locoMongoList.size())).existsLocoDataMySQLById(anyString());
    }


    @Test
    void testFetchAndSaveData_NullLocoCode() {
        // Arrange
        List<LocoDataMongo> locoMongoList = createMockLocoMongoListWithNullLocoCode();
        when(locoMigration.getAllLocoMongoData()).thenReturn(locoMongoList);
        when(locoMigration.existsLocoDataMySQLById(anyString())).thenReturn(false);

        // Act
        locoScheduler.fetchAndSaveData();

        // Assert
        verify(locoMigration, times(0)).saveLocoDataMySQL(any(LocoDataMySQL.class));
        verify(locoMigration, times(0)).existsLocoDataMySQLById(anyString());
    }

    @Test
    void testFetchAndSaveData_DuplicateLocoCode() {
        // Arrange
        List<LocoDataMongo> locoMongoList = createMockLocoMongoListWithDuplicateLocoCode();
        when(locoMigration.getAllLocoMongoData()).thenReturn(locoMongoList);
        when(locoMigration.existsLocoDataMySQLById(anyString())).thenReturn(true);

        // Act
        locoScheduler.fetchAndSaveData();

        // Assert
        verify(locoMigration, times(0)).saveLocoDataMySQL(any(LocoDataMySQL.class));
        verify(locoMigration, times(locoMongoList.size())).existsLocoDataMySQLById(anyString());
    }


    private List<LocoDataMongo> createMockLocoMongoList() {
        List<LocoDataMongo> locoMongoList = new ArrayList<>();
        locoMongoList.add(new LocoDataMongo("1","1", "Loco1", "Dimension1", "Good", LocalDateTime.now()));
        locoMongoList.add(new LocoDataMongo("2","2", "Loco2", "Dimension2", "Poor", LocalDateTime.now()));
        return locoMongoList;
    }

    private List<LocoDataMongo> createMockLocoMongoListWithNullLocoCode() {
        List<LocoDataMongo> locoMongoList = new ArrayList<>();
        locoMongoList.add(new LocoDataMongo("1",null, "Loco1", "Dimension1", "Good", LocalDateTime.now()));
        locoMongoList.add(new LocoDataMongo("2",null, "Loco2", "Dimension2", "Poor", LocalDateTime.now()));
        return locoMongoList;
    }

    private List<LocoDataMongo> createMockLocoMongoListWithDuplicateLocoCode() {
        List<LocoDataMongo> locoMongoList = new ArrayList<>();
        locoMongoList.add(new LocoDataMongo("1","1", "Loco1", "Dimension1", "Good", LocalDateTime.now()));
        locoMongoList.add(new LocoDataMongo("2","1", "Loco2", "Dimension2", "Poor", LocalDateTime.now()));
        return locoMongoList;
    }

    private List<LocoDataMongo> createMockLocoMongoListWithValidLocoCode() {
        List<LocoDataMongo> locoMongoList = new ArrayList<>();
        LocoDataMongo locoDataMongo = new LocoDataMongo();
        locoDataMongo.setLocoCode("validLocoCode");
        locoDataMongo.setLocoName("validLocoName");
        locoDataMongo.setLocoDimension("validLocoDimension");
        locoDataMongo.setStatus("validStatus");
        locoDataMongo.setTime(LocalDateTime.now());
        locoMongoList.add(locoDataMongo);
        return locoMongoList;
    }


    // Add more test cases as needed...

}
