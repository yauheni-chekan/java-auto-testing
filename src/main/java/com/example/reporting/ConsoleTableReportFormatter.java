package com.example.reporting;

import com.example.dto.AnalysisResult;

/**
 * Formats analysis results as a text-based table for console output.
 */
public class ConsoleTableReportFormatter implements ReportFormatter {
    
    private static final int TABLE_WIDTH = 70;

    @Override
    public String format(AnalysisResult result) {
        StringBuilder sb = new StringBuilder();
        String border = "=".repeat(TABLE_WIDTH);
        String separator = "-".repeat(TABLE_WIDTH);
        
        sb.append("\n").append(border).append(System.lineSeparator());
        sb.append("ANALYSIS RESULTS TABLE").append(System.lineSeparator());
        sb.append(border).append(System.lineSeparator());
        sb.append(separator).append(System.lineSeparator());
        
        // Line information
        appendRow(sb, "Given Line Equation", result.getLineEquation(), separator);
        
        // Segment endpoints
        sb.append(String.format("%-30s | P1: %-30s%n", "Segment Endpoints", result.getP1String()));
        sb.append(String.format("%-30s | P2: %-30s%n", "", result.getP2String()));
        sb.append(separator).append(System.lineSeparator());
        
        // Segment line equation
        appendRow(sb, "Segment Line Equation", result.getSegmentLineEquation(), separator);
        
        // Relative position
        appendRow(sb, "Relative Position", result.getRelativePosition(), separator);
        
        // Intersection point (if present)
        if (result.getIntersectionPoint() != null) {
            appendRow(sb, "Intersection Point", result.getIntersectionPoint(), separator);
        }
        
        // Endpoint on line (if applicable)
        if (result.getEndpointOnLine() != null) {
            appendRow(sb, "Endpoint on Line", result.getEndpointOnLine(), separator);
        }
        
        // Endpoint positions relative to line
        sb.append(String.format("%-30s | P1: %-33s%n", "Endpoint Positions", result.getP1Position()));
        sb.append(String.format("%-30s | P2: %-33s%n", "", result.getP2Position()));
        sb.append(separator).append(System.lineSeparator());
        
        // Parallel and perpendicular checks
        appendRow(sb, "Parallel to Line", result.isParallel() ? "Yes" : "No", separator);
        appendRow(sb, "Perpendicular to Line", result.isPerpendicular() ? "Yes" : "No", null);
        
        sb.append(border).append(System.lineSeparator());
        
        return sb.toString();
    }

    private void appendRow(StringBuilder sb, String label, String value, String separator) {
        sb.append(String.format("%-30s | %-37s%n", label, value != null ? value : "N/A"));
        if (separator != null) {
            sb.append(separator).append(System.lineSeparator());
        }
    }
}
