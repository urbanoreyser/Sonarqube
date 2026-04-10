package callmezydd.schedulerreport.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class LocoDataMongoTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        LocalDateTime currentTime = LocalDateTime.now();

        // Act
        LocoDataMongo locoDataMongo = new LocoDataMongo("1", "123", "TestLoco", "TestDimension", "Active", currentTime);

        // Assert
        assertEquals("1", locoDataMongo.get_id());
        assertEquals("123", locoDataMongo.getLocoCode());
        assertEquals("TestLoco", locoDataMongo.getLocoName());
        assertEquals("TestDimension", locoDataMongo.getLocoDimension());
        assertEquals("Active", locoDataMongo.getStatus());
        assertEquals(currentTime, locoDataMongo.getTime());
    }

    @Test
    void testSetterMethods() {
        // Arrange
        LocoDataMongo locoDataMongo = new LocoDataMongo();

        // Act
        locoDataMongo.set_id("2");
        locoDataMongo.setLocoCode("456");
        locoDataMongo.setLocoName("AnotherLoco");
        locoDataMongo.setLocoDimension("AnotherDimension");
        locoDataMongo.setStatus("Inactive");
        LocalDateTime currentTime = LocalDateTime.now();
        locoDataMongo.setTime(currentTime);

        // Assert
        assertEquals("2", locoDataMongo.get_id());
        assertEquals("456", locoDataMongo.getLocoCode());
        assertEquals("AnotherLoco", locoDataMongo.getLocoName());
        assertEquals("AnotherDimension", locoDataMongo.getLocoDimension());
        assertEquals("Inactive", locoDataMongo.getStatus());
        assertEquals(currentTime, locoDataMongo.getTime());
    }

    @Test
    void testEqualsAndHashCodeForNullFields() {
        // Arrange
        LocoDataMongo locoDataMongo1 = new LocoDataMongo();
        LocoDataMongo locoDataMongo2 = new LocoDataMongo();

        // Act & Assert
        assertEquals(locoDataMongo1, locoDataMongo2);
        assertEquals(locoDataMongo1.hashCode(), locoDataMongo2.hashCode());
    }

    @Test
    void testNotEqualsForDifferentIds() {
        // Arrange
        LocoDataMongo locoDataMongo1 = new LocoDataMongo("1", "123", "TestLoco", "TestDimension", "Active", LocalDateTime.now());
        LocoDataMongo locoDataMongo2 = new LocoDataMongo("2", "123", "TestLoco", "TestDimension", "Active", LocalDateTime.now());

        // Act & Assert
        assertNotEquals(locoDataMongo1, locoDataMongo2);
        assertNotEquals(locoDataMongo1.hashCode(), locoDataMongo2.hashCode());
    }

    @Test
    void testNotEqualsForDifferentLocoCodes() {
        // Arrange
        LocoDataMongo locoDataMongo1 = new LocoDataMongo("1", "123", "TestLoco", "TestDimension", "Active", LocalDateTime.now());
        LocoDataMongo locoDataMongo2 = new LocoDataMongo("1", "456", "TestLoco", "TestDimension", "Active", LocalDateTime.now());

        // Act & Assert
        assertNotEquals(locoDataMongo1, locoDataMongo2);
        assertNotEquals(locoDataMongo1.hashCode(), locoDataMongo2.hashCode());
    }

    // Add more test cases as needed...

}