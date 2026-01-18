package com.example.dto;

/**
 * Represents a point in 2D space with x and y coordinates.
 */
public record Point(double x, double y) {

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}
