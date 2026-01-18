package com.bdd_example.bdd.steps;

import com.bdd_example.assertions.LoginPageAssertions;
import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.HomePage;
import com.bdd_example.pages.LoginPage;
import com.bdd_example.pages.PricingPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;

/**
 * Step definitions for Login Navigation scenarios.
 */
public class LoginNavigationSteps extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;
    private LoginPageAssertions loginPageAssertions;

    @And("I hover over the {string} link in the header")
    @Step("I hover over the {string} link in the header")
    public void iHoverOverTheLinkInTheHeader(String linkName) {
        homePage = CommonSteps.sharedHomePage;
        if (homePage == null) {
            throw new IllegalStateException("HomePage not initialized. Please navigate to homepage first.");
        }
        if ("Login".equalsIgnoreCase(linkName)) {
            homePage.header().hoverLogin();
            logger.info("Hovered over {} link in the header", linkName);
        } else {
            throw new IllegalArgumentException("Unknown link name for hover: " + linkName);
        }
    }

    @And("I click the {string} link in the header")
    @Step("I click the {string} link in the header")
    public void iClickTheLinkInTheHeader(String linkName) {
        homePage = CommonSteps.sharedHomePage;
        if (homePage == null) {
            throw new IllegalStateException("HomePage not initialized. Please navigate to homepage first.");
        }
        if ("Login".equalsIgnoreCase(linkName)) {
            loginPage = homePage.header().clickLogin();
            loginPageAssertions = new LoginPageAssertions(loginPage);
            loginPageAssertions.assertPageIsLoaded();
            logger.info("Navigated to Login page");
            // Store in shared context for other step classes
            CommonSteps.sharedLoginPage = loginPage;
            logger.info("Clicked on {} link in the header", linkName);
        } else if ("Pricing".equalsIgnoreCase(linkName)) {
            PricingPage pricingPage = homePage.header().navigateToPricing();
            CommonSteps.sharedPricingPage = pricingPage;
            logger.info("Clicked on {} link in the header", linkName);
        } else {
            throw new IllegalArgumentException("Unknown link name: " + linkName);
        }
    }

    @Then("I should be navigated to the Login page")
    @Step("I should be navigated to the Login page")
    public void iShouldBeNavigatedToTheLoginPage() {
        // Check shared context if local loginPage is null
        if (loginPage == null && CommonSteps.sharedLoginPage != null) {
            loginPage = CommonSteps.sharedLoginPage;
        }
        if (loginPage == null) {
            throw new IllegalStateException("LoginPage not initialized. Please click Login link first.");
        }
        logger.info("Navigated to Login page");
    }

    @And("the Login page should be loaded")
    @Step("the Login page should be loaded")
    public void theLoginPageShouldBeLoaded() {
        // Check shared context if local loginPage is null
        if (loginPage == null && CommonSteps.sharedLoginPage != null) {
            loginPage = CommonSteps.sharedLoginPage;
        }
        if (loginPage == null) {
            loginPage = new LoginPage();
            CommonSteps.sharedLoginPage = loginPage;
        }
        if (loginPageAssertions == null) {
            loginPageAssertions = new LoginPageAssertions(loginPage);
        }
        loginPageAssertions.assertPageIsLoaded();
        logger.info("Login page loaded successfully");
    }
}
