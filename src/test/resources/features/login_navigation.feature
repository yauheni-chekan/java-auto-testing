@navigation @login @anywhere
Feature: Login Navigation
  As a user
  I want to navigate to the login page from different locations
  So that I can access my account from anywhere on the website

  Background:
    Given I am on the InMotion Hosting website

  Scenario: Navigate to Login page from homepage header
    When I navigate to the homepage
    And I hover over the "Login" link in the header
    And I click the "Login" link in the header
    Then I should be navigated to the Login page
    And the Login page should be loaded

  Scenario: Navigate to Login page from Shared Hosting page
    When I navigate to the homepage
    And I click the "All Hosting" dropdown in the header
    And I select "Shared Hosting" from the "All Hosting" dropdown
    Then I should be navigated to the Shared Hosting page
    And the Shared Hosting page should be loaded
    When I click the "Login" link on the Shared Hosting page
    Then I should be navigated to the Login page
    And the Login page should be loaded
