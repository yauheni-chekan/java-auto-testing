@navigation @logo
Feature: Logo Navigation
  As a user
  I want to navigate back to the homepage by clicking the logo
  So that I can easily return to the main page from any page

  Background:
    Given I am on the InMotion Hosting website

  Scenario: Click logo from Login page should return to homepage
    When I navigate to the homepage
    And I click the "Login" link in the header
    Then I should be navigated to the Login page
    When I click the logo in the header
    Then I should be navigated back to the homepage
    And the homepage should be loaded

  Scenario: Click logo from Shared Hosting page should return to homepage
    When I navigate to the homepage
    And I click the "All Hosting" dropdown in the header
    And I select "Shared Hosting" from the "All Hosting" dropdown
    Then I should be navigated to the Shared Hosting page
    When I click the logo in the header
    Then I should be navigated back to the homepage
    And the homepage should be loaded
