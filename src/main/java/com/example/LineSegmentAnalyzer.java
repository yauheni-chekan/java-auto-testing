package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Main application class for analyzing the relative position of a line and a line segment.
 * 
 * Given line: -3x + 5y - 2 = 0
 * User inputs two points to define a line segment.
 */
public class LineSegmentAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(LineSegmentAnalyzer.class);

    public static void main(String[] args) {
        logger.info("Starting Line Segment Intersection Analysis");
        logger.info("Given line: -3x + 5y - 2 = 0");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Read first point
            System.out.println("Enter the first point (x1 y1):");
            double x1 = scanner.nextDouble();
            double y1 = scanner.nextDouble();
            Point p1 = new Point(x1, y1);
            logger.info("First point entered: {}", p1);
            
            // Read second point
            System.out.println("Enter the second point (x2 y2):");
            double x2 = scanner.nextDouble();
            double y2 = scanner.nextDouble();
            Point p2 = new Point(x2, y2);
            logger.info("Second point entered: {}", p2);
            
            // Create line and segment
            Line line = new Line();
            LineSegment segment = new LineSegment(p1, p2);
            
            logger.info("Line equation: {}", line.getEquation());
            logger.info("Line segment equation: {}", segment.getLineEquationString());
            logger.info("Line segment: {}", segment);
            
            // Determine relative position
            LineSegment.RelativePosition position = segment.getRelativePosition(line);
            
            // Output results as table
            printAnalysisTable(line, segment, p1, p2, position);
            
            // Also output detailed results
            System.out.println("\n=== Detailed Analysis ===");
            System.out.println("Line equation: " + line.getEquation());
            System.out.println("Line segment: from " + p1 + " to " + p2);
            System.out.println();
            
            switch (position) {
                case PARALLEL:
                    System.out.println("Result: Line and segment are parallel");
                    break;
                    
                case NO_INTERSECTION:
                    System.out.println("Result: No intersection");
                    System.out.println("The segment lies entirely on one side of the line.");
                    break;
                    
                case SEGMENT_ON_LINE:
                    System.out.println("Result: Segment lies entirely on the line");
                    System.out.println("Both endpoints are on the line.");
                    break;
                    
                case ONE_ENDPOINT_ON_LINE:
                    System.out.println("Result: Segment has one endpoint on the line");
                    if (line.isPointOnLine(p1)) {
                        System.out.println("Endpoint P1 " + p1 + " lies on the line.");
                    }
                    if (line.isPointOnLine(p2)) {
                        System.out.println("Endpoint P2 " + p2 + " lies on the line.");
                    }
                    break;
                    
                case ONE_INTERSECTION:
                    System.out.println("Result: One intersection point");
                    
                    // Find intersection point
                    Point intersection = segment.findIntersectionPoint(line);
                    if (intersection != null) {
                        System.out.println("Intersection point: " + intersection);
                        
                        // Check perpendicularity
                        boolean isPerpendicular = segment.isPerpendicularTo(line);
                        System.out.println("Are line and segment mutually perpendicular? " + 
                                         (isPerpendicular ? "Yes" : "No"));
                        
                        // Check if any endpoint belongs to the line
                        boolean p1OnLine = segment.isEndpointOnLine(line, p1);
                        boolean p2OnLine = segment.isEndpointOnLine(line, p2);
                        
                        System.out.println("Does endpoint P1 " + p1 + " belong to the line? " + 
                                         (p1OnLine ? "Yes" : "No"));
                        System.out.println("Does endpoint P2 " + p2 + " belong to the line? " + 
                                         (p2OnLine ? "Yes" : "No"));
                    } else {
                        System.out.println("Could not calculate intersection point (parallel case).");
                    }
                    break;
            }
            
            logger.info("Analysis completed successfully");
            
        } catch (Exception e) {
            logger.error("Error during analysis", e);
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please ensure you enter valid numeric values.");
        } finally {
            scanner.close();
        }
    }

    /**
     * Prints the analysis results in a formatted table.
     * 
     * @param line The given line
     * @param segment The line segment
     * @param p1 First endpoint
     * @param p2 Second endpoint
     * @param position The relative position
     */
    private static void printAnalysisTable(Line line, LineSegment segment, Point p1, Point p2, 
                                          LineSegment.RelativePosition position) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ANALYSIS RESULTS TABLE");
        System.out.println("=".repeat(70));
        
        // Table header
        String separator = "-".repeat(70);
        System.out.println(separator);
        
        // Line information
        System.out.printf("%-30s | %-37s%n", "Given Line Equation", line.getEquation());
        System.out.println(separator);
        
        // Segment endpoints
        System.out.printf("%-30s | P1: %-30s%n", "Segment Endpoints", p1.toString());
        System.out.printf("%-30s | P2: %-30s%n", "", p2.toString());
        System.out.println(separator);
        
        // Segment line equation
        System.out.printf("%-30s | %-37s%n", "Segment Line Equation", segment.getLineEquationString());
        System.out.println(separator);
        
        // Relative position
        String positionStr = switch (position) {
            case PARALLEL -> "Parallel";
            case NO_INTERSECTION -> "No Intersection";
            case SEGMENT_ON_LINE -> "Segment lies on line";
            case ONE_ENDPOINT_ON_LINE -> "One endpoint on line";
            case ONE_INTERSECTION -> "One intersection point";
        };
        System.out.printf("%-30s | %-37s%n", "Relative Position", positionStr);
        System.out.println(separator);
        
        // Additional details based on position
        switch (position) {
            case ONE_INTERSECTION:
                Point intersection = segment.findIntersectionPoint(line);
                if (intersection != null) {
                    System.out.printf("%-30s | %-37s%n", "Intersection Point", intersection.toString());
                    System.out.println(separator);
                    
                    boolean isPerpendicular = segment.isPerpendicularTo(line);
                    System.out.printf("%-30s | %-37s%n", "Mutually Perpendicular", 
                                    isPerpendicular ? "Yes" : "No");
                    System.out.println(separator);
                }
                break;
                
            case ONE_ENDPOINT_ON_LINE:
                boolean p1OnLine = line.isPointOnLine(p1);
                boolean p2OnLine = line.isPointOnLine(p2);
                if (p1OnLine) {
                    System.out.printf("%-30s | P1 %-33s%n", "Endpoint on Line", p1.toString());
                    System.out.println(separator);
                }
                if (p2OnLine) {
                    System.out.printf("%-30s | P2 %-33s%n", "Endpoint on Line", p2.toString());
                    System.out.println(separator);
                }
                break;
                
            case SEGMENT_ON_LINE:
                System.out.printf("%-30s | Both P1 and P2%n", "Endpoints on Line", "");
                System.out.println(separator);
                break;
                
            case PARALLEL:
            case NO_INTERSECTION:
                // No additional details needed for these cases
                break;
        }
        
        // Endpoint positions relative to line
        double f1 = line.evaluate(p1);
        double f2 = line.evaluate(p2);
        String p1Position = Math.abs(f1) < 1e-9 ? "On line" : (f1 > 0 ? "Above/Right" : "Below/Left");
        String p2Position = Math.abs(f2) < 1e-9 ? "On line" : (f2 > 0 ? "Above/Right" : "Below/Left");
        
        System.out.printf("%-30s | P1: %-33s%n", "Endpoint Positions", p1Position + " (f=" + String.format("%.4f", f1) + ")");
        System.out.printf("%-30s | P2: %-33s%n", "", p2Position + " (f=" + String.format("%.4f", f2) + ")");
        System.out.println(separator);
        
        // Parallel and perpendicular checks
        boolean isParallel = segment.isParallelTo(line);
        boolean isPerpendicular = segment.isPerpendicularTo(line);
        System.out.printf("%-30s | %-37s%n", "Parallel to Line", isParallel ? "Yes" : "No");
        System.out.println(separator);
        System.out.printf("%-30s | %-37s%n", "Perpendicular to Line", isPerpendicular ? "Yes" : "No");
        System.out.println("=".repeat(70));
    }
}
