package com.example.reporting;

import com.example.dto.AnalysisResult;

/**
 * Strategy interface for formatting analysis results.
 */
public interface ReportFormatter {
    /**
     * Formats the analysis result into a string representation.
     * 
     * @param result The analysis data to format
     * @return A formatted string containing the report
     */
    String format(AnalysisResult result);
}
