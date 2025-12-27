package com.example.assertions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pages.PricingPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class PricingPageAssertions {
    
    private static final Logger logger = LoggerFactory.getLogger(PricingPageAssertions.class);
    private final PricingPage pricingPage;

    public PricingPageAssertions(PricingPage pricingPage) {
        this.pricingPage = pricingPage;
    }


    public void assertPageIsLoaded() {
        logger.debug("Asserting page is loaded");
        assertThat(pricingPage.isOnPage()).isTrue();
    }

    public void assertTitleIsCorrect() {
        String expectedTitle = "Web Hosting Plans & Pricing 2025 | InMotion Hosting";
        logger.debug("Asserting page title is correct");
        assertThat(pricingPage.getPage()).hasTitle(expectedTitle);
    }
}
