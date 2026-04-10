package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.model.LocoDataMySQLReport;
import callmezydd.schedulerreport.repo.LocoMySQLReportRepository;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class LocoReportTest {

    @Mock
    private LocoMySQLRepository locoMySQLRepository;

    @Mock
    private LocoMySQLReportRepository locoMySQLReportRepository;

    @InjectMocks
    private LocoReport locoReport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGenerateAndSaveReport_NewData() {
        // Mock data
        LocalDateTime latestReportDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime latestRawDateTime = LocalDateTime.of(2023, 1, 1, 1, 0);

        // Mock the behavior of findTopByOrderByDateTimeDesc for LocoMySQLReportRepository
        LocoDataMySQLReport locoDataMySQLReport = createLocoReport(latestReportDateTime).get(0);
        when(locoMySQLReportRepository.findTopByOrderByDateTimeDesc()).thenReturn(locoDataMySQLReport);

        // Mock the behavior of findTopByOrderByDateTimeDesc for LocoMySQLRepository
        LocoDataMySQL locoDataMySQL = createLocoData(latestRawDateTime).get(0);
        when(locoMySQLRepository.findTopByOrderByDateTimeDesc()).thenReturn(locoDataMySQL);

        // Mock the repository save method
        when(locoMySQLReportRepository.save(Mockito.any(LocoDataMySQLReport.class))).thenReturn(locoDataMySQLReport);

        // Call the method to be tested
        locoReport.generateAndSaveReport();

        // Verify that the save method was called
        verify(locoMySQLReportRepository, times(1)).save(any(LocoDataMySQLReport.class));
    }

    @Test
    void testGenerateAndSaveReport_NoNewData() {
        // Mock data where latestRawDateTime is not after latestReportDateTime
        LocalDateTime latestReportDateTime = LocalDateTime.of(2023, 1, 1, 1, 0);
        LocalDateTime latestRawDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);

        // Mock the behavior of findTopByOrderByDateTimeDesc for LocoMySQLReportRepository
        LocoDataMySQLReport locoDataMySQLReport = createLocoReport(latestReportDateTime).get(0);
        when(locoMySQLReportRepository.findTopByOrderByDateTimeDesc()).thenReturn(locoDataMySQLReport);

        // Mock the behavior of findTopByOrderByDateTimeDesc for LocoMySQLRepository
        LocoDataMySQL locoDataMySQL = createLocoData(latestRawDateTime).get(0);
        when(locoMySQLRepository.findTopByOrderByDateTimeDesc()).thenReturn(locoDataMySQL);

        // Call the method to be tested
        locoReport.generateAndSaveReport();

        // Verify that the save method was not called
        verify(locoMySQLReportRepository, never()).save(any(LocoDataMySQLReport.class));
    }


    private List<LocoDataMySQLReport> createLocoReport(LocalDateTime dateTime) {
        List<LocoDataMySQLReport> locoReportList = new ArrayList<>();
        LocoDataMySQLReport locoReport = new LocoDataMySQLReport();
        locoReport.setDateTime(dateTime);
        locoReportList.add(locoReport);
        return locoReportList;
    }

    private List<LocoDataMySQL> createLocoData(LocalDateTime dateTime) {
        List<LocoDataMySQL> locoDataList = new ArrayList<>();
        LocoDataMySQL locoData = new LocoDataMySQL();
        locoData.setDateTime(dateTime);
        locoDataList.add(locoData);
        return locoDataList;
    }
}
