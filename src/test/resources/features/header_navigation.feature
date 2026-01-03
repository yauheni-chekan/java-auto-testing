@navigation @header
Feature: Header Navigation Links Visibility
  As a user
  I want to see header navigation links
  So that I can navigate to different sections of the website

  Background:
    Given I am on the InMotion Hosting website

  Scenario Outline: Header navigation links should be visible
    When I navigate to the homepage
    Then the "<linkName>" link should be visible in the header

    Examples:
      | linkName          |
      | VPS Hosting       |
      | Dedicated Servers |
      | Pricing           |
