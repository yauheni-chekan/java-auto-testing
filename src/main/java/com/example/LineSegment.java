package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a line segment defined by two endpoints.
 */
public class LineSegment {
    private static final Logger logger = LoggerFactory.getLogger(LineSegment.class);
    
    private final Point p1;
    private final Point p2;
    private static final double EPSILON = 1e-9;

    public LineSegment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    /**
     * Calculates the direction vector of the segment.
     * 
     * @return The direction vector as a Point (dx, dy)
     */
    public Point getDirectionVector() {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return new Point(dx, dy);
    }

    /**
     * Gets the line equation in general form Ax + By + C = 0
     * for the line containing this segment.
     * 
     * @return An array [A, B, C] representing the line equation coefficients
     */
    public double[] getLineEquation() {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        
        // Handle vertical line (dx = 0)
        if (Math.abs(dx) < EPSILON) {
            // Vertical line: x = x1, so equation is x - x1 = 0
            // Which is: 1*x + 0*y - x1 = 0
            return new double[]{1.0, 0.0, -p1.getX()};
        }
        
        // Handle horizontal line (dy = 0)
        if (Math.abs(dy) < EPSILON) {
            // Horizontal line: y = y1, so equation is y - y1 = 0
            // Which is: 0*x + 1*y - y1 = 0
            return new double[]{0.0, 1.0, -p1.getY()};
        }
        
        // General case: use normal vector approach
        // Normal vector to direction (dx, dy) is (-dy, dx)
        double A = -dy;
        double B = dx;
        
        // Calculate C using point P1: C = -(A*x1 + B*y1)
        double C = -(A * p1.getX() + B * p1.getY());
        
        logger.debug("Line equation for segment {}: A={}, B={}, C={}", this, A, B, C);
        return new double[]{A, B, C};
    }

    /**
     * Gets the line equation as a formatted string in the form Ax + By + C = 0.
     * 
     * @return String representation of the line equation
     */
    public String getLineEquationString() {
        double[] coeffs = getLineEquation();
        double A = coeffs[0];
        double B = coeffs[1];
        double C = coeffs[2];
        
        StringBuilder sb = new StringBuilder();
        
        // Format A coefficient
        if (Math.abs(A) < EPSILON) {
            // A is zero, skip x term
        } else if (Math.abs(A - 1.0) < EPSILON) {
            sb.append("x");
        } else if (Math.abs(A + 1.0) < EPSILON) {
            sb.append("-x");
        } else {
            sb.append(String.format("%.2fx", A));
        }
        
        // Format B coefficient
        if (Math.abs(B) >= EPSILON) {
            if (sb.length() > 0) {
                sb.append(B > 0 ? " + " : " ");
            }
            if (Math.abs(B - 1.0) < EPSILON) {
                sb.append("y");
            } else if (Math.abs(B + 1.0) < EPSILON) {
                sb.append("-y");
            } else {
                sb.append(String.format("%.2fy", B));
            }
        }
        
        // Format C coefficient
        if (Math.abs(C) >= EPSILON) {
            if (sb.length() > 0) {
                sb.append(C > 0 ? " + " : " ");
            }
            sb.append(String.format("%.2f", C));
        } else if (sb.length() == 0) {
            // Both A and B are zero, which shouldn't happen, but handle it
            sb.append("0");
        }
        
        sb.append(" = 0");
        return sb.toString();
    }

    /**
     * Checks if the segment is parallel to the given line.
     * Two lines are parallel if their direction vectors are scalar multiples.
     * 
     * @param line The line to check against
     * @return true if the segment is parallel to the line
     */
    public boolean isParallelTo(Line line) {
        Point lineDir = line.getDirectionVector();
        Point segmentDir = getDirectionVector();
        
        // Cross product: (lineDir.x, lineDir.y) × (segmentDir.x, segmentDir.y)
        // = lineDir.x * segmentDir.y - lineDir.y * segmentDir.x
        double crossProduct = lineDir.getX() * segmentDir.getY() - lineDir.getY() * segmentDir.getX();
        
        boolean isParallel = Math.abs(crossProduct) < EPSILON;
        logger.debug("Segment {} is parallel to line: {} (cross product: {})", 
                     this, isParallel, crossProduct);
        return isParallel;
    }

