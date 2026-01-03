package com.bdd_example.base;

import com.bdd_example.config.Configuration;
import com.bdd_example.core.PlaywrightManager;
import com.bdd_example.utils.AllureUtils;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Base test class providing common setup and teardown for all tests.
 * Handles Playwright lifecycle, screenshot on failure, and Allure integration.
 */
@ExtendWith(BaseTest.TestResultWatcher.class)
public abstract class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Configuration config = Configuration.getInstance();
    protected static final PlaywrightManager playwrightManager = PlaywrightManager.getInstance();

    /**
     * Initializes the browser once before all tests in the class.
     * The browser window will be reused for the entire test session.
     */
    @BeforeAll
    public static void setUpSuite() {
        Logger suiteLogger = LoggerFactory.getLogger(BaseTest.class);
        suiteLogger.info("========================================");
        suiteLogger.info("Initializing browser for test session");
        suiteLogger.info("========================================");

        // Initialize Playwright and browser (will be reused for all tests)
        playwrightManager.init();

        suiteLogger.info("Browser initialized and ready for test session");
    }

    /**
     * Sets up before each test.
     * Clears browser state to ensure test isolation.
     *
     * @param testInfo JUnit test information
     */
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();
        logger.info("========================================");
        logger.info("Starting test: {}", testName);
        logger.info("========================================");

        // Clear browser state between tests (cookies, storage, etc.)
        playwrightManager.clearBrowserState();

        // Set Allure suite name and add test metadata
        Allure.suite(getClass().getSimpleName());
        AllureUtils.addParameter("Browser", config.getBrowserType());
        AllureUtils.addParameter("Headless", String.valueOf(config.isHeadless()));
        AllureUtils.addParameter("Base URL", config.getBaseUrl());

        logger.debug("Test setup complete for: {}", testName);
    }

    /**
     * Cleans up after each test.
     * Clears browser state but keeps the browser alive for the next test.
     *
     * @param testInfo JUnit test information
     */
    @AfterEach
    public void tearDown(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();
        logger.debug("Tearing down test: {}", testName);

        // Browser stays alive - just log completion
        logger.info("========================================");
        logger.info("Finished test: {}", testName);
        logger.info("========================================");
    }

    /**
     * Closes the browser after all tests in the class complete.
     * This is called once at the end of the test session.
     */
    @AfterAll
    public static void tearDownSuite() {
        Logger suiteLogger = LoggerFactory.getLogger(BaseTest.class);
        suiteLogger.info("========================================");
        suiteLogger.info("Closing browser after test session");
        suiteLogger.info("========================================");

        try {
            playwrightManager.quit();
            suiteLogger.info("Browser closed successfully");
        } catch (Exception e) {
            suiteLogger.error("Error during browser cleanup: {}", e.getMessage(), e);
        }
    }

    /**
     * Gets the current page instance.
     *
     * @return Page instance
     */
    protected Page getPage() {
        return playwrightManager.getPage();
    }

    /**
     * Takes a screenshot and attaches it to the report.
     *
     * @param name Screenshot name
     */
    protected void takeScreenshot(String name) {
        AllureUtils.attachScreenshot(getPage(), name);
    }

    /**
     * Takes a full page screenshot and attaches it to the report.
     *
     * @param name Screenshot name
     */
    protected void takeFullPageScreenshot(String name) {
        AllureUtils.attachFullPageScreenshot(getPage(), name);
    }

    /**
     * Attaches the current page source to the report.
     */
    protected void attachPageSource() {
        AllureUtils.attachPageSource(getPage(), "Page Source");
    }

    /**
     * Attaches the current URL to the report.
     */
    protected void attachCurrentUrl() {
        AllureUtils.attachCurrentUrl(getPage());
    }

    /**
     * JUnit 5 TestWatcher extension for handling test results.
     * Takes screenshots on test failure automatically.
     */
    public static class TestResultWatcher implements TestWatcher {

        private static final Logger watcherLogger = LoggerFactory.getLogger(TestResultWatcher.class);

        @Override
        public void testSuccessful(ExtensionContext context) {
            watcherLogger.info("Test PASSED: {}", context.getDisplayName());
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            String testName = context.getDisplayName();
            watcherLogger.error("Test FAILED: {} - {}", testName, cause.getMessage());

            // Take screenshot on failure using lifecycle API (works in TestWatcher context)
            try {
                PlaywrightManager manager = PlaywrightManager.getInstance();
                if (manager.isInitialized()) {
                    Page page = manager.getPage();
                    if (page != null && !page.isClosed()) {
                        Configuration config = Configuration.getInstance();
                        if (config.isScreenshotOnFailure()) {
                            // Use lifecycle API for TestWatcher context
                            AllureUtils.attachScreenshotViaLifecycle(page, "Failure Screenshot - " + testName);
                            AllureUtils.attachFullPageScreenshotViaLifecycle(page, "Full Page Failure Screenshot - " + testName);
                            
                            // Attach additional context using AllureUtils helper
                            AllureUtils.attachTextViaLifecycle("Current URL", page.url());
                            AllureUtils.attachPageSourceViaLifecycle(page, "Page Source on Failure");
                            
                            watcherLogger.debug("Failure artifacts captured for: {}", testName);
                        }
                    }
                }
            } catch (Exception e) {
                watcherLogger.error("Failed to capture failure artifacts: {}", e.getMessage(), e);
            }

            // Attach error message to Allure using lifecycle API
            try {
                String errorMessage = cause.getMessage();
                if (errorMessage != null) {
                    AllureUtils.attachTextViaLifecycle("Error Message", errorMessage);
                }
                
                if (cause.getStackTrace() != null) {
                    StringBuilder stackTrace = new StringBuilder();
                    for (StackTraceElement element : cause.getStackTrace()) {
                        stackTrace.append(element.toString()).append("\n");
                    }
                    AllureUtils.attachTextViaLifecycle("Stack Trace", stackTrace.toString());
                }
            } catch (Exception e) {
                watcherLogger.error("Failed to attach error details: {}", e.getMessage());
            }
        }

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            watcherLogger.warn("Test ABORTED: {} - {}", context.getDisplayName(), cause.getMessage());
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            watcherLogger.info("Test DISABLED: {} - Reason: {}",
                    context.getDisplayName(),
                    reason.orElse("No reason provided"));
        }
    }
}
