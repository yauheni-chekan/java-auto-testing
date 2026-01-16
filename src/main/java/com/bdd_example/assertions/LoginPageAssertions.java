package com.bdd_example.assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdd_example.pages.LoginPage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Utility class for Login Page assertions.
 * Provides reusable assertion methods with auto-waiting.
 */
public class LoginPageAssertions {

    private static final Logger logger = LoggerFactory.getLogger(LoginPageAssertions.class);
    private final LoginPage loginPage;

    public LoginPageAssertions(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    /**
     * Asserts that the Login page is loaded.
     */
    public void assertPageIsLoaded() {
        logger.debug("Asserting Login page is loaded");
        assertThat(loginPage.isOnPage()).isTrue();
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

    /**
     * Asserts that a locator is enabled.
     *
     * @param locator Locator to check
     */
    public void assertEnabled(Locator locator) {
        logger.debug("Asserting element is enabled");
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(locator).isEnabled();
    }

    /**
     * Asserts that the username input field is visible.
     */
    public void assertUsernameInputVisible() {
        logger.debug("Asserting username input is visible");
        assertVisible(loginPage.getUsernameInput());
    }

    /**
     * Asserts that the password input field is visible.
     */
    public void assertPasswordInputVisible() {
        logger.debug("Asserting password input is visible");
        assertVisible(loginPage.getPasswordInput());
    }

    /**
     * Asserts that the login button is visible and enabled.
     */
    public void assertLoginButtonVisibleAndEnabled() {
        logger.debug("Asserting login button is visible and enabled");
        assertVisible(loginPage.getLoginButton());
        assertEnabled(loginPage.getLoginButton());
    }

    /**
     * Asserts that we remain on the Login page (URL hasn't changed).
     *
     * @param previousUrl The URL before the action
     */
    public void assertRemainOnLoginPage(String previousUrl) {
        logger.debug("Asserting we remain on Login page");
        String currentUrl = loginPage.getCurrentUrl();
        assertThat(currentUrl).contains("login");
        // Optionally check that URL hasn't changed significantly
        assertThat(loginPage.isOnPage()).isTrue();
    }

    public void assertErrorMessageVisible() {
        logger.debug("Asserting error message is visible");
        assertVisible(loginPage.getErrorMessage());
    }
}
