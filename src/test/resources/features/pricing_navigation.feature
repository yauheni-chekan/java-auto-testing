@smoke @pricing @navigation
Feature: Pricing Page Navigation
  As a user
  I want to navigate to the pricing page from the homepage
  So that I can view hosting plans and pricing information

  Background:
    Given I am on the InMotion Hosting website

  Scenario: Click on pricing link should open and load Pricing page
    When I navigate to the homepage
    And I click the "Pricing" link in the header
    Then I should be navigated to the Pricing page
    And the Pricing page should be loaded
    And the Pricing page title should be correct
