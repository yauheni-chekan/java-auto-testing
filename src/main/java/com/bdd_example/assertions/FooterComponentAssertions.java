package com.bdd_example.assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdd_example.pages.components.FooterComponent;

public class FooterComponentAssertions {

    private static final Logger logger = LoggerFactory.getLogger(FooterComponentAssertions.class);
    private final FooterComponent footerComponent;

    public FooterComponentAssertions(FooterComponent footerComponent) {
        this.footerComponent = footerComponent;
    }

    public void assertHasText(String expectedText) {
        logger.debug("Asserting element has text: {}", expectedText);
        assertThat(footerComponent.getCopyrightTextLocator()).hasText(expectedText);
    }

    public void assertContainsText(String expectedText) {
        logger.debug("Asserting element contains text: {}", expectedText);
        assertThat(footerComponent.getCopyrightTextLocator()).containsText(expectedText);
    }
}