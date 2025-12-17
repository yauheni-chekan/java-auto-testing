package com.example.pages.components;

import com.example.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component representing the footer of InMotion Hosting website.
 * Encapsulates footer elements and actions.
 */
public class FooterComponent {

    private static final Logger logger = LoggerFactory.getLogger(FooterComponent.class);
    private final Page page;

    // Footer container
    private final Locator footerContainer;

    // Footer sections
    private final Locator webHostingSection;
    private final Locator hostingToolsSection;
    private final Locator supportSection;
    private final Locator aboutUsSection;

    // Social media links
    private final Locator socialMediaLinks;

    // Copyright and legal
    private final Locator copyrightText;
    private final Locator termsOfServiceLink;
    private final Locator privacyPolicyLink;

    /**
     * Creates a new FooterComponent instance.
     *
     * @param page Playwright Page instance
     */
    public FooterComponent(Page page) {
        this.page = page;

        // Initialize locators
        this.footerContainer = page.locator("footer").first();

        // Footer sections - using text content to identify sections
        this.webHostingSection = page.locator("footer :text('Web Hosting'), footer h4:text-matches('.*Hosting.*', 'i')").first();
        this.hostingToolsSection = page.locator("footer :text('Hosting Tools'), footer h4:text-matches('.*Tools.*', 'i')").first();
        this.supportSection = page.locator("footer :text('Support'), footer h4:text-matches('Support', 'i')").first();
        this.aboutUsSection = page.locator("footer :text('About Us'), footer h4:text-matches('About.*', 'i')").first();

        // Social media
        this.socialMediaLinks = page.locator("footer a[href*='facebook'], footer a[href*='twitter'], footer a[href*='linkedin'], footer .social-links a");

        // Legal
        this.copyrightText = page.locator("footer :text-matches('.*Copyright.*|.*©.*', 'i')").first();
        this.termsOfServiceLink = page.locator("footer a[href*='terms'], footer a:text-matches('Terms.*', 'i')").first();
        this.privacyPolicyLink = page.locator("footer a[href*='privacy'], footer a:text-matches('Privacy.*', 'i')").first();
    }

    // =========================================================================
    // Visibility Checks
    // =========================================================================

    /**
     * Checks if the footer is visible.
     *
     * @return true if footer is visible
     */
    public boolean isVisible() {
        return footerContainer.isVisible();
    }

    /**
     * Waits for the footer to be visible.
     */
    public void waitForFooter() {
        WaitUtils.waitForVisible(footerContainer);
    }

    /**
     * Scrolls the footer into view.
     */
    @Step("Scroll footer into view")
    public void scrollIntoView() {
        logger.debug("Scrolling footer into view");
        footerContainer.scrollIntoViewIfNeeded();
    }

    // =========================================================================
    // Navigation Actions
    // =========================================================================

    /**
     * Clicks on Terms of Service link.
     */
    @Step("Click Terms of Service")
    public void clickTermsOfService() {
        logger.info("Clicking Terms of Service");
        termsOfServiceLink.scrollIntoViewIfNeeded();
        termsOfServiceLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Clicks on Privacy Policy link.
     */
    @Step("Click Privacy Policy")
    public void clickPrivacyPolicy() {
        logger.info("Clicking Privacy Policy");
        privacyPolicyLink.scrollIntoViewIfNeeded();
        privacyPolicyLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    // =========================================================================
    // Information Retrieval
    // =========================================================================

    /**
     * Gets the copyright text from the footer.
     *
     * @return Copyright text
     */
    public String getCopyrightText() {
        return copyrightText.textContent();
    }

    /**
     * Gets the count of social media links in the footer.
     *
     * @return Number of social media links
     */
    public int getSocialMediaLinksCount() {
        return socialMediaLinks.count();
    }

    // =========================================================================
    // Element Getters for Assertions
    // =========================================================================

    /**
     * Gets the footer container locator.
     *
     * @return Footer container locator
     */
    public Locator getFooterContainer() {
        return footerContainer;
    }

    /**
     * Gets the copyright text locator.
     *
     * @return Copyright text locator
     */
    public Locator getCopyrightTextLocator() {
        return copyrightText;
    }

    /**
     * Gets the Terms of Service link locator.
     *
     * @return Terms of Service link locator
     */
    public Locator getTermsOfServiceLink() {
        return termsOfServiceLink;
    }

    /**
     * Gets the Privacy Policy link locator.
     *
     * @return Privacy Policy link locator
     */
    public Locator getPrivacyPolicyLink() {
        return privacyPolicyLink;
    }

    /**
     * Gets all social media links.
     *
     * @return Social media links locator
     */
    public Locator getSocialMediaLinks() {
        return socialMediaLinks;
    }
}

