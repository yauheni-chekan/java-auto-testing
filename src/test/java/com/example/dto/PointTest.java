package com.example.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testPointCreationAndAccessors() {
        Point point = new Point(1.0, 2.0);
        assertEquals(1.0, point.x(), "X coordinate should be 1.0");
        assertEquals(2.0, point.y(), "Y coordinate should be 2.0");
    }

    @Test
    void testToString() {
        Point point = new Point(-5.0, 3.0);
        // Use String.format to ensure the test expects the string formatted 
        // with the same system locale as the implementation uses.
        String expected = String.format("(%.2f, %.2f)", -5.0, 3.0);
        assertEquals(expected, point.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Point p1 = new Point(1.0, 1.0);
        Point p2 = new Point(1.0, 1.0);
        Point p3 = new Point(2.0, 2.0);

        assertEquals(p1, p2, "Points with same coordinates should be equal");
        assertNotEquals(p1, p3, "Points with different coordinates should not be equal");
        assertEquals(p1.hashCode(), p2.hashCode(), "Equal points should have same hash code");
    }
}
