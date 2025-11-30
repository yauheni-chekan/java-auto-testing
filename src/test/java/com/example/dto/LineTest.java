package com.example.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void testLineCreationAndAccessors() {
        Line line = new Line(-3.0, 5.0, -2.0);
        assertEquals(-3.0, line.a());
        assertEquals(5.0, line.b());
        assertEquals(-2.0, line.c());
    }

    @Test
    void testToString() {
        Line line = new Line(1.0, 2.0, 3.0);
        String str = line.toString();
        assertTrue(str.contains("a=1.0"));
        assertTrue(str.contains("b=2.0"));
        assertTrue(str.contains("c=3.0"));
    }
}
