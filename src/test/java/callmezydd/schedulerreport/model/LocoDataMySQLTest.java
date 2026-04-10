package callmezydd.schedulerreport.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class LocoDataMySQLTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        LocalDateTime currentTime = LocalDateTime.now();

        // Act
        LocoDataMySQL locoDataMySQL = new LocoDataMySQL("123", "TestLoco", "TestDimension", "Active", currentTime);

        // Assert
        assertEquals("123", locoDataMySQL.getLocoCode());
        assertEquals("TestLoco", locoDataMySQL.getLocoName());
        assertEquals("TestDimension", locoDataMySQL.getLocoDimension());
        assertEquals("Active", locoDataMySQL.getLocoStatus());
        assertEquals(currentTime, locoDataMySQL.getDateTime());
    }

    @Test
    void testSetterMethods() {
        // Arrange
        LocoDataMySQL locoDataMySQL = new LocoDataMySQL();

        // Act
        locoDataMySQL.setLocoCode("456");
        locoDataMySQL.setLocoName("AnotherLoco");
        locoDataMySQL.setLocoDimension("AnotherDimension");
        locoDataMySQL.setLocoStatus("Inactive");
        LocalDateTime currentTime = LocalDateTime.now();
        locoDataMySQL.setDateTime(currentTime);

        // Assert
        assertEquals("456", locoDataMySQL.getLocoCode());
        assertEquals("AnotherLoco", locoDataMySQL.getLocoName());
        assertEquals("AnotherDimension", locoDataMySQL.getLocoDimension());
        assertEquals("Inactive", locoDataMySQL.getLocoStatus());
        assertEquals(currentTime, locoDataMySQL.getDateTime());
    }

    @Test
    void testEqualsAndHashCodeForNullFields() {
        // Arrange
        LocoDataMySQL locoDataMySQL1 = new LocoDataMySQL();
        LocoDataMySQL locoDataMySQL2 = new LocoDataMySQL();

        // Act & Assert
        assertEquals(locoDataMySQL1, locoDataMySQL2);
        assertEquals(locoDataMySQL1.hashCode(), locoDataMySQL2.hashCode());
    }

    @Test
    void testNotEqualsForDifferentLocoCodes() {
        // Arrange
        LocoDataMySQL locoDataMySQL1 = new LocoDataMySQL("123", "TestLoco", "TestDimension", "Active", LocalDateTime.now());
        LocoDataMySQL locoDataMySQL2 = new LocoDataMySQL("456", "TestLoco", "TestDimension", "Active", LocalDateTime.now());

        // Act & Assert
        assertNotEquals(locoDataMySQL1, locoDataMySQL2);
        assertNotEquals(locoDataMySQL1.hashCode(), locoDataMySQL2.hashCode());
    }

    @Test
    void testNotEqualsForDifferentLocoNames() {
        // Arrange
        LocoDataMySQL locoDataMySQL1 = new LocoDataMySQL("123", "TestLoco", "TestDimension", "Active", LocalDateTime.now());
        LocoDataMySQL locoDataMySQL2 = new LocoDataMySQL("123", "AnotherLoco", "TestDimension", "Active", LocalDateTime.now());

        // Act & Assert
        assertNotEquals(locoDataMySQL1, locoDataMySQL2);
        assertNotEquals(locoDataMySQL1.hashCode(), locoDataMySQL2.hashCode());
    }

    // Add more test cases as needed...

}
