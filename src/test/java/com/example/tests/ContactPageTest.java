package com.example.tests;

import org.junit.jupiter.api.BeforeEach;

import com.example.base.BaseTest;
import com.example.pages.ContactPage;
import com.example.assertions.ContactPageAssertions;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;

public class ContactPageTest extends BaseTest {
    private ContactPage contactPage;
    private ContactPageAssertions pageAssertions;

    @BeforeEach
    void initContactPage() {
        contactPage = new ContactPage();
        pageAssertions = new ContactPageAssertions(contactPage);
    }

    @Test
    @Story("Page Load")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("smoke")
    @DisplayName("Contact page should load successfully")
    @Description("Verify that the Contact page loads with all essential elements visible")
    void testContactPageLoads() {
        contactPage.open();
        pageAssertions.assertTitleIsCorrect();
        pageAssertions.assertPageIsLoaded();
        logger.info("Contact page loaded successfully with title: {}", contactPage.getPageTitle());
    }

    @Test
    @Issue("IMH-CT-001")
    @Story("Link Verification")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Account and Billing card mailto link should have the correct email address")
    @Description("Verify that the Account and Billing card mailto link has the correct email address")
    void testAccountAndBillingCardMailtoLink() {
        contactPage.open();
        pageAssertions.assertAccountAndBillingCardMailtoLink();
        logger.info("Account and Billing card mailto link verified successfully with email address: {}", contactPage.getAccountAndBillingCardMailtoLink());
    }
}