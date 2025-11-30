package com.example.logic;

import com.example.dto.Line;
import com.example.dto.LineSegment;
import com.example.dto.Point;
import com.example.dto.RelativePosition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RelativePositionParameterizedTest {

    // The task line: -3x + 5y - 2 = 0
    private final Line line = new Line(-3.0, 5.0, -2.0);

    @ParameterizedTest(name = "{index}: P1({0},{1}), P2({2},{3}) -> {4}, Perp={5}")
    @CsvSource({
        // 1. Both above
        "0, 1, 0, 5, NO_INTERSECTION, false",
        
        // 2. Both below
        "0, 0, 5, 0, NO_INTERSECTION, false",
        
        // 3. Crosses (above -> below)
        "0, 1, 5, 0, ONE_INTERSECTION, false",
        
        // 4. Crosses (below -> above)
        "0, 0, 0, 1, ONE_INTERSECTION, false",
        
        // 5. P1 on line
        "1, 1, 0, 5, ONE_ENDPOINT_ON_LINE, false",
        
        // 6. P2 on line
        "0, 0, 1, 1, ONE_ENDPOINT_ON_LINE, false",
        
        // 7. Both on line
        "1, 1, 6, 4, SEGMENT_ON_LINE, false",
        
        // 8. Parallel above
        // P1(0,1), P2(5,4) -> Dir(5,3) same as line
        "0, 1, 5, 4, PARALLEL, false",
        
        // 9. Parallel below
        // P1(0,0), P2(5,3) -> Dir(5,3) same as line
        "0, 0, 5, 3, PARALLEL, false",
        
        // 10. Intersection + Perpendicular
        // P1(0,5), P2(3,0) -> Dir(3,-5). Line Dir(5,3). Dot: 15-15=0.
        "0, 5, 3, 0, ONE_INTERSECTION, true",
        
        // 11. Intersection + Not Perpendicular
        // P1(0,1), P2(2,0). Just an arbitrary crossing.
        "0, 1, 2, 0, ONE_INTERSECTION, false"
    })
    void testRelativePositionAndPerpendicularity(
            double x1, double y1, double x2, double y2, 
            RelativePosition expectedPosition, boolean expectedPerpendicular) {
        
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        LineSegment segment = new LineSegment(p1, p2);

        // Check Relative Position
        RelativePosition actualPosition = GeometryUtils.getRelativePosition(segment, line);
        assertEquals(expectedPosition, actualPosition, 
            () -> String.format("Failed position for segment %s", segment));

        // Check Perpendicularity
        boolean actualPerpendicular = GeometryUtils.isPerpendicularTo(segment, line);
        assertEquals(expectedPerpendicular, actualPerpendicular, 
            () -> String.format("Failed perpendicularity check for segment %s", segment));
    }
}
