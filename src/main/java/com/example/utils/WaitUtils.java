package com.example.utils;

import com.example.config.Configuration;
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
     * Waits for the page to fully load (including all resources).
     *
     * @param page Page instance
     */
    public static void waitForPageLoad(Page page) {
        logger.debug("Waiting for page load complete");
        page.waitForLoadState(LoadState.LOAD);
    }

    /**
     * Waits for the page network to be idle.
     *
     * @param page Page instance
     */
    public static void waitForNetworkIdle(Page page) {
        logger.debug("Waiting for network idle");
        page.waitForLoadState(LoadState.NETWORKIDLE);
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

    /**
     * Waits for an element to be hidden or removed from the DOM.
     *
     * @param locator Element locator
     */
    public static void waitForHidden(Locator locator) {
        waitForHidden(locator, config.getElementTimeout());
    }

    /**
     * Waits for an element to be hidden with custom timeout.
     *
     * @param locator   Element locator
     * @param timeoutMs Timeout in milliseconds
     */
    public static void waitForHidden(Locator locator, int timeoutMs) {
        logger.debug("Waiting for element to be hidden (timeout: {}ms)", timeoutMs);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeoutMs));
    }

    /**
     * Waits for an element to be attached to the DOM.
     *
     * @param locator Element locator
     */
    public static void waitForAttached(Locator locator) {
        waitForAttached(locator, config.getElementTimeout());
    }

    /**
     * Waits for an element to be attached to the DOM with custom timeout.
     *
     * @param locator   Element locator
     * @param timeoutMs Timeout in milliseconds
     */
    public static void waitForAttached(Locator locator, int timeoutMs) {
        logger.debug("Waiting for element to be attached (timeout: {}ms)", timeoutMs);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(timeoutMs));
    }

    /**
     * Waits for an element to be detached from the DOM.
     *
     * @param locator Element locator
     */
    public static void waitForDetached(Locator locator) {
        waitForDetached(locator, config.getElementTimeout());
    }

    /**
     * Waits for an element to be detached from the DOM with custom timeout.
     *
     * @param locator   Element locator
     * @param timeoutMs Timeout in milliseconds
     */
    public static void waitForDetached(Locator locator, int timeoutMs) {
        logger.debug("Waiting for element to be detached (timeout: {}ms)", timeoutMs);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.DETACHED)
                .setTimeout(timeoutMs));
    }

    /**
     * Waits for a URL to contain a specific substring.
     *
     * @param page        Page instance
     * @param urlFragment URL substring to wait for
     */
    public static void waitForUrlContains(Page page, String urlFragment) {
        waitForUrlContains(page, urlFragment, config.getNavigationTimeout());
    }

    /**
     * Waits for a URL to contain a specific substring with custom timeout.
     *
     * @param page        Page instance
     * @param urlFragment URL substring to wait for
     * @param timeoutMs   Timeout in milliseconds
     */
    public static void waitForUrlContains(Page page, String urlFragment, int timeoutMs) {
        logger.debug("Waiting for URL to contain: {} (timeout: {}ms)", urlFragment, timeoutMs);
        page.waitForURL(url -> url.contains(urlFragment),
                new Page.WaitForURLOptions().setTimeout(timeoutMs));
    }

    /**
     * Waits for a URL to match a specific pattern.
     *
     * @param page       Page instance
     * @param urlPattern URL pattern (regex)
     */
    public static void waitForUrlMatches(Page page, String urlPattern) {
        waitForUrlMatches(page, urlPattern, config.getNavigationTimeout());
    }

    /**
     * Waits for a URL to match a specific pattern with custom timeout.
     *
     * @param page       Page instance
     * @param urlPattern URL pattern (regex)
     * @param timeoutMs  Timeout in milliseconds
     */
    public static void waitForUrlMatches(Page page, String urlPattern, int timeoutMs) {
        logger.debug("Waiting for URL to match pattern: {} (timeout: {}ms)", urlPattern, timeoutMs);
        page.waitForURL(urlPattern, new Page.WaitForURLOptions().setTimeout(timeoutMs));
    }

    /**
     * Waits for a specific amount of time (use sparingly).
     *
     * @param milliseconds Time to wait in milliseconds
     */
    public static void sleep(long milliseconds) {
        logger.debug("Sleeping for {}ms", milliseconds);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Sleep interrupted", e);
        }
    }

    /**
     * Waits for a JavaScript condition to be true.
     *
     * @param page       Page instance
     * @param expression JavaScript expression that returns boolean
     */
    public static void waitForCondition(Page page, String expression) {
        waitForCondition(page, expression, config.getElementTimeout());
    }

    /**
     * Waits for a JavaScript condition to be true with custom timeout.
     *
     * @param page       Page instance
     * @param expression JavaScript expression that returns boolean
     * @param timeoutMs  Timeout in milliseconds
     */
    public static void waitForCondition(Page page, String expression, int timeoutMs) {
        logger.debug("Waiting for JS condition: {} (timeout: {}ms)", expression, timeoutMs);
        page.waitForFunction(expression,
                new Page.WaitForFunctionOptions().setTimeout(timeoutMs));
    }

    /**
     * Waits for element text to contain expected value.
     *
     * @param locator      Element locator
     * @param expectedText Expected text substring
     * @param timeoutMs    Timeout in milliseconds
     */
    public static void waitForTextContains(Locator locator, String expectedText, int timeoutMs) {
        logger.debug("Waiting for text to contain: '{}' (timeout: {}ms)", expectedText, timeoutMs);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            String actualText = locator.textContent();
            if (actualText != null && actualText.contains(expectedText)) {
                return;
            }
            sleep(100);
        }
        throw new RuntimeException(
                String.format("Text did not contain '%s' within %dms", expectedText, timeoutMs));
    }
}

