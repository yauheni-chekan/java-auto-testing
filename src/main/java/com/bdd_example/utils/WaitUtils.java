package com.bdd_example.utils;

import com.bdd_example.config.Configuration;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class providing various wait methods for Playwright automation.
 */
public final class WaitUtils {

    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);
    private static final Configuration config = Configuration.getInstance();

    private WaitUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Waits for the page to reach the DOM content loaded state.
     *
     * @param page Page instance
     */
    public static void waitForDomContentLoaded(Page page) {
        logger.debug("Waiting for DOM content loaded");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    /**
     * Waits for an element to be visible on the page.
     *
     * @param locator Element locator
     */
    public static void waitForVisible(Locator locator) {
        waitForVisible(locator, config.getElementTimeout());
    }

    /**
     * Waits for an element to be visible on the page with custom timeout.
     *
     * @param locator   Element locator
     * @param timeoutMs Timeout in milliseconds
     */
    public static void waitForVisible(Locator locator, int timeoutMs) {
        logger.debug("Waiting for element to be visible (timeout: {}ms)", timeoutMs);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMs));
    }
}

