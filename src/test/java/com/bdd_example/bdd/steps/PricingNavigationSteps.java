package com.bdd_example.bdd.steps;

import com.bdd_example.assertions.PricingPageAssertions;
import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.HomePage;
import com.bdd_example.pages.PricingPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;

/**
 * Step definitions for Pricing Navigation scenarios.
 */
public class PricingNavigationSteps extends BaseTest {
    
    private HomePage homePage;
    private PricingPage pricingPage;
    private PricingPageAssertions pricingPageAssertions;

    // Note: "I click the {string} link in the header" step is handled by LoginNavigationSteps
    // to avoid duplicate step definitions. This class handles Pricing-specific assertions.
    
    // This method is called after LoginNavigationSteps handles the click
    private void initializePricingPage() {
        homePage = CommonSteps.sharedHomePage;
        if (homePage == null) {
            throw new IllegalStateException("HomePage not initialized. Please navigate to homepage first.");
        }
        // Get pricing page from navigation - it should already be navigated by LoginNavigationSteps
        pricingPage = new PricingPage();
        pricingPageAssertions = new PricingPageAssertions(pricingPage);
    }

    @Then("I should be navigated to the Pricing page")
    @Step("I should be navigated to the Pricing page")
    public void iShouldBeNavigatedToThePricingPage() {
        // Navigation is handled by LoginNavigationSteps
        initializePricingPage();
        logger.info("Navigated to Pricing page");
    }

    @And("the Pricing page should be loaded")
    @Step("the Pricing page should be loaded")
    public void thePricingPageShouldBeLoaded() {
        if (pricingPageAssertions == null) {
            initializePricingPage();
        }
        pricingPageAssertions.assertPageIsLoaded();
        logger.info("Pricing page loaded successfully");
    }

    @And("the Pricing page title should be correct")
    @Step("the Pricing page title should be correct")
    public void thePricingPageTitleShouldBeCorrect() {
        pricingPageAssertions.assertTitleIsCorrect();
        logger.info("Pricing page title verified");
    }
}
