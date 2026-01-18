package com.example.logic;

import com.example.dto.Line;
import com.example.dto.LineSegment;
import com.example.dto.Point;
import com.example.dto.RelativePosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for geometric calculations involving Lines, Points, and LineSegments.
 * Logic extracted from domain entities to separate data from behavior.
 */
public class GeometryUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(GeometryUtils.class);
    private static final double EPSILON = 1e-9;

    // --- Line & Point Operations ---

    /**
     * Evaluates the line equation for a given point.
     * f(x, y) = Ax + By + C
     */
    public static double evaluate(Line line, Point point) {
        double result = line.a() * point.x() + line.b() * point.y() + line.c();
        logger.debug("Evaluating point {} on line: f(x,y) = {}", point, result);
        return result;
    }

    /**
     * Checks if a point lies on the line (within tolerance).
     */
    public static boolean isPointOnLine(Line line, Point point) {
        double value = Math.abs(evaluate(line, point));
        boolean onLine = value < EPSILON;
        logger.debug("Point {} is on line: {}", point, onLine);
        return onLine;
    }

    /**
     * Gets the normal vector of the line (A, B).
     */
    public static Point getNormalVector(Line line) {
        return new Point(line.a(), line.b());
    }

    /**
     * Gets the direction vector of the line (B, -A).
     */
    public static Point getDirectionVector(Line line) {
        return new Point(line.b(), -line.a());
    }

    /**
     * Gets the line equation as a string.
     */
    public static String getEquationString(Line line) {
        return String.format("%.0fx %+.0fy %+.0f = 0", line.a(), line.b(), line.c());
    }

    // --- LineSegment Operations ---

    /**
     * Checks if the segment is degenerate (start and end points are the same).
     */
    public static boolean isDegenerate(LineSegment segment) {
        double dx = segment.p2().x() - segment.p1().x();
        double dy = segment.p2().y() - segment.p1().y();
        return Math.abs(dx) < EPSILON && Math.abs(dy) < EPSILON;
    }

    /**
     * Calculates the direction vector of the segment.
     * Returns (0,0) if degenerate.
     */
    public static Point getDirectionVector(LineSegment segment) {
        double dx = segment.p2().x() - segment.p1().x();
        double dy = segment.p2().y() - segment.p1().y();
        return new Point(dx, dy);
    }

    /**
     * Gets the line equation in general form Ax + By + C = 0 for the line containing the segment.
     * Returns null if segment is degenerate.
     */
    public static double[] getLineEquation(LineSegment segment) {
        if (isDegenerate(segment)) {
            logger.debug("Segment {} is degenerate (point), no line equation", segment);
            return null;
        }

        double dx = segment.p2().x() - segment.p1().x();
        double dy = segment.p2().y() - segment.p1().y();
        
        // Handle vertical line (dx = 0)
        if (Math.abs(dx) < EPSILON) {
            return new double[]{1.0, 0.0, -segment.p1().x()};
        }
        
        // Handle horizontal line (dy = 0)
        if (Math.abs(dy) < EPSILON) {
            return new double[]{0.0, 1.0, -segment.p1().y()};
        }
        
        // General case: use normal vector approach
        double A = -dy;
        double B = dx;
        double C = -(A * segment.p1().x() + B * segment.p1().y());
        
        logger.debug("Line equation for segment {}: A={}, B={}, C={}", segment, A, B, C);
        return new double[]{A, B, C};
    }

    /**
     * Gets the line equation of the segment as a formatted string.
     */
    public static String getLineEquationString(LineSegment segment) {
        if (isDegenerate(segment)) {
            return "Degenerate (Point)";
        }
        double[] coeffs = getLineEquation(segment);
        return String.format("%.0fx %+.0fy %+.0f = 0", coeffs[0], coeffs[1], coeffs[2]);
    }

    /**
     * Checks if the segment is parallel to the given line.
     * Returns false if degenerate.
     */
    public static boolean isParallelTo(LineSegment segment, Line line) {
        if (isDegenerate(segment)) return false;

        Point lineDir = getDirectionVector(line);
        Point segmentDir = getDirectionVector(segment);
        
        double crossProduct = lineDir.x() * segmentDir.y() - lineDir.y() * segmentDir.x();
        boolean isParallel = Math.abs(crossProduct) < EPSILON;
        
        logger.debug("Segment {} is parallel to line: {} (cross product: {})", 
                     segment, isParallel, crossProduct);
        return isParallel;
    }

    /**
     * Checks if the segment is perpendicular to the given line.
     * Returns false if degenerate.
     */
    public static boolean isPerpendicularTo(LineSegment segment, Line line) {
        if (isDegenerate(segment)) return false;

        Point lineDir = getDirectionVector(line);
        Point segmentDir = getDirectionVector(segment);
        
        double dotProduct = lineDir.x() * segmentDir.x() + lineDir.y() * segmentDir.y();
        boolean isPerpendicular = Math.abs(dotProduct) < EPSILON;
        
        logger.debug("Segment {} is perpendicular to line: {} (dot product: {})", 
                     segment, isPerpendicular, dotProduct);
        return isPerpendicular;
    }

    /**
     * Finds the intersection point between the segment and the line.
     * If degenerate, returns the point itself if it's on the line.
     */
    public static Point findIntersectionPoint(LineSegment segment, Line line) {
        if (isDegenerate(segment)) {
            return isPointOnLine(line, segment.p1()) ? segment.p1() : null;
        }

        double dx = segment.p2().x() - segment.p1().x();
        double dy = segment.p2().y() - segment.p1().y();
        
        double f1 = evaluate(line, segment.p1());
        double denominator = line.a() * dx + line.b() * dy;
        
        if (Math.abs(denominator) < EPSILON) {
            logger.debug("Segment is parallel to line, no unique intersection point");
            return null;
        }
        
        double t = -f1 / denominator;
        logger.debug("Intersection parameter t = {}", t);
        
        if (t < 0 || t > 1) {
            logger.debug("Intersection point is outside segment bounds");
            return null;
        }
        
        double x = segment.p1().x() + t * dx;
        double y = segment.p1().y() + t * dy;
        Point intersection = new Point(x, y);
        
        logger.debug("Intersection point found: {}", intersection);
        return intersection;
    }

    /**
     * Determines the relative position of the segment with respect to the line.
     */
    public static RelativePosition getRelativePosition(LineSegment segment, Line line) {
        if (isDegenerate(segment)) {
            boolean onLine = isPointOnLine(line, segment.p1());
            logger.debug("Segment is degenerate. On line: {}", onLine);
            return onLine ? RelativePosition.SEGMENT_ON_LINE : RelativePosition.NO_INTERSECTION;
        }

        double f1 = evaluate(line, segment.p1());
        double f2 = evaluate(line, segment.p2());
        
        boolean p1OnLine = Math.abs(f1) < EPSILON;
        boolean p2OnLine = Math.abs(f2) < EPSILON;
        
        logger.debug("Evaluating segment endpoints: f(P1) = {}, f(P2) = {}", f1, f2);
        
        if (p1OnLine && p2OnLine) return RelativePosition.SEGMENT_ON_LINE;
        if (p1OnLine || p2OnLine) return RelativePosition.ONE_ENDPOINT_ON_LINE;
        if (isParallelTo(segment, line)) return RelativePosition.PARALLEL;
        if (f1 * f2 < 0) return RelativePosition.ONE_INTERSECTION;
        
        return RelativePosition.NO_INTERSECTION;
    }
}
