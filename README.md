# Line Segment Intersection Analysis

## 📋 Project Overview

This project implements a solution for **Task 4** of the Java Auto Testing practice. It is a Java application designed to analyze the relative position of a specific straight line and a user-defined line segment.

**The Line Equation:** `-3x + 5y - 2 = 0`

The application takes two pairs of integer coordinates `(x1, y1)` and `(x2, y2)` representing a line segment, and determines how this segment relates to the fixed line.

## ✨ Features

- **Geometric Analysis**: Determines if the segment:
  - Is parallel to the line
  - Has no intersection with the line
  - Has one intersection point with the line
  - Has one endpoint on the line
  - Is completely contained within the line
- **Perpendicularity Check**: If there is an intersection, it checks if the segment is perpendicular to the line.
- **Detailed Reporting**: Outputs the analysis results in a formatted console table.
- **Robust Testing**: Includes comprehensive unit tests covering domain boundaries and edge cases.

## 🛠️ Technologies

- **Java 25**: Core programming language.
- **Maven**: Dependency management and build tool.
- **JUnit 5**: Unit testing framework (Jupiter).
- **SLF4J / Logback**: Logging framework.
- **JaCoCo**: Code coverage reporting.

## 📂 Project Structure

The project follows a clean architecture with separation of concerns:

- **`com.example.dto`**: Data Transfer Objects (`Point`, `Line`, `LineSegment`, `AnalysisResult`) and Enums (`RelativePosition`).
- **`com.example.logic`**: Core geometric logic and calculations (`GeometryUtils`).
- **`com.example.reporting`**: Logic for formatting and displaying results (`ConsoleTableReportFormatter`).
- **`com.example`**: Main application entry point (`LineSegmentAnalyzer`).

## 🚀 Getting Started

### Prerequisites

- Java JDK 25
- Maven 3.x

### Building the Project

```bash
mvn clean install
```

### Running the Application

You can run the application using the Maven Exec plugin:

```bash
mvn exec:java
```

Follow the on-screen prompts to enter the coordinates for the line segment.

### Running Tests

To execute the unit tests:

```bash
mvn test
```

To generate a code coverage report (JaCoCo):

```bash
mvn verify
```
The report will be available at `target/site/jacoco/index.html`.

## 🧪 Testing Strategy

The project employs a rigorous testing strategy focusing on Domain Testing:
- **Parameterized Tests**: `RelativePositionParameterizedTest` covers 11 distinct domain cases (e.g., segment above line, crossing line, parallel, perpendicular, etc.).
- **Unit Tests**: `GeometryUtilsTest` and DTO tests ensure the correctness of individual components.
