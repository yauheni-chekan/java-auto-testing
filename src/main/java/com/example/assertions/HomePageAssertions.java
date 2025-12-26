package com.example.assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

import com.example.pages.HomePage;


/**
 * Utility class for page-level assertions.
 * Provides reusable assertion methods with auto-waiting using WaitUtils.
 * 
 * Note: Playwright Java doesn't have a built-in expect() API like JavaScript.
 * This class uses AssertJ for assertions combined with WaitUtils for auto-waiting.
 */
public class HomePageAssertions {

    private static final Logger logger = LoggerFactory.getLogger(HomePageAssertions.class);
    private final HomePage homePage;
    private final FooterComponentAssertions footerComponentAssertions;

    public HomePageAssertions(HomePage homePage) {
        this.homePage = homePage;
        this.footerComponentAssertions = new FooterComponentAssertions(homePage.footer());
    }

    /**
     * Asserts that the page title is correct.
     *
     */
    public void assertTitleIsCorrect() {
        String expectedTitle = "Best Web Hosting, VPS, Dedicated Servers for Business 2025 | InMotion Hosting";
        logger.debug("Asserting page title is correct");
        assertThat(homePage.getPage()).hasTitle(expectedTitle);
    }

    /**
     * Asserts that the page contains the expected navigation bar.
     *
     */
    public void assertContainsNavBar() {
        logger.debug("Asserting page contains navigation bar");
        assertVisible(homePage.header().getHeaderContainer());
        assertVisible(homePage.header().getLogo());
        assertVisible(homePage.header().getVpsHostingLink());
        assertVisible(homePage.header().getDedicatedServersDropdown());
        assertVisible(homePage.header().getWordPressHostingDropdown());
        assertVisible(homePage.header().getLoginButton());
    }

    /**
     * Asserts that the page contains the expected footer.
     *
     */
    public void assertContainsFooter() {
        logger.debug("Asserting page contains footer");
        assertVisible(homePage.footer().getFooterContainer());
        assertVisible(homePage.footer().getCopyrightTextLocator());
        footerComponentAssertions.assertContainsText("Copyright © 2002-");
    }

    /**
     * Asserts that the page load time is within the expected range.
     *
     * @param loadTime Load time in milliseconds
     */
    public void assertLoadTime(long loadTime) {
        logger.debug("Asserting page load time is within the expected range");
        assertThat(loadTime).isLessThan(30000);
    }
    /**
     * Asserts that the page URL matches the expected pattern.
     *
     * @param urlPattern Expected URL pattern (regex)
     */
    public void assertUrlMatches(String urlPattern) {
        logger.debug("Asserting URL matches pattern: {}", urlPattern);
        assertThat(homePage.getPage()).hasURL(urlPattern);
    }

    /**
     * Asserts that a locator is visible.
     * Uses WaitUtils to wait for visibility before asserting.
     *
     * @param locator Locator to check
     */
    public void assertVisible(Locator locator) {
        logger.debug("Asserting element is visible");
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).isVisible();
    }

    /**
     * Asserts that a locator is not visible.
     *
     * @param locator Locator to check
     */
    public void assertNotVisible(Locator locator) {
        logger.debug("Asserting element is not visible");
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        assertThat(locator).isHidden();
    }

    /**
     * Asserts that a locator contains the expected text.
     * Uses WaitUtils to wait for visibility before checking text.
     *
     * @param locator Locator to check
     * @param expectedText Expected text
     */
    public void assertContainsText(Locator locator, String expectedText) {
        logger.debug("Asserting element contains text: {}", expectedText);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).hasText(expectedText);
    }

    /**
     * Asserts that a locator has the exact text.
     * Uses WaitUtils to wait for visibility before checking text.
     *
     * @param locator Locator to check
     * @param expectedText Expected exact text
     */
    public void assertHasText(Locator locator, String expectedText) {
        logger.debug("Asserting element has text: {}", expectedText);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).hasText(expectedText);
    }

    /**
     * Asserts that a locator is enabled.
     * Uses WaitUtils to wait for visibility before checking state.
     *
     * @param locator Locator to check
     */
    public void assertEnabled(Locator locator) {
        logger.debug("Asserting element is enabled");
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).isEnabled();
    }

    /**
     * Asserts that a locator is disabled.
     * Uses WaitUtils to wait for visibility before checking state.
     *
     * @param locator Locator to check
     */
    public void assertDisabled(Locator locator) {
        logger.debug("Asserting element is disabled");
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).isDisabled();
    }

    /**
     * Asserts that a locator has the expected value.
     * Uses WaitUtils to wait for visibility before checking value.
     *
     * @param locator Locator to check
     * @param expectedValue Expected value
     */
    public void assertHasValue(Locator locator, String expectedValue) {
        logger.debug("Asserting element has value: {}", expectedValue);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).hasValue(expectedValue);
    }

    /**
     * Asserts that a checkbox/radio is checked.
     * Uses WaitUtils to wait for visibility before checking state.
     *
     * @param locator Locator to check
     */
    public void assertChecked(Locator locator) {
        logger.debug("Asserting element is checked");
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).isChecked();
    }

    /**
     * Asserts that a checkbox/radio is not checked.
     * Uses WaitUtils to wait for visibility before checking state.
     *
     * @param locator Locator to check
     */
    public void assertNotChecked(Locator locator) {
        logger.debug("Asserting element is not checked");
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).not().isChecked();
    }

    /**
     * Asserts that a locator has the expected count.
     *
     * @param locator Locator to check
     * @param expectedCount Expected count
     */
    public void assertCount(Locator locator, int expectedCount) {
        logger.debug("Asserting element count is: {}", expectedCount);
        assertThat(locator).hasCount(expectedCount);
    }

    /**
     * Asserts that a locator has the expected attribute value.
     * Uses WaitUtils to wait for visibility before checking attribute.
     *
     * @param locator Locator to check
     * @param attribute Attribute name
     * @param expectedValue Expected attribute value
     */
    public void assertHasAttribute(Locator locator, String attribute, String expectedValue) {
        logger.debug("Asserting element has attribute {} with value: {}", attribute, expectedValue);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).hasAttribute(attribute, expectedValue);
    }
}