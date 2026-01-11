package com.example.assertions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.pages.ContactPage;

public class ContactPageAssertions {
    
    private static final Logger logger = LoggerFactory.getLogger(ContactPageAssertions.class);
    private final ContactPage contactPage;

    public ContactPageAssertions(ContactPage contactPage) {
        this.contactPage = contactPage;
    }

    public void assertPageIsLoaded() {
        logger.debug("Asserting page is loaded");
        assertThat(contactPage.isOnPage()).isTrue();
    }

    public void assertTitleIsCorrect() {
        String expectedTitle = "Contact InMotion Hosting | Web Hosting Sales & Support";
        logger.debug("Asserting page title is correct");
        assertThat(contactPage.getPage()).hasTitle(expectedTitle);
    }

    public void assertAccountAndBillingCardMailtoLink() {
        String expectedEmail = "billing@inmotionhosting.com";
        logger.debug("Asserting account and billing card mailto link is correct");
        assertThat(contactPage.getAccountAndBillingCardMailtoLinkLocator()).isVisible();
        assertThat(contactPage.getAccountAndBillingCardMailtoLink()).contains(expectedEmail);
    }
}
