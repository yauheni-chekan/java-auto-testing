package com.example.dto;

/**
 * Data Transfer Object (DTO) that holds all the results of the line segment analysis.
 * This class is immutable and contains no business logic.
 */
public class AnalysisResult {
    
    private final String lineEquation;
    private final String p1String;
    private final String p2String;
    private final String segmentLineEquation;
    private final String relativePosition;
    private final String intersectionPoint;
    private final boolean isParallel;
    private final boolean isPerpendicular;
    private final String p1Position;
    private final String p2Position;
    private final String endpointOnLine;

    private AnalysisResult(Builder builder) {
        this.lineEquation = builder.lineEquation;
        this.p1String = builder.p1String;
        this.p2String = builder.p2String;
        this.segmentLineEquation = builder.segmentLineEquation;
        this.relativePosition = builder.relativePosition;
        this.intersectionPoint = builder.intersectionPoint;
        this.isParallel = builder.isParallel;
        this.isPerpendicular = builder.isPerpendicular;
        this.p1Position = builder.p1Position;
        this.p2Position = builder.p2Position;
        this.endpointOnLine = builder.endpointOnLine;
    }

    // Getters
    public String getLineEquation() { return lineEquation; }
    public String getP1String() { return p1String; }
    public String getP2String() { return p2String; }
    public String getSegmentLineEquation() { return segmentLineEquation; }
    public String getRelativePosition() { return relativePosition; }
    public String getIntersectionPoint() { return intersectionPoint; }
    public boolean isParallel() { return isParallel; }
    public boolean isPerpendicular() { return isPerpendicular; }
    public String getP1Position() { return p1Position; }
    public String getP2Position() { return p2Position; }
    public String getEndpointOnLine() { return endpointOnLine; }

    /**
     * Builder pattern to construct the AnalysisResult.
     */
    public static class Builder {
        private String lineEquation;
        private String p1String;
        private String p2String;
        private String segmentLineEquation;
        private String relativePosition;
        private String intersectionPoint;
        private boolean isParallel;
        private boolean isPerpendicular;
        private String p1Position;
        private String p2Position;
        private String endpointOnLine;

        public Builder setLineEquation(String lineEquation) {
            this.lineEquation = lineEquation;
            return this;
        }

        public Builder setSegmentEndpoints(String p1, String p2) {
            this.p1String = p1;
            this.p2String = p2;
            return this;
        }

        public Builder setSegmentLineEquation(String segmentLineEquation) {
            this.segmentLineEquation = segmentLineEquation;
            return this;
        }

        public Builder setRelativePosition(String relativePosition) {
            this.relativePosition = relativePosition;
            return this;
        }

        public Builder setIntersectionPoint(String intersectionPoint) {
            this.intersectionPoint = intersectionPoint;
            return this;
        }

        public Builder setParallel(boolean parallel) {
            isParallel = parallel;
            return this;
        }

        public Builder setPerpendicular(boolean perpendicular) {
            isPerpendicular = perpendicular;
            return this;
        }

        public Builder setEndpointPositions(String p1Position, String p2Position) {
            this.p1Position = p1Position;
            this.p2Position = p2Position;
            return this;
        }

        public Builder setEndpointOnLine(String endpointOnLine) {
            this.endpointOnLine = endpointOnLine;
            return this;
        }

        public AnalysisResult build() {
            return new AnalysisResult(this);
        }
    }
}
