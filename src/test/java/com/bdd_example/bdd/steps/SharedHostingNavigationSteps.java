package com.bdd_example.bdd.steps;

import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.LoginPage;
import com.bdd_example.pages.SharedHostingPage;

import io.cucumber.java.en.When;
import io.qameta.allure.Step;

/**
 * Step definitions for Shared Hosting Page Navigation scenarios.
 */
public class SharedHostingNavigationSteps extends BaseTest {

    private SharedHostingPage sharedHostingPage;
    private LoginPage loginPage;

    // Note: All Hosting dropdown steps are handled by AllHostingNavigationSteps to
    // avoid duplicates
    // This class only handles Shared Hosting page specific steps

    @When("I click the {string} link on the Shared Hosting page")
    @Step("I click the {string} link on the Shared Hosting page")
    public void iClickTheLinkOnTheSharedHostingPage(String linkName) {
        // Get sharedHostingPage from AllHostingNavigationSteps context if available
        if (sharedHostingPage == null && CommonSteps.sharedSharedHostingPage != null) {
            sharedHostingPage = CommonSteps.sharedSharedHostingPage;
        } else if (sharedHostingPage == null) {
            sharedHostingPage = new SharedHostingPage();
        }
        if ("Login".equalsIgnoreCase(linkName)) {
            loginPage = sharedHostingPage.clickLoginLink();
            // Store loginPage in shared context for other step classes to use
            CommonSteps.sharedLoginPage = loginPage;
            logger.info("Clicked {} link on the Shared Hosting page", linkName);
        } else {
            throw new IllegalArgumentException("Unknown link name: " + linkName);
        }
    }

    // Note: Login page navigation steps are handled by LoginNavigationSteps to
    // avoid duplicates
}
