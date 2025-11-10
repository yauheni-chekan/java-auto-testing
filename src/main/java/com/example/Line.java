package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the line defined by the equation: -3x + 5y - 2 = 0
 */
public class Line {
    private static final Logger logger = LoggerFactory.getLogger(Line.class);
    
    // Coefficients for the line equation: Ax + By + C = 0
    private static final double A = -3.0;
    private static final double B = 5.0;
    private static final double C = -2.0;
    
    // Tolerance for floating point comparisons
    private static final double EPSILON = 1e-9;

    /**
     * Evaluates the line equation for a given point.
     * f(x, y) = -3x + 5y - 2
     * 
     * @param point The point to evaluate
     * @return The value of f(x, y)
     */
    public double evaluate(Point point) {
        double result = A * point.getX() + B * point.getY() + C;
        logger.debug("Evaluating point {} on line: f(x,y) = {}", point, result);
        return result;
    }

    /**
     * Checks if a point lies on the line (within tolerance).
     * 
     * @param point The point to check
     * @return true if the point lies on the line
     */
    public boolean isPointOnLine(Point point) {
        double value = Math.abs(evaluate(point));
        boolean onLine = value < EPSILON;
        logger.debug("Point {} is on line: {}", point, onLine);
        return onLine;
    }

    /**
     * Gets the normal vector of the line.
     * For line Ax + By + C = 0, the normal vector is (A, B).
     * 
     * @return The normal vector as a Point (x, y)
     */
    public Point getNormalVector() {
        return new Point(A, B);
    }

    /**
     * Gets the direction vector of the line.
     * For line Ax + By + C = 0, a direction vector is (B, -A) or (-B, A).
     * We use (B, -A) = (5, 3).
     * 
     * @return The direction vector as a Point (x, y)
     */
    public Point getDirectionVector() {
        return new Point(B, -A);
    }

    /**
     * Gets the line equation as a string.
     * 
     * @return String representation of the line equation
     */
    public String getEquation() {
        return String.format("%.0fx + %.0fy + %.0f = 0", A, B, C);
    }
}

