package com.bdd_example.bdd.steps;

import com.bdd_example.assertions.HomePageAssertions;
import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.HomePage;
import com.microsoft.playwright.Locator;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;

/**
 * Step definitions for Header Navigation scenarios.
 */
public class HeaderNavigationSteps extends BaseTest {
    
    private HomePage homePage;
    private HomePageAssertions homePageAssertions;

    @Then("the {string} link should be visible in the header")
    @Step("the {string} link should be visible in the header")
    public void theLinkShouldBeVisibleInTheHeader(String linkName) {
        homePage = CommonSteps.sharedHomePage;
        if (homePage == null) {
            throw new IllegalStateException("HomePage not initialized. Please navigate to homepage first.");
        }
        homePageAssertions = new HomePageAssertions(homePage);
        
        Locator linkLocator = getLinkLocator(linkName);
        if (linkLocator != null) {
            homePageAssertions.assertVisible(linkLocator);
            logger.info("Verified that {} link is visible in the header", linkName);
        } else {
            throw new IllegalArgumentException("Unknown link name: " + linkName);
        }
    }

    /**
     * Gets the locator for the specified link name.
     *
     * @param linkName The name of the link
     * @return The locator for the link, or null if not found
     */
    private Locator getLinkLocator(String linkName) {
        switch (linkName) {
            case "VPS Hosting":
                return homePage.header().getVpsHostingLink();
            case "Dedicated Servers":
                return homePage.header().getDedicatedServersDropdown();
            case "Pricing":
                return homePage.header().getPricingLink();
            default:
                return null;
        }
    }
}
