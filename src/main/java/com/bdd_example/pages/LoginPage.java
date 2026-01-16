package com.bdd_example.pages;

import com.bdd_example.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private final Locator loginContainer = page.locator("#login-container-simple");
    private final Locator usernameInput = loginContainer.getByPlaceholder("email address");
    private final Locator passwordInput = loginContainer.getByPlaceholder("password");
    private final Locator loginButton = loginContainer.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Log In"));
    private final Locator errorMessage = loginContainer.getByText("Invalid e-mail address and/or password");

    public LoginPage() {
        super();
    }

    public LoginPage(Page page) {
        super(page);
    }

    public Locator getLoginContainer() {
        return loginContainer;
    }

    public Locator getUsernameInput() {
        return usernameInput;
    }

    public Locator getPasswordInput() {
        return passwordInput;
    }

    public Locator getLoginButton() {
        return loginButton;
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }

    @Override
    protected String getUrlPattern() {
        return ".*inmotionhosting\\.com/index/login?$";
    }

    @Override
    protected Locator getPageIdentifier() {
        return loginContainer;
    }

    // =========================================================================
    // Page Actions
    // =========================================================================

    /**
     * Clicks the Log In button without entering credentials.
     */
    @Step("Click Log In button without entering credentials")
    public void clickLoginButtonWithoutCredentials() {
        logger.info("Clicking Log In button without entering credentials");
        WaitUtils.waitForVisible(loginButton);
        loginButton.click();
        // Wait a moment for any validation or page changes
        WaitUtils.waitForDomContentLoaded(page);
    }

    @Step("Click Log In button")
    public void clickLoginButton() {
        logger.info("Clicking Log In button");
        WaitUtils.waitForVisible(loginButton);
        loginButton.click();
        // Wait a moment for any validation or page changes
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Enters an invalid email address into the username field.
     */
    @Step("Enter invalid email address")
    public void enterInvalidEmailAddress() {
        logger.info("Entering invalid email address");
        type(usernameInput, "invalid-email@invalid");
    }

    /**
     * Enters an invalid password into the password field.
     */
    @Step("Enter invalid password")
    public void enterInvalidPassword() {
        logger.info("Entering invalid password");
        type(passwordInput, "invalidpassword123");
    }
}
