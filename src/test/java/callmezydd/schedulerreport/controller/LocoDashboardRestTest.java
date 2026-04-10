package callmezydd.schedulerreport.controller;

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
import static org.mockito.Mockito.*;

class LocoDashboardRestTest {

    @Mock
    private LocoMySQLRepository locoMySQLRepository;

    @InjectMocks
    private LocoDashboardRest locoDashboardRest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetDataLoco_AllParametersNull() {
        // Arrange
        List<LocoDataMySQL> expectedData = new ArrayList<>();
        when(locoMySQLRepository.findAll()).thenReturn(expectedData);

        // Act
        List<LocoDataMySQL> result = locoDashboardRest.getDataLoco(null, null, null, null);

        // Assert
        assertEquals(expectedData, result);
        verify(locoMySQLRepository, times(1)).findAll();
        verify(locoMySQLRepository, times(0)).findByYearAndMonthAndDay(anyInt(), anyInt(), anyInt());
        verify(locoMySQLRepository, times(0)).findByLocoStatus(anyString());
    }

    @Test
    void testGetDataLoco_WithStatusParameter() {
        // Arrange
        String status = "Good";
        List<LocoDataMySQL> expectedData = new ArrayList<>();
        when(locoMySQLRepository.findByLocoStatus(status)).thenReturn(expectedData);

        // Act
        List<LocoDataMySQL> result = locoDashboardRest.getDataLoco(status, null, null, null);

        // Assert
        assertEquals(expectedData, result);
        verify(locoMySQLRepository, times(0)).findAll();
        verify(locoMySQLRepository, times(0)).findByYearAndMonthAndDay(anyInt(), anyInt(), anyInt());
        verify(locoMySQLRepository, times(1)).findByLocoStatus(status);
    }

    @Test
    void testGetDataLoco_WithDateParameters() {
        // Arrange
        Integer year = 2023;
        Integer month = 11;
        Integer day = 30;
        List<LocoDataMySQL> expectedData = new ArrayList<>();
        when(locoMySQLRepository.findByYearAndMonthAndDay(year, month, day)).thenReturn(expectedData);

        // Act
        List<LocoDataMySQL> result = locoDashboardRest.getDataLoco(null, year, month, day);

        // Assert
        assertEquals(expectedData, result);
        verify(locoMySQLRepository, times(0)).findAll();
        verify(locoMySQLRepository, times(1)).findByYearAndMonthAndDay(year, month, day);
        verify(locoMySQLRepository, times(0)).findByLocoStatus(anyString());
    }

    // Add more test cases for other scenarios...
}
