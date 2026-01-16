@e2e @integration @login @task8
Feature: Complete Login Flow Integration
  As a user
  I want to complete the full login navigation flow
  So that I can verify the entire user journey works correctly

  Background:
    Given I am on the InMotion Hosting website

  Scenario: Complete login navigation flow from homepage
    When I navigate to the homepage
    And I hover over the "Login" link in the header
    And I click the "Login" link in the header
    Then I should be navigated to the Login page
    And the Login page should be loaded
    When I click the "Log In" button without entering credentials
    Then I should remain on the Login page
    And the login form should remain unchanged
    When I click the logo in the header
    Then I should be navigated back to the homepage
    And the homepage should be loaded
    When I click the "All Hosting" dropdown in the header
    And I select "Shared Hosting" from the "All Hosting" dropdown
    Then I should be navigated to the Shared Hosting page
    And the Shared Hosting page should be loaded in the same tab
    When I click the "Login" link on the Shared Hosting page
    Then I should be navigated to the Login page
    And the Login page should be loaded
    When I click the "Log In" button without entering credentials
    Then I should remain on the Login page
    And the login form should remain unchanged
    When I click the logo in the header
    Then I should be navigated back to the homepage
    And the homepage should be loaded
