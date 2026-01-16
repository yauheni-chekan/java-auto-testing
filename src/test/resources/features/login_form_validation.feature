@smoke @login @validation
Feature: Login Form Validation
  As a user
  I want to see proper validation when submitting the login form
  So that I understand what information is required

  Background:
    Given I am on the InMotion Hosting website

  Scenario: Submit empty login form should show validation
    When I navigate to the homepage
    And I click the "Login" link in the header
    Then I should be navigated to the Login page
    And the Login page should be loaded
    When I click the "Log In" button without entering credentials
    Then I should remain on the Login page
    And the login form should remain unchanged

  Scenario: Login form elements should be visible and enabled
    When I navigate to the homepage
    And I click the "Login" link in the header
    Then I should be navigated to the Login page
    And the Login page should be loaded
    And the username input field should be visible
    And the password input field should be visible
    And the "Log In" button should be visible and enabled
