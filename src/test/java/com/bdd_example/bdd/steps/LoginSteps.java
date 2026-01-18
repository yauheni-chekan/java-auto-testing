package com.bdd_example.bdd.steps;

import com.bdd_example.assertions.LoginPageAssertions;
import com.bdd_example.base.BaseTest;
import com.bdd_example.pages.LoginPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

/**
 * Step definitions for Login Form Validation scenarios.
 */
public class LoginSteps extends BaseTest {

    private LoginPage loginPage;
    private LoginPageAssertions loginPageAssertions;
    private String previousUrl;

    // Note: Navigation steps are handled by LoginNavigationSteps to avoid duplicates

    @When("I click the {string} button without entering credentials")
    @Step("I click the {string} button without entering credentials")
    public void iClickTheButtonWithoutEnteringCredentials(String buttonName) {
        // Get loginPage from shared context if available, otherwise create new
        loginPage = getLoginPageFromContext();
        if (loginPageAssertions == null) {
            loginPageAssertions = new LoginPageAssertions(loginPage);
        }
        if ("Log In".equalsIgnoreCase(buttonName)) {
            previousUrl = loginPage.getCurrentUrl();
            loginPage.clickLoginButtonWithoutCredentials();
            logger.info("Clicked {} button without entering credentials", buttonName);
        } else {
            throw new IllegalArgumentException("Unknown button name: " + buttonName);
        }
    }

    private LoginPage getLoginPageFromContext() {
        // Try to get loginPage from shared context
        if (CommonSteps.sharedLoginPage != null) {
            loginPage = CommonSteps.sharedLoginPage;
        } else if (loginPage == null) {
            loginPage = new LoginPage();
            CommonSteps.sharedLoginPage = loginPage;
        }
        return loginPage;
    }

    @Then("I should remain on the Login page")
    @Step("I should remain on the Login page")
    public void iShouldRemainOnTheLoginPage() {
        loginPage = getLoginPageFromContext();
        if (loginPageAssertions == null) {
            loginPageAssertions = new LoginPageAssertions(loginPage);
        }
        loginPageAssertions.assertRemainOnLoginPage(previousUrl);
        logger.info("Remained on Login page");
    }

    @And("the login form should remain unchanged")
    @Step("the login form should remain unchanged")
    public void theLoginFormShouldRemainUnchanged() {
        loginPage = getLoginPageFromContext();
        if (loginPageAssertions == null) {
            loginPageAssertions = new LoginPageAssertions(loginPage);
        }
        // Verify that login form elements are still visible (form hasn't changed)
        loginPageAssertions.assertUsernameInputVisible();
        loginPageAssertions.assertPasswordInputVisible();
        loginPageAssertions.assertLoginButtonVisibleAndEnabled();
        logger.info("Login form validation verified - form remains unchanged");
    }

    @And("the login form should show validation errors")
    @Step("the login form should show validation errors")
    public void theLoginFormShouldShowValidationErrors() {
        loginPage = getLoginPageFromContext();
        if (loginPageAssertions == null) {
            loginPageAssertions = new LoginPageAssertions(loginPage);
        }
        // Verify that login form elements are still visible (form hasn't changed)
        loginPageAssertions.assertUsernameInputVisible();
        loginPageAssertions.assertPasswordInputVisible();
        loginPageAssertions.assertLoginButtonVisibleAndEnabled();
        loginPageAssertions.assertErrorMessageVisible();
        logger.info("Login form validation verified - form shows errors");
    }

    @And("the username input field should be visible")
    @Step("the username input field should be visible")
    public void theUsernameInputFieldShouldBeVisible() {
        loginPage = getLoginPageFromContext();
        if (loginPageAssertions == null) {
            loginPageAssertions = new LoginPageAssertions(loginPage);
        }
        loginPageAssertions.assertUsernameInputVisible();
        logger.info("Username input field is visible");
    }

    @And("the password input field should be visible")
    @Step("the password input field should be visible")
    public void thePasswordInputFieldShouldBeVisible() {
        loginPage = getLoginPageFromContext();
        if (loginPageAssertions == null) {
            loginPageAssertions = new LoginPageAssertions(loginPage);
        }
        loginPageAssertions.assertPasswordInputVisible();
        logger.info("Password input field is visible");
    }

    @And("the {string} button should be visible and enabled")
    @Step("the {string} button should be visible and enabled")
    public void theButtonShouldBeVisibleAndEnabled(String buttonName) {
        loginPage = getLoginPageFromContext();
        if (loginPageAssertions == null) {
            loginPageAssertions = new LoginPageAssertions(loginPage);
        }
        if ("Log In".equalsIgnoreCase(buttonName)) {
            loginPageAssertions.assertLoginButtonVisibleAndEnabled();
            logger.info("{} button is visible and enabled", buttonName);
        } else {
            throw new IllegalArgumentException("Unknown button name: " + buttonName);
        }
    }

    @When("I enter invalid email address")
    @Step("I enter invalid email address")
    public void iEnterInvalidEmailAddress() {
        loginPage = getLoginPageFromContext();
        loginPage.enterInvalidEmailAddress();
        logger.info("Entered invalid email address");
    }

    @When("I enter invalid password")
    @Step("I enter invalid password")
    public void iEnterInvalidPassword() {
        loginPage = getLoginPageFromContext();
        loginPage.enterInvalidPassword();
        logger.info("Entered invalid password");
    }

    @And("I click the {string} button")
    @Step("I click the {string} button")
    public void iClickTheButton(String buttonName) {
        loginPage = getLoginPageFromContext();
        if ("Log In".equalsIgnoreCase(buttonName)) {
            loginPage.clickLoginButton();
            logger.info("Clicked {} button", buttonName);
        } else {
            throw new IllegalArgumentException("Unknown button name: " + buttonName);
        }
    }
}
