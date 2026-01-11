package com.example.assertions;

import java.time.Year;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pages.PricingPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class PricingPageAssertions {
    
    private static final Logger logger = LoggerFactory.getLogger(PricingPageAssertions.class);
    private final PricingPage pricingPage;
    private final String currentYear;

    public PricingPageAssertions(PricingPage pricingPage) {
        this.pricingPage = pricingPage;
        this.currentYear = String.valueOf(Year.now().getValue());
    }


    public void assertPageIsLoaded() {
        logger.debug("Asserting page is loaded");
        assertThat(pricingPage.isOnPage()).isTrue();
    }

    public void assertTitleIsCorrect() {
        String expectedTitle = String.format("Web Hosting Plans & Pricing %s | InMotion Hosting", currentYear);
        logger.debug("Asserting page title is correct");
        assertThat(pricingPage.getPage()).hasTitle(expectedTitle);
    }
}
