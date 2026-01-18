package com.example.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.config.Configuration;
import com.example.core.PlaywrightManager;
import com.example.utils.AllureUtils;
import com.example.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import io.qameta.allure.Step;

/**
 * Base class for all page objects.
 * Provides common methods and utilities for page interactions.
 */
public abstract class BasePage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Page page;
    protected final Configuration config;

    /**
     * Creates a new page object using the singleton PlaywrightManager instance.
     */
    protected BasePage() {
        this.page = PlaywrightManager.getInstance().getPage();
        this.config = Configuration.getInstance();
    }

    /**
     * Creates a new page object with a specific page instance.
     *
     * @param page Page instance to use
     */
    protected BasePage(Page page) {
        this.page = page;
        this.config = Configuration.getInstance();
    }

    /**
     * Gets the expected URL pattern for this page.
     * Override in subclasses to specify the URL pattern.
     *
     * @return URL pattern (can be regex)
     */
    protected abstract String getUrlPattern();

    /**
     * Gets the page title or a unique element to verify page load.
     * Override in subclasses to specify the page verification element.
     *
     * @return Locator for page verification
     */
    protected abstract Locator getPageIdentifier();

    // =========================================================================
    // Navigation Methods
    // =========================================================================

    /**
     * Navigates to the specified URL.
     *
     * @param url URL to navigate to
     */
    @Step("Navigate to: {url}")
    public void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        page.navigate(url);
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to the base URL.
     */
    @Step("Navigate to base URL")
    public void navigateToBaseUrl() {
        navigateTo(config.getBaseUrl());
    }

    /**
     * Refreshes the current page.
     */
    @Step("Refresh page")
    public void refresh() {
        logger.debug("Refreshing page");
        page.reload();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates back in browser history.
     */
    @Step("Navigate back")
    public void goBack() {
        logger.debug("Navigating back");
        page.goBack();
    }

    /**
     * Navigates forward in browser history.
     */
    @Step("Navigate forward")
    public void goForward() {
        logger.debug("Navigating forward");
        page.goForward();
    }

    // =========================================================================
    // Page Information Methods
    // =========================================================================

    /**
     * Gets the current page URL.
     *
     * @return Current URL
     */
    public String getCurrentUrl() {
        return page.url();
    }

    /**
     * Gets the current page title.
     *
     * @return Page title
     */
    public String getPageTitle() {
        return page.title();
    }

    /**
     * Checks if the current page matches the expected URL pattern.
     *
     * @return true if on expected page
     */
    public boolean isOnPage() {
        String currentUrl = getCurrentUrl();
        String pattern = getUrlPattern();
        boolean matches = currentUrl.matches(pattern) || currentUrl.contains(pattern);
        logger.debug("Page check - Current: {}, Pattern: {}, Matches: {}", currentUrl, pattern, matches);
        return matches;
    }

    /**
     * Waits for the page to be fully loaded and verified.
     */
    @Step("Wait for page to load")
    public void waitForPageLoad() {
        logger.debug("Waiting for page to load");
        WaitUtils.waitForDomContentLoaded(page);
        WaitUtils.waitForVisible(getPageIdentifier());
    }

    // =========================================================================
    // Element Interaction Methods
    // =========================================================================

    /**
     * Clicks on an element.
     *
     * @param locator Element locator
     */
    @Step("Click on element")
    protected void click(Locator locator) {
        logger.debug("Clicking element");
        WaitUtils.waitForVisible(locator);
        locator.click();
    }

    /**
     * Double-clicks on an element.
     *
     * @param locator Element locator
     */
    @Step("Double-click on element")
    protected void doubleClick(Locator locator) {
        logger.debug("Double-clicking element");
        WaitUtils.waitForVisible(locator);
        locator.dblclick();
    }

    /**
     * Types text into an element.
     *
     * @param locator Element locator
     * @param text    Text to type
     */
    @Step("Type text: {text}")
    protected void type(Locator locator, String text) {
        logger.debug("Typing text: {}", text);
        WaitUtils.waitForVisible(locator);
        locator.fill(text);
    }

    /**
     * Clears an input element and types new text.
     *
     * @param locator Element locator
     * @param text    Text to type
     */
    @Step("Clear and type: {text}")
    protected void clearAndType(Locator locator, String text) {
        logger.debug("Clearing and typing: {}", text);
        WaitUtils.waitForVisible(locator);
        locator.clear();
        locator.fill(text);
    }

    /**
     * Gets the text content of an element.
     *
     * @param locator Element locator
     * @return Element text content
     */
    protected String getText(Locator locator) {
        WaitUtils.waitForVisible(locator);
        return locator.textContent();
    }

    /**
     * Gets the inner text of an element.
     *
     * @param locator Element locator
     * @return Element inner text
     */
    protected String getInnerText(Locator locator) {
        WaitUtils.waitForVisible(locator);
        return locator.innerText();
    }

    /**
     * Gets an attribute value from an element.
     *
     * @param locator   Element locator
     * @param attribute Attribute name
     * @return Attribute value
     */
    protected String getAttribute(Locator locator, String attribute) {
        WaitUtils.waitForVisible(locator);
        return locator.getAttribute(attribute);
    }

    /**
     * Gets the value of an input element.
     *
     * @param locator Element locator
     * @return Input value
     */
    protected String getInputValue(Locator locator) {
        WaitUtils.waitForVisible(locator);
        return locator.inputValue();
    }

    /**
     * Checks if an element is visible.
     *
     * @param locator Element locator
     * @return true if visible
     */
    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    /**
     * Checks if an element is enabled.
     *
     * @param locator Element locator
     * @return true if enabled
     */
    protected boolean isEnabled(Locator locator) {
        return locator.isEnabled();
    }

    /**
     * Checks if a checkbox or radio button is checked.
     *
     * @param locator Element locator
     * @return true if checked
     */
    protected boolean isChecked(Locator locator) {
        return locator.isChecked();
    }

    /**
     * Hovers over an element.
     *
     * @param locator Element locator
     */
    @Step("Hover over element")
    protected void hover(Locator locator) {
        logger.debug("Hovering over element");
        WaitUtils.waitForVisible(locator);
        locator.hover();
    }

    /**
     * Scrolls an element into view.
     *
     * @param locator Element locator
     */
    @Step("Scroll element into view")
    protected void scrollIntoView(Locator locator) {
        logger.debug("Scrolling element into view");
        locator.scrollIntoViewIfNeeded();
    }

    /**
     * Selects an option from a dropdown by visible text.
     *
     * @param locator Element locator
     * @param text    Option text
     */
    @Step("Select option: {text}")
    protected void selectByText(Locator locator, String text) {
        logger.debug("Selecting by text: {}", text);
        WaitUtils.waitForVisible(locator);
        locator.selectOption(text);
    }

    /**
     * Selects an option from a dropdown by value.
     *
     * @param locator Element locator
     * @param value   Option value
     */
    @Step("Select option by value: {value}")
    protected void selectByValue(Locator locator, String value) {
        logger.debug("Selecting by value: {}", value);
        WaitUtils.waitForVisible(locator);
        locator.selectOption(new com.microsoft.playwright.options.SelectOption().setValue(value));
    }

    // =========================================================================
    // Screenshot and Debugging Methods
    // =========================================================================

    /**
     * Takes a screenshot and attaches it to the Allure report.
     *
     * @param name Screenshot name
     */
    public void takeScreenshot(String name) {
        AllureUtils.attachScreenshot(page, name);
    }

    /**
     * Takes a full page screenshot and attaches it to the Allure report.
     *
     * @param name Screenshot name
     */
    public void takeFullPageScreenshot(String name) {
        AllureUtils.attachFullPageScreenshot(page, name);
    }

    /**
     * Attaches the current page source to the Allure report.
     */
    public void attachPageSource() {
        AllureUtils.attachPageSource(page, "Page Source");
    }

    // =========================================================================
    // JavaScript Execution Methods
    // =========================================================================

    /**
     * Executes JavaScript on the page.
     *
     * @param script JavaScript code
     * @return Result of script execution
     */
    protected Object executeScript(String script) {
        logger.debug("Executing script: {}", script);
        return page.evaluate(script);
    }

    /**
     * Executes JavaScript on a specific element.
     *
     * @param locator Element locator
     * @param script  JavaScript code (element available as 'element' parameter)
     * @return Result of script execution
     */
    protected Object executeScriptOnElement(Locator locator, String script) {
        logger.debug("Executing script on element: {}", script);
        return locator.evaluate(script);
    }

    /**
     * Scrolls the page to the bottom.
     */
    @Step("Scroll to page bottom")
    public void scrollToBottom() {
        logger.debug("Scrolling to bottom of page");
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Scrolls the page to the top.
     */
    @Step("Scroll to page top")
    public void scrollToTop() {
        logger.debug("Scrolling to top of page");
        page.evaluate("window.scrollTo(0, 0)");
    }

    // =========================================================================
    // Frame and Window Methods
    // =========================================================================

    /**
     * Switches to a frame by name or URL.
     *
     * @param nameOrUrl Frame name or URL
     * @return Frame Page instance
     */
    protected com.microsoft.playwright.Frame switchToFrame(String nameOrUrl) {
        logger.debug("Switching to frame: {}", nameOrUrl);
        return page.frame(nameOrUrl);
    }

    /**
     * Gets the underlying Playwright Page instance.
     *
     * @return Page instance
     */
    public Page getPage() {
        return page;
    }
}
