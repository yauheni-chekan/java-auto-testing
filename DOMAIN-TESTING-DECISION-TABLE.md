# Domain Testing Decision Table - Task 4

## Line Segment Intersection Analysis

**Given Line Equation:** `-3x + 5y - 2 = 0`

**Input:** Two pairs of integer coordinates (x1, y1) and (x2, y2) defining a line segment

---

## 📊 Input Domain

| Variable | Type | Valid Range | Notes |
|----------|------|-------------|-------|
| x1 | Integer | -∞ to +∞ | First point X coordinate (integers only) |
| y1 | Integer | -∞ to +∞ | First point Y coordinate (integers only) |
| x2 | Integer | -∞ to +∞ | Second point X coordinate (integers only) |
| y2 | Integer | -∞ to +∞ | Second point Y coordinate (integers only) |

> **Constraint:** All input coordinates must be integers as per task requirements.

---

## 📊 Output Domain (Relative Positions)

| Position | Condition | Description |
|----------|-----------|-------------|
| PARALLEL | Cross product = 0, f(P1) ≠ 0 | Segment parallel to line, not on line |
| NO_INTERSECTION | f(P1) × f(P2) > 0 | Both points on same side |
| SEGMENT_ON_LINE | f(P1) = 0 AND f(P2) = 0 | Both endpoints on line |
| ONE_ENDPOINT_ON_LINE | f(P1) = 0 XOR f(P2) = 0 | Exactly one endpoint on line |
| ONE_INTERSECTION | f(P1) × f(P2) < 0 | Points on opposite sides |

---

## 📐 Line Equation Reference

For line `-3x + 5y - 2 = 0`:
- Point on line: `f(x, y) = -3x + 5y - 2 = 0`
- Direction vector: `(5, 3)`
- Normal vector: `(-3, 5)`

### Integer Points on the Line (f(x,y) = 0)

Using the equation `-3x + 5y = 2`, integer solutions follow the pattern:
- **x = -4 + 5t, y = -2 + 3t** for any integer t

| t | Point (x, y) | Verification |
|---|--------------|--------------|
| 0 | (-4, -2) | -3(-4) + 5(-2) - 2 = 12 - 10 - 2 = 0 ✓ |
| 1 | (1, 1) | -3(1) + 5(1) - 2 = -3 + 5 - 2 = 0 ✓ |
| 2 | (6, 4) | -3(6) + 5(4) - 2 = -18 + 20 - 2 = 0 ✓ |
| 3 | (11, 7) | -3(11) + 5(7) - 2 = -33 + 35 - 2 = 0 ✓ |

### Perpendicularity Condition

Segment is perpendicular to line if segment direction vector is parallel to normal (-3, 5).
- Example perpendicular direction: (-3, 5) or (3, -5) or multiples

---

## 📊 Domain Testing Decision Table

### Test Case Categories

| # | Category | P1 Position | P2 Position | Expected Result | Perpendicular? |
|---|----------|-------------|-------------|-----------------|----------------|
| 1 | Both above | f(P1) > 0 | f(P2) > 0 | NO_INTERSECTION | N/A |
| 2 | Both below | f(P1) < 0 | f(P2) < 0 | NO_INTERSECTION | N/A |
| 3 | P1 above, P2 below | f(P1) > 0 | f(P2) < 0 | ONE_INTERSECTION | Check |
| 4 | P1 below, P2 above | f(P1) < 0 | f(P2) > 0 | ONE_INTERSECTION | Check |
| 5 | P1 on line | f(P1) = 0 | f(P2) ≠ 0 | ONE_ENDPOINT_ON_LINE | Check |
| 6 | P2 on line | f(P1) ≠ 0 | f(P2) = 0 | ONE_ENDPOINT_ON_LINE | Check |
| 7 | Both on line | f(P1) = 0 | f(P2) = 0 | SEGMENT_ON_LINE | N/A |
| 8 | Parallel above | Parallel | f > 0 | PARALLEL | N/A |
| 9 | Parallel below | Parallel | f < 0 | PARALLEL | N/A |
| 10 | Intersection + Perpendicular | Opposite signs | - | ONE_INTERSECTION | Yes |
| 11 | Intersection + Not Perpendicular | Opposite signs | - | ONE_INTERSECTION | No |

---

## 🧪 Specific Test Values (All Integers)

