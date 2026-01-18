package com.bdd_example.bdd.steps;

import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.HomePage;
import com.bdd_example.pages.LoginPage;
import com.bdd_example.pages.PricingPage;
import com.bdd_example.pages.SharedHostingPage;
import com.bdd_example.utils.AllureUtils;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

/**
 * Common step definitions shared across multiple feature files.
 * Contains Cucumber lifecycle hooks for browser management.
 */
public class CommonSteps extends BaseTest {

    private static final org.slf4j.Logger staticLogger = org.slf4j.LoggerFactory.getLogger(CommonSteps.class);
    
    protected static HomePage sharedHomePage;
    protected static LoginPage sharedLoginPage;
    protected static SharedHostingPage sharedSharedHostingPage;
    protected static PricingPage sharedPricingPage;
    private static boolean browserInitialized = false;

    /**
     * Cucumber Before hook - runs before each scenario.
     * Initializes browser on first run and clears state between scenarios.
     */
    @Before
    public void setUp() {
        if (!browserInitialized) {
            logger.info("Initializing browser for Cucumber tests");
            playwrightManager.init();
            
            // Register shutdown hook to close browser at JVM exit
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (browserInitialized) {
                    staticLogger.info("========================================");
                    staticLogger.info("Closing browser at JVM shutdown");
                    staticLogger.info("========================================");
                    try {
                        playwrightManager.quit();
                        staticLogger.info("Browser closed successfully");
                    } catch (Exception e) {
                        staticLogger.error("Error during browser cleanup: {}", e.getMessage(), e);
                    }
                }
            }));
            
            browserInitialized = true;
        }
        // Clear browser state between scenarios
        playwrightManager.clearBrowserState();
    }

    /**
     * Cucumber After hook - runs after each scenario.
     * Takes screenshots on failure if enabled.
     */
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && config.isScreenshotOnFailure()) {
            try {
                if (playwrightManager.isInitialized()) {
                    Page page = playwrightManager.getPage();
                    if (page != null && !page.isClosed()) {
                        logger.info("Scenario failed: {}. Capturing failure artifacts...", scenario.getName());
                        
                        // Attach failure screenshots and context
                        AllureUtils.attachScreenshotViaLifecycle(page, "Failure Screenshot - " + scenario.getName());
                        AllureUtils.attachFullPageScreenshotViaLifecycle(page, "Full Page Failure Screenshot - " + scenario.getName());
                        AllureUtils.attachTextViaLifecycle("Current URL", page.url());
                        AllureUtils.attachPageSourceViaLifecycle(page, "Page Source on Failure");
                        
                        logger.debug("Failure artifacts captured for: {}", scenario.getName());
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to capture failure artifacts: {}", e.getMessage(), e);
            }
        }
    }


    @Given("I am on the InMotion Hosting website")
    @Step("I am on the InMotion Hosting website")
    public void iAmOnTheInMotionHostingWebsite() {
        logger.info("Setting up test context for InMotion Hosting website");
        // Initialization can be done here if needed
    }

    @When("I navigate to the homepage")
    @Step("I navigate to the homepage")
    public void iNavigateToTheHomepage() {
        sharedHomePage = new HomePage();
        sharedHomePage.open();
        logger.info("Navigated to homepage");
    }
}
