# Playwright Test Automation Framework

A robust, enterprise-grade test automation framework for web testing using **Java**, **Playwright**, **JUnit 5**, and **Allure Reports**. This framework is specifically designed for automating the [InMotion Hosting](https://www.inmotionhosting.com/) website but can be easily adapted for any web application.

## Features

- **Page Object Model (POM)**: Clean separation between test logic and page interactions
- **Layered Architecture**: Organized into Core, Pages, Components, Utils, and Test layers
- **Single-Threaded Execution**: Simple, sequential test execution for easier debugging
- **Browser Session Reuse**: Browser window is reused for the entire test session, improving performance
- **Allure Reporting**: Rich, interactive test reports with screenshots and attachments
- **Configurable**: Properties-based configuration with system property overrides
- **Cross-Browser Support**: Chromium, Firefox, and WebKit
- **Automatic Screenshots**: Captures screenshots on test failures
- **Comprehensive Logging**: SLF4J with Logback for structured logging

## Project Structure

```
src/
├── main/java/com/example/
│   ├── config/
│   │   └── Configuration.java         # Configuration management
│   ├── core/
│   │   ├── BrowserFactory.java        # Browser creation and setup
│   │   └── PlaywrightManager.java     # Singleton Playwright management
│   ├── pages/
│   │   ├── BasePage.java              # Base page object class
│   │   ├── HomePage.java              # InMotion homepage PO
│   │   └── components/
│   │       ├── HeaderComponent.java   # Header navigation component
│   │       └── FooterComponent.java   # Footer component
│   └── utils/
│       ├── WaitUtils.java             # Wait helper methods
│       └── AllureUtils.java           # Allure reporting utilities
├── main/resources/
│   ├── config.properties              # Framework configuration
│   └── logback.xml                    # Logging configuration
└── test/java/com/example/
    ├── base/
    │   └── BaseTest.java              # Base test class with lifecycle
    └── tests/
        └── HomePageTest.java          # Sample test class
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

**For Bash/Linux/Mac:**
```bash
# Run all tests
mvn clean test

# Run with specific browser
mvn clean test -Dbrowser.type=firefox

# Run in headed mode (visible browser)
mvn clean test -Dbrowser.headless=false

# Run specific test class
mvn clean test -Dtest=HomePageTest

# Run specific test method
mvn clean test -Dtest=HomePageTest#testHomePageLoads

# Run with smoke tag
mvn clean test -Dgroups=smoke
```

**For PowerShell (Windows):**
```powershell
# Run all tests
mvn clean test

# Run with specific browser (quote the -D parameter)
mvn clean test "-Dbrowser.type=firefox"

# Run in headed mode (visible browser)
mvn clean test "-Dbrowser.headless=false"

# Run specific test class
mvn clean test "-Dtest=HomePageTest"

# Run specific test method
mvn clean test "-Dtest=HomePageTest#testHomePageLoads"

# Multiple system properties
mvn clean test "-Dbrowser.type=firefox" "-Dbrowser.headless=false"

# Run with smoke tag
mvn clean test "-Dgroups=smoke"
```

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
mvn clean test -Dbrowser.type=firefox -Dbrowser.headless=false -Dbase.url=https://example.com
```

**PowerShell (Windows):**
```powershell
mvn clean test "-Dbrowser.type=firefox" "-Dbrowser.headless=false" "-Dbase.url=https://example.com"
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

### Basic Test Example

```java
@Epic("Website")
@Feature("Homepage")
class MyTest extends BaseTest {

    @Test
    @Story("Page Load")
    @DisplayName("Homepage should load successfully")
    void testHomePageLoads() {
        HomePage homePage = new HomePage();
        homePage.open();
        
        assertThat(homePage.getPageTitle())
            .containsIgnoringCase("InMotion");
    }
}
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
│                        Test Layer                           │
│              (JUnit 5 Test Classes)                         │
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
- Step-by-step test execution
- Screenshots on failure
- Page source attachments
- Environment information
- Test categorization by Epic/Feature/Story

### Maven Surefire Reports

Maven Surefire Plugin automatically generates test execution reports in `target/surefire-reports/` after each test run.

#### Report Types

1. **TXT Reports** (`{ClassName}.txt`)
   - Quick summary of test execution
   - Shows: Tests run, Failures, Errors, Skipped, Time elapsed
   - Example:
     ```
     Tests run: 10, Failures: 2, Errors: 2, Skipped: 0, Time elapsed: 0.688 s
     ```

2. **XML Reports** (`TEST-{ClassName}.xml`)
   - Detailed test execution results
   - Includes: System properties, stack traces, test method details, execution times
   - Machine-readable format for CI/CD integration

#### Viewing Surefire Reports

**Option 1: Direct File Access**
- Navigate to `target/surefire-reports/` directory
- Open `.txt` files for quick summaries
- Open `.xml` files for detailed information (view in browser or XML viewer)

**Option 2: Maven Command Line**
```bash
# Run tests and view summary in console
mvn test

# Run with detailed output
mvn test -X

# Run specific test class
mvn test -Dtest=HomePageTest

# Run specific test method
mvn test -Dtest=HomePageTest#testHomePageLoads
```

**Option 3: Generate HTML Reports (if configured)**
```bash
# Generate HTML report from XML files
mvn surefire-report:report-only
```

#### Using Reports for Debugging

1. **Identify Failing Tests**: Check TXT files for quick overview
2. **Analyze Stack Traces**: TXT files contain full stack traces for failures and errors
3. **Check Execution Times**: Identify slow tests from time elapsed information
4. **Review System Properties**: XML files include environment details (Java version, OS, etc.)

#### Report Location

- **Directory**: `target/surefire-reports/`
- **Generated**: Automatically after each `mvn test` execution
- **Format**: One TXT and one XML file per test class

#### CI/CD Integration

Surefire reports can be:
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

1. **Page Objects**: One class per page/significant component
2. **Locators**: Use robust locator strategies (data-testid, aria-label)
3. **Assertions**: Use AssertJ for fluent, readable assertions
4. **Logging**: Log important actions and state changes
5. **Screenshots**: Capture screenshots at key verification points
6. **Browser Session Reuse**: The browser is initialized once per test class and reused for all tests, with state cleared between tests

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
