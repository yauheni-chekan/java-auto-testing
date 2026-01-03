package com.bdd_example.bdd.steps;

import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.HomePage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

/**
 * Common step definitions shared across multiple feature files.
 */
public class CommonSteps extends BaseTest {

    protected static HomePage sharedHomePage;
    private static boolean browserInitialized = false;

    @Before
    public void setUp() {
        if (!browserInitialized) {
            logger.info("Initializing browser for Cucumber tests");
            playwrightManager.init();
            browserInitialized = true;
        }
        // Clear browser state between scenarios
        playwrightManager.clearBrowserState();
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
