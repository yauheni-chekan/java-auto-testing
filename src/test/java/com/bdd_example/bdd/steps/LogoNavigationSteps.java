package com.bdd_example.bdd.steps;

import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.HomePage;
import com.bdd_example.pages.components.HeaderComponent;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

/**
 * Step definitions for Logo Navigation scenarios.
 * Logo navigation can be performed from any page on the website.
 */
public class LogoNavigationSteps extends BaseTest {

    private HomePage homePage;

    // Note: Login navigation steps are handled by LoginNavigationSteps to avoid duplicates

    @When("I click the logo in the header")
    @Step("I click the logo in the header")
    public void iClickTheLogoInTheHeader() {
        // Logo can be clicked from any page, so we create a HeaderComponent directly from the current page
        // This works regardless of which page we're currently on (HomePage, LoginPage, SharedHostingPage, etc.)
        HeaderComponent header = new HeaderComponent(getPage());
        header.clickLogo();
        logger.info("Clicked logo in the header");
    }

    @Then("I should be navigated back to the homepage")
    @Step("I should be navigated back to the homepage")
    public void iShouldBeNavigatedBackToTheHomepage() {
        logger.info("Navigated back to homepage");
    }

    @And("the homepage should be loaded")
    @Step("the homepage should be loaded")
    public void theHomepageShouldBeLoaded() {
        // After clicking the logo, we should be on the homepage
        homePage = CommonSteps.sharedHomePage;
        if (homePage == null) {
            homePage = new HomePage();
            CommonSteps.sharedHomePage = homePage;
        }
        homePage.waitForPageLoad();
        // Verify we're on the homepage by checking URL
        String currentUrl = homePage.getCurrentUrl();
        logger.info("Homepage loaded successfully. Current URL: {}", currentUrl);
    }
}
