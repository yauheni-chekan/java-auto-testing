package com.bdd_example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {

    private final Locator loginContainer = page.locator("#login-container-simple");
    private final Locator usernameInput = loginContainer.getByPlaceholder("email address");
    private final Locator passwordInput = loginContainer.getByPlaceholder("password");
    private final Locator loginButton = loginContainer.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Log In"));

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

    @Override
    protected String getUrlPattern() {
        return ".*inmotionhosting\\.com/index/login?$";
    }

    @Override
    protected Locator getPageIdentifier() {
        return loginContainer;
    }
}
