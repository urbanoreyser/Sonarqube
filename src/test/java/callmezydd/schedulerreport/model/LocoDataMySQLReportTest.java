package callmezydd.schedulerreport.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class LocoDataMySQLReportTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        LocalDateTime currentTime = LocalDateTime.now();

        // Act
        LocoDataMySQLReport locoDataMySQLReport = new LocoDataMySQLReport(1, 10, 5, 3, 2, currentTime, "TopLoco");

        // Assert
        assertEquals(1, locoDataMySQLReport.getId());
        assertEquals(10, locoDataMySQLReport.getTotalLoco());
        assertEquals(5, locoDataMySQLReport.getLocoGood());
        assertEquals(3, locoDataMySQLReport.getLocoPoor());
        assertEquals(2, locoDataMySQLReport.getLocoExcellent());
        assertEquals(currentTime, locoDataMySQLReport.getDateTime());
        assertEquals("TopLoco", locoDataMySQLReport.getTopLocoAll());
    }

    @Test
    void testSetterMethods() {
        // Arrange
        LocoDataMySQLReport locoDataMySQLReport = new LocoDataMySQLReport();

        // Act
        locoDataMySQLReport.setId(2);
        locoDataMySQLReport.setTotalLoco(15);
        locoDataMySQLReport.setLocoGood(8);
        locoDataMySQLReport.setLocoPoor(4);
        locoDataMySQLReport.setLocoExcellent(3);
        LocalDateTime currentTime = LocalDateTime.now();
        locoDataMySQLReport.setDateTime(currentTime);
        locoDataMySQLReport.setTopLocoAll("AnotherTopLoco");

        // Assert
        assertEquals(2, locoDataMySQLReport.getId());
        assertEquals(15, locoDataMySQLReport.getTotalLoco());
        assertEquals(8, locoDataMySQLReport.getLocoGood());
        assertEquals(4, locoDataMySQLReport.getLocoPoor());
        assertEquals(3, locoDataMySQLReport.getLocoExcellent());
        assertEquals(currentTime, locoDataMySQLReport.getDateTime());
        assertEquals("AnotherTopLoco", locoDataMySQLReport.getTopLocoAll());
    }

    @Test
    void testEqualsAndHashCodeForNullFields() {
        // Arrange
        LocoDataMySQLReport locoDataMySQLReport1 = new LocoDataMySQLReport();
        LocoDataMySQLReport locoDataMySQLReport2 = new LocoDataMySQLReport();

        // Act & Assert
        assertEquals(locoDataMySQLReport1, locoDataMySQLReport2);
        assertEquals(locoDataMySQLReport1.hashCode(), locoDataMySQLReport2.hashCode());
    }

    @Test
    void testNotEqualsForDifferentIds() {
        // Arrange
        LocoDataMySQLReport locoDataMySQLReport1 = new LocoDataMySQLReport(1, 10, 5, 3, 2, LocalDateTime.now(), "TopLoco");
        LocoDataMySQLReport locoDataMySQLReport2 = new LocoDataMySQLReport(2, 10, 5, 3, 2, LocalDateTime.now(), "TopLoco");

        // Act & Assert
        assertNotEquals(locoDataMySQLReport1, locoDataMySQLReport2);
        assertNotEquals(locoDataMySQLReport1.hashCode(), locoDataMySQLReport2.hashCode());
    }

    @Test
    void testNotEqualsForDifferentTopLocoAll() {
        // Arrange
        LocoDataMySQLReport locoDataMySQLReport1 = new LocoDataMySQLReport(1, 10, 5, 3, 2, LocalDateTime.now(), "TopLoco1");
        LocoDataMySQLReport locoDataMySQLReport2 = new LocoDataMySQLReport(1, 10, 5, 3, 2, LocalDateTime.now(), "TopLoco2");

        // Act & Assert
        assertNotEquals(locoDataMySQLReport1, locoDataMySQLReport2);
        assertNotEquals(locoDataMySQLReport1.hashCode(), locoDataMySQLReport2.hashCode());
    }

    // Add more test cases as needed...

}
