package com.bdd_example.assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdd_example.pages.SharedHostingPage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Utility class for Shared Hosting Page assertions.
 * Provides reusable assertion methods with auto-waiting.
 */
public class SharedHostingPageAssertions {

    private static final Logger logger = LoggerFactory.getLogger(SharedHostingPageAssertions.class);
    private final SharedHostingPage sharedHostingPage;

    public SharedHostingPageAssertions(SharedHostingPage sharedHostingPage) {
        this.sharedHostingPage = sharedHostingPage;
    }

    /**
     * Asserts that the Shared Hosting page is loaded.
     */
    public void assertPageIsLoaded() {
        logger.debug("Asserting Shared Hosting page is loaded");
        assertThat(sharedHostingPage.isOnPage()).isTrue();
    }

    /**
     * Asserts that a locator is visible.
     *
     * @param locator Locator to check
     */
    public void assertVisible(Locator locator) {
        logger.debug("Asserting element is visible");
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).isVisible();
    }
}
