package com.bdd_example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    private final Locator loginContainer;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage() {
        super();
        this.loginContainer = getLoginContainer();
        this.usernameInput = getUsernameInput();
        this.passwordInput = getPasswordInput();
        this.loginButton = getLoginButton();
    }

    public LoginPage(Page page) {
        super(page);
        this.loginContainer = getLoginContainer();
        this.usernameInput = getUsernameInput();
        this.passwordInput = getPasswordInput();
        this.loginButton = getLoginButton();
    }

    private Locator getLoginContainer() {
        return page.locator("#login-container-simple");
    }

    private Locator getUsernameInput() {
        return loginContainer.getByPlaceholder("email address");
    }

    private Locator getPasswordInput() {
        return loginContainer.getByPlaceholder("password");
    }

    private Locator getLoginButton() {
        return loginContainer.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Log In"));
    }

    @Override
    protected String getUrlPattern() {
        return ".*inmotionhosting\\.com/index/login?$";
    }

    @Override
    protected Locator getPageIdentifier() {
        return loginContainer;
    }
}
