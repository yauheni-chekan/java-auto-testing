package com.example.dto;

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
