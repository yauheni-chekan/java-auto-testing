package com.example.logic;

import com.example.dto.Line;
import com.example.dto.LineSegment;
import com.example.dto.Point;
import com.example.dto.RelativePosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeometryUtilsTest {

    // The standard line for this task: -3x + 5y - 2 = 0
    private Line taskLine;
    private static final double EPSILON = 1e-9;

    @BeforeEach
    void setUp() {
        taskLine = new Line(-3.0, 5.0, -2.0);
    }

    // --- Point & Line Operations ---

    @Test
    void testEvaluate() {
        // Point on line: (1, 1) -> -3(1) + 5(1) - 2 = 0
        assertEquals(0.0, GeometryUtils.evaluate(taskLine, new Point(1, 1)), EPSILON);

        // Point above line: (0, 1) -> -3(0) + 5(1) - 2 = 3
        assertEquals(3.0, GeometryUtils.evaluate(taskLine, new Point(0, 1)), EPSILON);

        // Point below line: (0, 0) -> -3(0) + 5(0) - 2 = -2
        assertEquals(-2.0, GeometryUtils.evaluate(taskLine, new Point(0, 0)), EPSILON);
    }

    @Test
    void testIsPointOnLine() {
        assertTrue(GeometryUtils.isPointOnLine(taskLine, new Point(1, 1)));
        assertFalse(GeometryUtils.isPointOnLine(taskLine, new Point(0, 0)));
    }

    @Test
    void testVectors() {
        // Normal vector (A, B) -> (-3, 5)
        Point normal = GeometryUtils.getNormalVector(taskLine);
        assertEquals(-3.0, normal.x(), EPSILON);
        assertEquals(5.0, normal.y(), EPSILON);

        // Direction vector (B, -A) -> (5, 3)
        Point direction = GeometryUtils.getDirectionVector(taskLine);
        assertEquals(5.0, direction.x(), EPSILON);
        assertEquals(3.0, direction.y(), EPSILON);
    }

    @Test
    void testGetEquationString() {
        String eq = GeometryUtils.getEquationString(taskLine);
        // "%.0fx %+.0fy %+.0f = 0" -> "-3x +5y -2 = 0"
        assertEquals("-3x +5y -2 = 0", eq);
    }

    // --- LineSegment Operations ---

    @Test
    void testIsDegenerate() {
        LineSegment pointSegment = new LineSegment(new Point(1, 1), new Point(1, 1));
        assertTrue(GeometryUtils.isDegenerate(pointSegment));

        LineSegment realSegment = new LineSegment(new Point(0, 0), new Point(1, 1));
        assertFalse(GeometryUtils.isDegenerate(realSegment));
    }

    @Test
    void testGetSegmentDirectionVector() {
        LineSegment segment = new LineSegment(new Point(0, 0), new Point(3, 4));
        Point dir = GeometryUtils.getDirectionVector(segment);
        assertEquals(3.0, dir.x(), EPSILON);
        assertEquals(4.0, dir.y(), EPSILON);
    }

    @Test
    void testGetLineEquationForSegment() {
        // Horizontal segment on y=5 -> 0x + 1y - 5 = 0
        LineSegment horiz = new LineSegment(new Point(0, 5), new Point(10, 5));
        double[] coeffsH = GeometryUtils.getLineEquation(horiz);
        assertNotNull(coeffsH);
        assertEquals(0.0, coeffsH[0], EPSILON);
        assertEquals(1.0, coeffsH[1], EPSILON);
        assertEquals(-5.0, coeffsH[2], EPSILON);

        // Vertical segment on x=3 -> 1x + 0y - 3 = 0
        LineSegment vert = new LineSegment(new Point(3, 0), new Point(3, 10));
        double[] coeffsV = GeometryUtils.getLineEquation(vert);
        assertNotNull(coeffsV);
        assertEquals(1.0, coeffsV[0], EPSILON);
        assertEquals(0.0, coeffsV[1], EPSILON);
        assertEquals(-3.0, coeffsV[2], EPSILON);

        // Degenerate
        assertNull(GeometryUtils.getLineEquation(new LineSegment(new Point(1,1), new Point(1,1))));
    }

    @Test
    void testIsParallelTo() {
        // Task line direction is (5, 3)
        // Parallel segment: (0,0) to (5,3)
        LineSegment parallel = new LineSegment(new Point(0, 0), new Point(5, 3));
        assertTrue(GeometryUtils.isParallelTo(parallel, taskLine));

        // Not parallel: (0,0) to (1,0)
        LineSegment notParallel = new LineSegment(new Point(0, 0), new Point(1, 0));
        assertFalse(GeometryUtils.isParallelTo(notParallel, taskLine));
    }

    @Test
    void testIsPerpendicularTo() {
        // Task line normal is (-3, 5), direction (5, 3)
        // Perpendicular direction should be parallel to normal (-3, 5) or (3, -5)
        
        // Segment (0, 5) to (3, 0) -> direction (3, -5)
        LineSegment perp = new LineSegment(new Point(0, 5), new Point(3, 0));
        assertTrue(GeometryUtils.isPerpendicularTo(perp, taskLine));

        // Not perpendicular
        LineSegment notPerp = new LineSegment(new Point(0, 0), new Point(5, 3)); // Parallel actually
        assertFalse(GeometryUtils.isPerpendicularTo(notPerp, taskLine));
    }

    @Test
    void testFindIntersectionPoint() {
        // Line: -3x + 5y - 2 = 0
        // Segment crossing it: (0, 0) to (0, 1)
        // At x=0, line y = 2/5 = 0.4
        // Segment is on x=0 line from y=0 to y=1. Intersection should be (0, 0.4)
        
        LineSegment crossing = new LineSegment(new Point(0, 0), new Point(0, 1));
        Point intersection = GeometryUtils.findIntersectionPoint(crossing, taskLine);
        
        assertNotNull(intersection);
        assertEquals(0.0, intersection.x(), EPSILON);
        assertEquals(0.4, intersection.y(), EPSILON);

        // Parallel segment (no intersection)
        LineSegment parallel = new LineSegment(new Point(0, 1), new Point(5, 4));
        assertNull(GeometryUtils.findIntersectionPoint(parallel, taskLine));
        
        // Segment not reaching line (short segment)
        // Line intersection at (0, 0.4). Segment (0, 0) to (0, 0.2)
        LineSegment shortSeg = new LineSegment(new Point(0, 0), new Point(0, 0.2));
        assertNull(GeometryUtils.findIntersectionPoint(shortSeg, taskLine));
    }
    
    @Test
    void testRelativePositionBasic() {
        // Just a few basic checks, the parameterized test covers the full matrix
        
        // Parallel
        LineSegment parallel = new LineSegment(new Point(0, 1), new Point(5, 4));
        assertEquals(RelativePosition.PARALLEL, GeometryUtils.getRelativePosition(parallel, taskLine));
        
        // Segment on line
        LineSegment onLine = new LineSegment(new Point(1, 1), new Point(6, 4));
        assertEquals(RelativePosition.SEGMENT_ON_LINE, GeometryUtils.getRelativePosition(onLine, taskLine));
    }
}