```
Line equation: -3x + 5y - 2 = 0

Integer Points ON the line (f = 0):
- P(1, 1)      → -3(1) + 5(1) - 2 = -3 + 5 - 2 = 0 ✓
- P(6, 4)      → -3(6) + 5(4) - 2 = -18 + 20 - 2 = 0 ✓
- P(-4, -2)    → -3(-4) + 5(-2) - 2 = 12 - 10 - 2 = 0 ✓
- P(11, 7)     → -3(11) + 5(7) - 2 = -33 + 35 - 2 = 0 ✓

Integer Points ABOVE the line (f > 0):
- P(0, 1)      → -3(0) + 5(1) - 2 = 3 > 0
- P(0, 5)      → -3(0) + 5(5) - 2 = 23 > 0
- P(-5, 0)     → -3(-5) + 5(0) - 2 = 13 > 0
- P(5, 4)      → -3(5) + 5(4) - 2 = 3 > 0

Integer Points BELOW the line (f < 0):
- P(0, 0)      → -3(0) + 5(0) - 2 = -2 < 0
- P(5, 0)      → -3(5) + 5(0) - 2 = -17 < 0
- P(3, 0)      → -3(3) + 5(0) - 2 = -11 < 0
- P(5, 3)      → -3(5) + 5(3) - 2 = -2 < 0

Perpendicular segment direction: (3, -5) or (-3, 5)
- Example: P1(0, 5) to P2(3, 0) → direction (3, -5) ✓ perpendicular
  Verification: Line direction (5, 3) · segment direction (3, -5) = 15 - 15 = 0 ✓

Parallel segment direction: (5, 3) or (-5, -3)
- Example: P1(0, 1) to P2(5, 4) → direction (5, 3) ✓ parallel
```

---

## 📋 Complete Test Cases with Values (All Integer Points)

| # | Test Case | P1 (x1, y1) | P2 (x2, y2) | f(P1) | f(P2) | Expected Position | Perpendicular |
|---|-----------|-------------|-------------|-------|-------|-------------------|---------------|
| 1 | Both above | (0, 1) | (0, 5) | 3 | 23 | NO_INTERSECTION | N/A |
| 2 | Both below | (0, 0) | (5, 0) | -2 | -17 | NO_INTERSECTION | N/A |
| 3 | Crosses (above→below) | (0, 1) | (5, 0) | 3 | -17 | ONE_INTERSECTION | No |
| 4 | Crosses (below→above) | (0, 0) | (0, 1) | -2 | 3 | ONE_INTERSECTION | No |
| 5 | P1 on line | (1, 1) | (0, 5) | 0 | 23 | ONE_ENDPOINT_ON_LINE | No |
| 6 | P2 on line | (0, 0) | (1, 1) | -2 | 0 | ONE_ENDPOINT_ON_LINE | No |
| 7 | Both on line | (1, 1) | (6, 4) | 0 | 0 | SEGMENT_ON_LINE | N/A |
| 8 | Parallel above | (0, 1) | (5, 4) | 3 | 3 | PARALLEL | N/A |
| 9 | Parallel below | (0, 0) | (5, 3) | -2 | -2 | PARALLEL | N/A |
| 10 | Perpendicular cross | (0, 5) | (3, 0) | 23 | -11 | ONE_INTERSECTION | Yes |
| 11 | Non-perpendicular cross | (0, 1) | (2, 0) | 3 | -8 | ONE_INTERSECTION | No |

> **Note:** All test points use integer coordinates only, as required by the task specification.

---

## 🔍 Domain Partitioning Strategy

### Equivalence Classes

1. **Point Position Classes:**
   - Class A: Points above the line (f(x, y) > 0)
   - Class B: Points on the line (f(x, y) = 0)
   - Class C: Points below the line (f(x, y) < 0)

2. **Segment Orientation Classes:**
   - Parallel to line
   - Perpendicular to line
   - Oblique to line

3. **Intersection Classes:**
   - Both endpoints same side (no intersection)
   - Endpoints opposite sides (one intersection)
   - One endpoint on line (endpoint intersection)
   - Both endpoints on line (segment on line)

### Boundary Value Analysis

- **Critical boundaries:** Points exactly on the line (f(x, y) = 0)
- **Transition points:** Where segment crosses the line
- **Special cases:** 
  - Degenerate segments (P1 = P2)
  - Perpendicular orientations
  - Parallel orientations

---

## ✅ Implementation

All 11 test cases have been implemented in:
- **Class:** `RelativePositionParameterizedTest.java`
- **Location:** `src/test/java/com/example/logic/`
- **Framework:** JUnit 5 with `@ParameterizedTest`

---

**Document Version:** 1.0  
**Last Updated:** November 30, 2025
