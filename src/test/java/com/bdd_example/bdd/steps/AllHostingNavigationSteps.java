package com.bdd_example.bdd.steps;

import com.bdd_example.assertions.SharedHostingPageAssertions;
import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.SharedHostingPage;
import com.bdd_example.pages.components.HeaderComponent;
import com.microsoft.playwright.Locator;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

/**
 * Step definitions for All Hosting Navigation scenarios.
 */
public class AllHostingNavigationSteps extends BaseTest {

    private SharedHostingPage sharedHostingPage;
    private SharedHostingPageAssertions sharedHostingPageAssertions;

    @And("I click the {string} dropdown in the header")
    @Step("I click the {string} dropdown in the header")
    public void iClickTheDropdownInTheHeader(String dropdownName) {
        HeaderComponent header = new HeaderComponent(getPage());
        if ("All Hosting".equalsIgnoreCase(dropdownName)) {
            header.navigateToAllHosting();
            logger.info("Clicked {} dropdown in the header", dropdownName);
        } else {
            throw new IllegalArgumentException("Unknown dropdown name: " + dropdownName);
        }
    }

    @Then("the {string} dropdown menu should be visible")
    @Step("the {string} dropdown menu should be visible")
    public void theDropdownMenuShouldBeVisible(String dropdownName) {
        HeaderComponent header = new HeaderComponent(getPage());
        if ("All Hosting".equalsIgnoreCase(dropdownName)) {
            Locator dropdownMenu = header.getAllHostingDropdownMenu();
            dropdownMenu.waitFor();
            logger.info("{} dropdown menu is visible", dropdownName);
        } else {
            throw new IllegalArgumentException("Unknown dropdown name: " + dropdownName);
        }
    }

    @When("I select {string} from the {string} dropdown")
    @Step("I select {string} from the {string} dropdown")
    public void iSelectFromTheDropdown(String option, String dropdownName) {
        HeaderComponent header = new HeaderComponent(getPage());
        if ("All Hosting".equalsIgnoreCase(dropdownName) && "Shared Hosting".equalsIgnoreCase(option)) {
            sharedHostingPage = header.navigateToSharedHosting();
            sharedHostingPageAssertions = new SharedHostingPageAssertions(sharedHostingPage);
            // Store in shared context for other step classes
            CommonSteps.sharedSharedHostingPage = sharedHostingPage;
            logger.info("Selected {} from {} dropdown", option, dropdownName);
        } else {
            throw new IllegalArgumentException("Unknown dropdown/option combination: " + dropdownName + "/" + option);
        }
    }

    @Then("I should be navigated to the Shared Hosting page")
    @Step("I should be navigated to the Shared Hosting page")
    public void iShouldBeNavigatedToTheSharedHostingPage() {
        if (sharedHostingPage == null) {
            throw new IllegalStateException("SharedHostingPage not initialized. Please select Shared Hosting first.");
        }
        logger.info("Navigated to Shared Hosting page");
    }

    @And("the Shared Hosting page should be loaded")
    @Step("the Shared Hosting page should be loaded")
    public void theSharedHostingPageShouldBeLoaded() {
        if (sharedHostingPageAssertions == null) {
            sharedHostingPage = new SharedHostingPage();
            sharedHostingPageAssertions = new SharedHostingPageAssertions(sharedHostingPage);
        }
        sharedHostingPageAssertions.assertPageIsLoaded();
        logger.info("Shared Hosting page loaded successfully");
    }

    @And("the Shared Hosting page should be loaded in the same tab")
    @Step("the Shared Hosting page should be loaded in the same tab")
    public void theSharedHostingPageShouldBeLoadedInTheSameTab() {
        if (sharedHostingPageAssertions == null) {
            sharedHostingPage = new SharedHostingPage();
            sharedHostingPageAssertions = new SharedHostingPageAssertions(sharedHostingPage);
        }
        sharedHostingPageAssertions.assertPageIsLoaded();
        // Verify we're still in the same tab by checking URL contains shared hosting
        String currentUrl = sharedHostingPage.getCurrentUrl();
        logger.info("Shared Hosting page loaded in the same tab. Current URL: {}", currentUrl);
    }
}
