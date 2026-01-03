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

    @And("I click the {string} link in the header")
    @Step("I click the {string} link in the header")
    public void iClickTheLinkInTheHeader(String linkName) {
        homePage = CommonSteps.sharedHomePage;
        if (homePage == null) {
            throw new IllegalStateException("HomePage not initialized. Please navigate to homepage first.");
        }
        if ("Pricing".equalsIgnoreCase(linkName)) {
            pricingPage = homePage.header().navigateToPricing();
            pricingPageAssertions = new PricingPageAssertions(pricingPage);
            logger.info("Clicked on Pricing link");
        }
    }

    @Then("I should be navigated to the Pricing page")
    @Step("I should be navigated to the Pricing page")
    public void iShouldBeNavigatedToThePricingPage() {
        // Navigation is already done in the When step
        logger.info("Navigated to Pricing page");
    }

    @And("the Pricing page should be loaded")
    @Step("the Pricing page should be loaded")
    public void thePricingPageShouldBeLoaded() {
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
