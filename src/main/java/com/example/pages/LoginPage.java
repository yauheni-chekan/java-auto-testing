package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import io.qameta.allure.Step;

/**
 * Page Object for InMotion Hosting Login Page.
 * Contains elements and actions specific to the login page.
 */
public class LoginPage extends BasePage {

    /**
     * Creates a new LoginPage instance using the current thread's page.
     */
    public LoginPage() {
        super();
    }

    /**
     * Creates a new LoginPage instance with a specific page.
     *
     * @param page Playwright Page instance
     */
    public LoginPage(Page page) {
        super(page);
    }

    // =========================================================================
    // Locator Getters (Private - lazy initialization)
    // =========================================================================

    private Locator getLoginContainer() {
        return page.locator("#login-container-simple");
    }

    private Locator getUsernameInput() {
        return getLoginContainer().getByPlaceholder("email address");
    }

    private Locator getPasswordInput() {
        return getLoginContainer().getByPlaceholder("password");
    }

    private Locator getLoginButton() {
        return getLoginContainer().getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Log In"));
    }

    // =========================================================================
    // Page Identification
    // =========================================================================

    @Override
    protected String getUrlPattern() {
        return ".*inmotionhosting\\.com/index/login?$";
    }

    @Override
    protected Locator getPageIdentifier() {
        return getLoginContainer();
    }

    // =========================================================================
    // Page Actions
    // =========================================================================

    /**
     * Opens the login page.
     *
     * @return This LoginPage instance for method chaining
     */
    @Step("Open login page")
    public LoginPage open() {
        logger.info("Opening login page");
        navigateTo(config.getBaseUrl() + "/index/login");
        waitForPageLoad();
        return this;
    }

    /**
     * Enters username/email into the username input field.
     *
     * @param username Username or email address
     * @return This LoginPage instance for method chaining
     */
    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        logger.info("Entering username: {}", username);
        type(getUsernameInput(), username);
        return this;
    }

    /**
     * Enters password into the password input field.
     *
     * @param password Password
     * @return This LoginPage instance for method chaining
     */
    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        logger.info("Entering password");
        type(getPasswordInput(), password);
        return this;
    }

    /**
     * Clicks the login button.
     */
    @Step("Click login button")
    public void clickLogin() {
        logger.info("Clicking login button");
        click(getLoginButton());
    }

    /**
     * Performs complete login action with username and password.
     *
     * @param username Username or email address
     * @param password Password
     */
    @Step("Login with username: {username}")
    public void login(String username, String password) {
        logger.info("Logging in with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // =========================================================================
    // Page Information
    // =========================================================================

    /**
     * Gets the username input value.
     *
     * @return Username input value
     */
    public String getUsernameValue() {
        return getInputValue(getUsernameInput());
    }

    /**
     * Checks if the login container is visible.
     *
     * @return true if login container is visible
     */
    public boolean isLoginContainerVisible() {
        return isVisible(getLoginContainer());
    }

    /**
     * Checks if the login button is enabled.
     *
     * @return true if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return isEnabled(getLoginButton());
    }

    // =========================================================================
    // Element Getters for Assertions (if needed)
    // =========================================================================

    /**
     * Gets the login container locator for assertions.
     *
     * @return Login container locator
     */
    public Locator loginContainer() {
        return getLoginContainer();
    }

    /**
     * Gets the username input locator for assertions.
     *
     * @return Username input locator
     */
    public Locator usernameInput() {
        return getUsernameInput();
    }

    /**
     * Gets the login button locator for assertions.
     *
     * @return Login button locator
     */
    public Locator loginButton() {
        return getLoginButton();
    }
}
