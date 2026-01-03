# Assertion Utilities

This package provides reusable assertion utilities for Playwright-based test automation.

## Best Practices

### 1. **Use Playwright's Built-in Assertions**
Playwright's `expect()` API provides:
- **Auto-waiting**: Automatically waits for conditions to be met
- **Retry logic**: Retries assertions until timeout
- **Better error messages**: Detailed failure information
- **Web-specific**: Designed for browser automation

### 2. **Store Assertions in Dedicated Classes**
- **PageAssertions**: Page-level assertions (title, URL, etc.)
- **LocatorAssertions**: Element-level assertions (visibility, text, etc.)
- **ComponentAssertions**: Component-specific assertions (optional, for complex components)

### 3. **Separation of Concerns**
- **Page Objects**: Return data and locators (no assertions)
- **Assertion Utilities**: Perform checks using Playwright's expect API
- **Test Classes**: Orchestrate actions and assertions

## Usage Examples

### Using PageAssertions

```java
PageAssertions pageAssertions = new PageAssertions(page);

// Assert page title
pageAssertions.assertTitleContains("InMotion");

// Assert URL
pageAssertions.assertUrlContains("inmotionhosting.com");

// Assert element visibility
pageAssertions.assertVisible(homePage.heroTitle());
```

### Using LocatorAssertions (Fluent API)

```java
// Fluent chaining
LocatorAssertions.expect(homePage.heroTitle())
    .isVisible()
    .containsText("Hosting");

// Single assertion
LocatorAssertions.expect(homePage.header().getLogo())
    .isVisible();
```

### Using Direct AssertJ Assertions (Current Approach)

```java
import static org.assertj.core.api.Assertions.assertThat;

// Direct usage in tests (with WaitUtils for auto-waiting)
WaitUtils.waitForVisible(homePage.heroTitle());
assertThat(homePage.heroTitle().isVisible()).isTrue();
assertThat(homePage.getPageTitle()).containsIgnoringCase("InMotion");
```

## Comparison: Direct AssertJ vs Assertion Utilities

### Direct AssertJ (Current Approach)
```java
assertThat(homePage.getPageTitle())
    .as("Page title should contain InMotion")
    .containsIgnoringCase("InMotion");
```
- ❌ No auto-waiting (requires manual WaitUtils calls)
- ❌ May fail on timing issues if not careful
- ✅ Good for non-web assertions (performance, data validation)
- ✅ Simple and direct

### Assertion Utilities (Recommended)
```java
pageAssertions.assertTitleContains("InMotion");
// or
LocatorAssertions.expect(homePage.heroTitle())
    .isVisible()
    .containsText("Hosting");
```
- ✅ Built-in auto-waiting via WaitUtils
- ✅ More reliable in async environments
- ✅ Reusable and consistent
- ✅ Better error messages with context

## When to Use Each Approach

### Use Playwright Assertions For:
- Element visibility checks
- Text content verification
- URL/title assertions
- Form field values
- Element states (enabled/disabled/checked)

### Use AssertJ For:
- Performance metrics (load times)
- Data validation (non-DOM)
- Business logic assertions
- Collection operations on extracted data

## Migration Strategy

1. **Keep AssertJ** for non-DOM assertions
2. **Add Playwright assertions** for web element checks
3. **Gradually migrate** existing assertions
4. **Use assertion utilities** for consistency

