package com.example.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LineSegmentTest {

    @Test
    void testLineSegmentCreationAndAccessors() {
        Point p1 = new Point(0.0, 0.0);
        Point p2 = new Point(1.0, 1.0);
        LineSegment segment = new LineSegment(p1, p2);

        assertEquals(p1, segment.p1(), "P1 should match");
        assertEquals(p2, segment.p2(), "P2 should match");
    }

    @Test
    void testToString() {
        Point p1 = new Point(0.0, 0.0);
        Point p2 = new Point(1.0, 1.0);
        LineSegment segment = new LineSegment(p1, p2);

        String expected = String.format("Segment[%s to %s]", p1, p2);
        assertEquals(expected, segment.toString(), "ToString should match custom format");
    }
    
    @Test
    void testEqualsAndHashCode() {
        Point p1 = new Point(0.0, 0.0);
        Point p2 = new Point(1.0, 1.0);
        
        LineSegment s1 = new LineSegment(p1, p2);
        LineSegment s2 = new LineSegment(new Point(0.0, 0.0), new Point(1.0, 1.0));
        LineSegment s3 = new LineSegment(p2, p1); // Reversed points

        assertEquals(s1, s2, "Segments with same points should be equal");
        assertNotEquals(s1, s3, "Segments with swapped points are not equal records (strict equality)");
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
