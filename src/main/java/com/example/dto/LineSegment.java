package com.example.dto;

/**
 * Represents a line segment defined by two endpoints.
 * Pure data carrier.
 */
public record LineSegment(Point p1, Point p2) {
    @Override
    public String toString() {
        return String.format("Segment[%s to %s]", p1, p2);
    }
}
