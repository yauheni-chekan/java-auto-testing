# Playwright Test Automation Framework

A robust, enterprise-grade test automation framework for web testing using **Java**, **Playwright**, **Cucumber BDD**, and **Allure Reports**. This framework is specifically designed for automating the [InMotion Hosting](https://www.inmotionhosting.com/) website but can be easily adapted for any web application.

## Features

- **Behavior-Driven Development (BDD)**: Cucumber-based test scenarios written in Gherkin syntax
- **Page Object Model (POM)**: Clean separation between test logic and page interactions
- **Layered Architecture**: Organized into Core, Pages, Components, Utils, and Test layers
- **Single-Threaded Execution**: Simple, sequential test execution for easier debugging
- **Browser Session Reuse**: Browser window is reused for the entire test session, improving performance
- **Allure Reporting**: Rich, interactive test reports with screenshots and attachments
- **Configurable**: Properties-based configuration with system property overrides
- **Cross-Browser Support**: Chromium, Firefox, and WebKit
- **Automatic Screenshots**: Captures screenshots on test failures
- **Comprehensive Logging**: SLF4J with Logback for structured logging
- **Tag-Based Test Execution**: Run specific test scenarios using Cucumber tags

## Project Structure

```
src/
├── main/java/com/bdd_example/
│   ├── config/
│   │   └── Configuration.java         # Configuration management
│   ├── core/
│   │   ├── BrowserFactory.java        # Browser creation and setup
│   │   └── PlaywrightManager.java     # Singleton Playwright management
│   ├── pages/
│   │   ├── BasePage.java              # Base page object class
│   │   ├── HomePage.java              # InMotion homepage PO
│   │   ├── PricingPage.java           # Pricing page PO
│   │   └── components/
│   │       ├── HeaderComponent.java   # Header navigation component
│   │       └── FooterComponent.java   # Footer component
│   ├── assertions/
│   │   ├── HomePageAssertions.java    # Homepage assertions
│   │   ├── PricingPageAssertions.java # Pricing page assertions
│   │   └── FooterComponentAssertions.java # Footer assertions
│   └── utils/
│       ├── WaitUtils.java             # Wait helper methods
│       └── AllureUtils.java           # Allure reporting utilities
├── main/resources/
│   ├── config.properties              # Framework configuration
│   └── logback.xml                    # Logging configuration
└── test/
    ├── java/com/bdd_example/
    │   ├── base/
    │   │   └── BaseTest.java          # Base test class with lifecycle
    │   └── bdd/
    │       ├── runners/
    │       │   └── CucumberTestRunner.java  # Cucumber test runner
    │       └── steps/
    │           ├── CommonSteps.java         # Common step definitions
    │           ├── HeaderNavigationSteps.java # Header navigation steps
    │           └── PricingNavigationSteps.java # Pricing navigation steps
    └── resources/
        └── features/
            ├── header_navigation.feature    # Header navigation scenarios
            └── pricing_navigation.feature   # Pricing navigation scenarios
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.8+**
- **Allure CLI** (optional, for report generation)

## Quick Start

### 1. Clone and Install Dependencies

```bash
git clone <repository-url>
cd java-auto-testing
mvn clean install -DskipTests
```

### 2. Install Playwright Browsers

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### 3. Run Tests

The framework uses Cucumber BDD with JUnit Platform. Tests are executed via the `CucumberTestRunner` class.

**For Bash/Linux/Mac:**
```bash
# Run all Cucumber scenarios
mvn clean test

# Run with specific browser
mvn clean test -Dbrowser.type=firefox

# Run in headed mode (visible browser)
mvn clean test -Dbrowser.headless=false

# Run scenarios with specific tags (e.g., @smoke)
mvn clean test -Dcucumber.filter.tags="@smoke"

# Run scenarios excluding specific tags (e.g., exclude @wip)
mvn clean test -Dcucumber.filter.tags="not @wip"

# Run scenarios with multiple tag conditions
mvn clean test -Dcucumber.filter.tags="@smoke and @navigation"

# Run specific feature file
mvn clean test -Dcucumber.features="src/test/resources/features/pricing_navigation.feature"

# Combine browser and tag filters
mvn clean test -Dbrowser.type=firefox -Dcucumber.filter.tags="@smoke"
```

**For PowerShell (Windows):**
```powershell
# Run all Cucumber scenarios
mvn clean test

# Run with specific browser (quote the -D parameter)
mvn clean test "-Dbrowser.type=firefox"

# Run in headed mode (visible browser)
mvn clean test "-Dbrowser.headless=false"

# Run scenarios with specific tags (e.g., @smoke)
mvn clean test "-Dcucumber.filter.tags=@smoke"

# Run scenarios excluding specific tags (e.g., exclude @wip)
mvn clean test "-Dcucumber.filter.tags=not @wip"

# Run scenarios with multiple tag conditions
mvn clean test "-Dcucumber.filter.tags=@smoke and @navigation"

# Run specific feature file
mvn clean test "-Dcucumber.features=src/test/resources/features/pricing_navigation.feature"

# Multiple system properties
mvn clean test "-Dbrowser.type=firefox" "-Dbrowser.headless=false" "-Dcucumber.filter.tags=@smoke"
```

**Note:** By default, the `CucumberTestRunner` is configured to run scenarios with `@smoke` tag or exclude `@wip` tag. You can override this behavior using the `-Dcucumber.filter.tags` system property.

### 4. Generate Allure Report

**Option 1: Using Allure Maven Plugin (requires Allure CLI)**

First, install Allure CLI:
- **Windows**: Download from [Allure Releases](https://github.com/allure-framework/allure2/releases) or use Chocolatey: `choco install allure`
- **Mac**: `brew install allure`
- **Linux**: Follow [Allure Installation Guide](https://docs.qameta.io/allure/#_installing_a_commandline)

Then generate the report:
```bash
# Generate and open report in browser
mvn allure:serve

# Generate report only (HTML files in target/site/allure-maven-plugin)
mvn allure:report
```

**Option 2: Using Allure CLI Directly**

```bash
# Generate standard Allure report
allure generate target/allure-results -o target/allure-report --clean

# Open report in browser
allure open target/allure-report
```

**Option 3: Using npm Scripts (Recommended)**

If you have `package.json` with allure-commandline, you can use npm scripts:

```bash
# Generate standard Allure report
npm run report

# Serve report (opens in browser automatically)
npm run report:serve

# Open existing report
npm run open
```

**Troubleshooting "tree.json not found" Error:**

If you see "Data 'widgets/default/tree.json' not found!" when using the Allure Awesome plugin:

1. **Use Standard Allure Report Instead** (Recommended):
   ```bash
   npm run report
   # Then open target/allure-report/index.html in your browser
   ```
   The standard report works perfectly and includes all features including screenshots.

2. **Fix Awesome Plugin Report:**
   If you're using the Allure Awesome plugin and see the "tree.json not found" error:
   ```powershell
   # Step 1: Generate standard Allure report first
   npm run report
   
   # Step 2: Generate awesome report (if you haven't already)
   npx allure awesome target/allure-results -o allure-report --single-file
   
   # Step 3: Run the fix script to copy widgets structure and create tree.json
   .\fix-awesome-report.ps1
   
   # Step 4: Open the fixed report
   # Open allure-report/index.html in your browser
   ```

3. **Or Install Allure CLI** and use it directly:
   ```bash
   # Install Allure CLI first (see Option 1)
   allure generate target/allure-results -o target/allure-report --clean
   allure open target/allure-report
   ```

**Option 4: View Results Without Installation**

The Allure results are stored in `target/allure-results/` as JSON files. You can:
- Upload them to an online Allure viewer
- Use CI/CD tools that support Allure (Jenkins, GitLab CI, etc.)

## Configuration

Edit `src/main/resources/config.properties` to customize the framework:

```properties
# Base URL
base.url=https://www.inmotionhosting.com/

# Browser settings
browser.type=chromium          # chromium, firefox, webkit
browser.headless=true
browser.viewport.width=1920
browser.viewport.height=1080

# Timeouts (milliseconds)
timeout.default=30000
timeout.navigation=60000
timeout.element=10000

# Screenshot settings
screenshot.on.failure=true
screenshot.format=png
```

### Runtime Overrides

Override any property via system properties:

**Bash/Linux/Mac:**
```bash
mvn clean test -Dbrowser.type=firefox -Dbrowser.headless=false -Dbase.url=https://example.com -Dcucumber.filter.tags="@smoke"
```

**PowerShell (Windows):**
```powershell
mvn clean test "-Dbrowser.type=firefox" "-Dbrowser.headless=false" "-Dbase.url=https://example.com" "-Dcucumber.filter.tags=@smoke"
```

## Browser Session Management

The framework reuses the browser window for the entire test session to improve performance:

- **Browser Initialization**: The browser is initialized once before all tests in a test class (`@BeforeAll`)
- **State Clearing**: Between each test, cookies and browser state are cleared to ensure test isolation
- **Browser Cleanup**: The browser is closed once after all tests complete (`@AfterAll`)

This approach provides:
- **Faster Test Execution**: No browser startup overhead between tests
- **Better Performance**: Reduced resource usage
- **Test Isolation**: State is cleared between tests to prevent interference

If you need a completely fresh browser for each test, you can override the `setUp()` and `tearDown()` methods in your test class.

## Writing Tests

### Cucumber Feature Files

Tests are written in Gherkin syntax as `.feature` files located in `src/test/resources/features/`:

```gherkin
@smoke @navigation
Feature: Homepage Navigation
  As a user
  I want to navigate the homepage
  So that I can access different sections

  Background:
    Given I am on the InMotion Hosting website

  Scenario: Homepage should load successfully
    When I navigate to the homepage
    Then the homepage should be loaded
    And the page title should contain "InMotion"
```

### Step Definitions

Step definitions are Java classes in `src/test/java/com/bdd_example/bdd/steps/` that implement the Gherkin steps:

```java
package com.bdd_example.bdd.steps;

import com.bdd_example.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class NavigationSteps extends BaseTest {
    
    private HomePage homePage;
    
    @Given("I am on the InMotion Hosting website")
    public void iAmOnTheWebsite() {
        // Initialization logic
    }
    
    @When("I navigate to the homepage")
    public void iNavigateToHomepage() {
        homePage = new HomePage();
        homePage.open();
    }
    
    @Then("the homepage should be loaded")
    public void homepageShouldBeLoaded() {
        homePage.verifyPageLoaded();
    }
}
```

### Cucumber Tags

Use tags to organize and filter scenarios:

- `@smoke` - Critical smoke tests
- `@navigation` - Navigation-related tests
- `@wip` - Work in progress (excluded by default)
- Custom tags for feature-specific tests

Tag examples in feature files:
```gherkin
@smoke @pricing
Feature: Pricing Page
  Scenario: View pricing plans
    # ...
```

### Creating Page Objects

```java
public class MyPage extends BasePage {

    private final Locator myElement;

    public MyPage() {
        super();
        this.myElement = page.locator("#my-element");
    }

    @Override
    protected String getUrlPattern() {
        return ".*mypage.*";
    }

    @Override
    protected Locator getPageIdentifier() {
        return myElement;
    }

    @Step("Perform action")
    public void doSomething() {
        click(myElement);
    }
}
```

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Cucumber BDD Layer                       │
│         (Feature Files + Step Definitions)                  │
├─────────────────────────────────────────────────────────────┤
│                     Page Object Layer                       │
│         (Page Objects + Reusable Components)                │
├─────────────────────────────────────────────────────────────┤
│                        Core Layer                           │
│     (PlaywrightManager, BrowserFactory, Configuration)      │
├─────────────────────────────────────────────────────────────┤
│                      Utilities Layer                        │
│           (WaitUtils, AllureUtils, Helpers)                 │
└─────────────────────────────────────────────────────────────┘
```

## Reporting

The framework generates multiple types of reports for comprehensive test analysis:

### Allure Reports

The framework generates Allure reports with:

- Test execution summary
- Step-by-step test execution (Cucumber scenarios and steps)
- Screenshots on failure
- Page source attachments
- Environment information
- Test categorization by Feature/Scenario
- Gherkin syntax display in reports

### Cucumber Reports

Cucumber generates multiple report formats automatically:

1. **HTML Report** (`target/cucumber-reports/cucumber.html`)
   - Interactive HTML report with scenario details
   - View in browser: Open `target/cucumber-reports/cucumber.html`

2. **JSON Report** (`target/cucumber-reports/cucumber.json`)
   - Machine-readable format for CI/CD integration
   - Can be parsed by various reporting tools

3. **XML Report** (`target/cucumber-reports/cucumber.xml`)
   - JUnit-compatible XML format
   - Useful for CI/CD tools that expect JUnit XML

4. **Pretty Console Output**
   - Formatted console output during test execution
   - Shows scenario progress and results

### Maven Surefire Reports

Maven Surefire Plugin automatically generates test execution reports in `target/surefire-reports/` after each test run.

#### Report Types

1. **TXT Reports** (`{ClassName}.txt`)
   - Quick summary of test execution
   - Shows: Tests run, Failures, Errors, Skipped, Time elapsed

2. **XML Reports** (`TEST-{ClassName}.xml`)
   - Detailed test execution results
   - Includes: System properties, stack traces, execution times
   - Machine-readable format for CI/CD integration

#### Viewing Reports

**Cucumber HTML Report:**
```bash
# After running tests, open the HTML report
# Windows: start target/cucumber-reports/cucumber.html
# Mac/Linux: open target/cucumber-reports/cucumber.html
```

**Allure Report:**
```bash
# Generate and serve Allure report
mvn allure:serve
```

**Surefire Reports:**
- Navigate to `target/surefire-reports/` directory
- Open `.txt` files for quick summaries
- Open `.xml` files for detailed information

#### CI/CD Integration

Reports can be:
- Published as build artifacts
- Parsed by CI tools (Jenkins, GitHub Actions, GitLab CI, etc.)
- Used to fail builds if tests fail
- Integrated with test result visualization tools

**Example GitHub Actions Integration:**
```yaml
- name: Publish Test Results
  uses: EnricoMi/publish-unit-test-result-action@v2
  if: always()
  with:
    files: target/surefire-reports/**/*.xml
```

## Best Practices

1. **BDD Scenarios**: Write clear, readable Gherkin scenarios that describe user behavior
2. **Step Definitions**: Keep step definitions focused and reusable across scenarios
3. **Page Objects**: One class per page/significant component
4. **Locators**: Use robust locator strategies (data-testid, aria-label)
5. **Assertions**: Use AssertJ for fluent, readable assertions
6. **Tags**: Use meaningful tags to organize and filter scenarios
7. **Logging**: Log important actions and state changes
8. **Screenshots**: Capture screenshots at key verification points
9. **Browser Session Reuse**: The browser is initialized once per test class and reused for all tests, with state cleared between tests
10. **Feature Files**: Organize feature files by functionality or user journey

## Troubleshooting

### Browser Not Found

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### Timeout Issues

Increase timeouts in `config.properties`:

```properties
timeout.default=60000
timeout.navigation=120000
```

### Headless Mode Issues

Run in headed mode for debugging:

```bash
mvn clean test -Dbrowser.headless=false -Dbrowser.slow.mo=500
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
