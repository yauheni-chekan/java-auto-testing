package com.example;

import com.example.dto.AnalysisResult;
import com.example.dto.Line;
import com.example.dto.LineSegment;
import com.example.dto.Point;
import com.example.dto.RelativePosition;
import com.example.logic.GeometryUtils;
import com.example.reporting.ConsoleTableReportFormatter;
import com.example.reporting.ReportFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main application class for analyzing the relative position of a line and a line segment.
 * 
 * Given line: -3x + 5y - 2 = 0
 * User inputs two points to define a line segment.
 */
public class LineSegmentAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(LineSegmentAnalyzer.class);
    private static final double EPSILON = 1e-9;

    public static void main(String[] args) {
        logger.info("Starting Line Segment Intersection Analysis");
        logger.info("Given line: -3x + 5y - 2 = 0");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Read first point
            System.out.println("Enter the first point (x1 y1):");
            double x1 = scanner.nextInt();
            double y1 = scanner.nextInt();
            Point p1 = new Point(x1, y1);
            logger.info("First point entered: {}", p1);
            
            // Read second point
            System.out.println("Enter the second point (x2 y2):");
            double x2 = scanner.nextInt();
            double y2 = scanner.nextInt();
            Point p2 = new Point(x2, y2);
            logger.info("Second point entered: {}", p2);

            // Create line and segment
            // The specific line is -3x + 5y - 2 = 0
            Line line = new Line(-3.0, 5.0, -2.0);
            LineSegment segment = new LineSegment(p1, p2);
            
            logger.info("Line equation: {}", GeometryUtils.getEquationString(line));
            logger.info("Line segment equation: {}", GeometryUtils.getLineEquationString(segment));
            logger.info("Line segment: {}", segment);
            
            // Perform all analysis and collect results into a DTO
            AnalysisResult result = analyzeAndCollectResults(line, segment, p1, p2);
            
            logger.info("Analysis completed successfully");
            
            // Generate report using Strategy pattern
            ReportFormatter formatter = new ConsoleTableReportFormatter();
            String report = formatter.format(result);
            
            // Print final results table at the end
            System.out.println("\n\n");
            System.out.print(report);
        } catch (InputMismatchException e) {
            logger.error("Input values must be integers");
        } catch (Exception e) {
            logger.error("Error during analysis", e);
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please ensure you enter valid numeric values.");
        } finally {
            scanner.close();
        }
    }

    /**
     * Performs all analysis calculations and registers results into the result DTO.
     * All domain logic and calculations happen here.
     */
    private static AnalysisResult analyzeAndCollectResults(Line line, LineSegment segment, Point p1, Point p2) {
        AnalysisResult.Builder builder = new AnalysisResult.Builder();
        
        // Register basic information
        builder.setLineEquation(GeometryUtils.getEquationString(line))
               .setSegmentEndpoints(p1.toString(), p2.toString())
               .setSegmentLineEquation(GeometryUtils.getLineEquationString(segment));
        
        // Calculate and register relative position
        RelativePosition position = GeometryUtils.getRelativePosition(segment, line);
        String positionStr = switch (position) {
            case PARALLEL -> "Parallel";
            case NO_INTERSECTION -> "No Intersection";
            case SEGMENT_ON_LINE -> "Segment lies on line";
            case ONE_ENDPOINT_ON_LINE -> "One endpoint on line";
            case ONE_INTERSECTION -> "One intersection point";
        };
        builder.setRelativePosition(positionStr);
        
        // Calculate and register intersection point (if applicable)
        if (position == RelativePosition.ONE_INTERSECTION) {
            Point intersection = GeometryUtils.findIntersectionPoint(segment, line);
            if (intersection != null) {
                builder.setIntersectionPoint(intersection.toString());
            }
        }
        
        // Calculate and register endpoint on line (if applicable)
        boolean p1OnLine = GeometryUtils.isPointOnLine(line, p1);
        boolean p2OnLine = GeometryUtils.isPointOnLine(line, p2);
        
        if (position == RelativePosition.SEGMENT_ON_LINE) {
            builder.setEndpointOnLine("Both P1 and P2");
        } else if (position == RelativePosition.ONE_ENDPOINT_ON_LINE) {
            if (p1OnLine && p2OnLine) {
                builder.setEndpointOnLine("Both P1 and P2");
            } else if (p1OnLine) {
                builder.setEndpointOnLine("P1 " + p1);
            } else if (p2OnLine) {
                builder.setEndpointOnLine("P2 " + p2);
            }
        }
        
        // Calculate and register endpoint positions relative to line
        double f1 = GeometryUtils.evaluate(line, p1);
        double f2 = GeometryUtils.evaluate(line, p2);
        String p1Position = formatEndpointPosition(f1);
        String p2Position = formatEndpointPosition(f2);
        builder.setEndpointPositions(p1Position, p2Position);
        
        // Calculate and register parallel/perpendicular status
        boolean isParallel = GeometryUtils.isParallelTo(segment, line);
        boolean isPerpendicular = GeometryUtils.isPerpendicularTo(segment, line);
        builder.setParallel(isParallel)
               .setPerpendicular(isPerpendicular);
        
        return builder.build();
    }

    /**
     * Formats the endpoint position based on line evaluation value.
     */
    private static String formatEndpointPosition(double f) {
        String side = Math.abs(f) < EPSILON ? "On line" : (f > 0 ? "Above/Right" : "Below/Left");
        return side + " (f=" + String.format("%.4f", f) + ")";
    }
}
