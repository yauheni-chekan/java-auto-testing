@navigation @all-hosting
Feature: All Hosting Dropdown Navigation
  As a user
  I want to navigate to different hosting options from the All Hosting dropdown
  So that I can explore available hosting solutions

  Background:
    Given I am on the InMotion Hosting website

  Scenario: Select Shared Hosting from All Hosting dropdown
    When I navigate to the homepage
    And I click the "All Hosting" dropdown in the header
    Then the "All Hosting" dropdown menu should be visible
    When I select "Shared Hosting" from the "All Hosting" dropdown
    Then I should be navigated to the Shared Hosting page
    And the Shared Hosting page should be loaded in the same tab
