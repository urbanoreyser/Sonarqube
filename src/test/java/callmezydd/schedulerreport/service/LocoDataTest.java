package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LocoDataTest {

    @Mock
    private LocoMySQLRepository locoMySQLRepository;

    @InjectMocks
    private LocoData locoData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {
        // Mock data
        List<LocoDataMySQL> mockData = new ArrayList<>();
        mockData.add(new LocoDataMySQL(/* provide necessary data */));

        // Mock behavior of the repository
        when(locoMySQLRepository.findAll()).thenReturn(mockData);

        // Call the service method
        List<LocoDataMySQL> result = locoData.findAll();

        // Verify the result
        assertEquals(mockData, result);
    }
}