    /**
     * Checks if the segment is perpendicular to the given line.
     * A segment is perpendicular to a line if its direction vector is parallel to the line's normal vector.
     * 
     * @param line The line to check against
     * @return true if the segment is perpendicular to the line
     */
    public boolean isPerpendicularTo(Line line) {
        Point lineNormal = line.getNormalVector();
        Point segmentDir = getDirectionVector();
        
        // Dot product: (lineNormal.x, lineNormal.y) · (segmentDir.x, segmentDir.y)
        double dotProduct = lineNormal.getX() * segmentDir.getX() + lineNormal.getY() * segmentDir.getY();
        
        boolean isPerpendicular = Math.abs(dotProduct) < EPSILON;
        logger.debug("Segment {} is perpendicular to line: {} (dot product: {})", 
                     this, isPerpendicular, dotProduct);
        return isPerpendicular;
    }

    /**
     * Checks if an endpoint lies on the given line.
     * 
     * @param line The line to check against
     * @param endpoint The endpoint to check (should be p1 or p2)
     * @return true if the endpoint lies on the line
     */
    public boolean isEndpointOnLine(Line line, Point endpoint) {
        return line.isPointOnLine(endpoint);
    }

    /**
     * Finds the intersection point between the segment and the line, if it exists.
     * 
     * @param line The line to intersect with
     * @return The intersection point, or null if no intersection exists
     */
    public Point findIntersectionPoint(Line line) {
        // Parameterize the segment: P(t) = P1 + t(P2 - P1), t ∈ [0,1]
        // Substitute into line equation: A(x1 + t*dx) + B(y1 + t*dy) + C = 0
        // Solve for t: t = -(A*x1 + B*y1 + C) / (A*dx + B*dy)
        
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        
        // Evaluate line equation at P1
        double f1 = line.evaluate(p1);
        
        // Denominator: A*dx + B*dy
        double denominator = -3.0 * dx + 5.0 * dy;
        
        // If denominator is zero, segment is parallel to line
        if (Math.abs(denominator) < EPSILON) {
            logger.debug("Segment is parallel to line, no unique intersection point");
            return null;
        }
        
        // Calculate t
        double t = -f1 / denominator;
        
        logger.debug("Intersection parameter t = {}", t);
        
        // Check if intersection is within segment bounds [0, 1]
        if (t < 0 || t > 1) {
            logger.debug("Intersection point is outside segment bounds");
            return null;
        }
        
        // Calculate intersection point
        double x = p1.getX() + t * dx;
        double y = p1.getY() + t * dy;
        Point intersection = new Point(x, y);
        
        logger.debug("Intersection point found: {}", intersection);
        return intersection;
    }

    /**
     * Determines the relative position of the segment with respect to the line.
     * 
     * @param line The line to check against
     * @return A RelativePosition enum value describing the relationship
     */
    public RelativePosition getRelativePosition(Line line) {
        double f1 = line.evaluate(p1);
        double f2 = line.evaluate(p2);
        
        boolean p1OnLine = Math.abs(f1) < EPSILON;
        boolean p2OnLine = Math.abs(f2) < EPSILON;
        
        logger.debug("Evaluating segment endpoints: f(P1) = {}, f(P2) = {}", f1, f2);
        
        // Both endpoints on line
        if (p1OnLine && p2OnLine) {
            return RelativePosition.SEGMENT_ON_LINE;
        }
        
        // One endpoint on line
        if (p1OnLine || p2OnLine) {
            return RelativePosition.ONE_ENDPOINT_ON_LINE;
        }
        
        // Check if parallel (but not on line)
        if (isParallelTo(line)) {
            return RelativePosition.PARALLEL;
        }
        
        // Opposite signs means intersection
        if (f1 * f2 < 0) {
            return RelativePosition.ONE_INTERSECTION;
        }
        
        // Same sign means no intersection
        return RelativePosition.NO_INTERSECTION;
    }

    @Override
    public String toString() {
        return String.format("Segment[%s to %s]", p1, p2);
    }

    /**
     * Enum representing the relative position of a segment with respect to a line.
     */
    public enum RelativePosition {
        PARALLEL,
        NO_INTERSECTION,
        SEGMENT_ON_LINE,
        ONE_ENDPOINT_ON_LINE,
        ONE_INTERSECTION
    }
}
