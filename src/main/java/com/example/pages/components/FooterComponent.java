package com.example.pages.components;

import com.example.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

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
    private final Locator footerNavigation;
    private final Locator bottomFooter;

    // Social media links
    private final Locator socialMediaLinks;

    // Copyright and legal
    private final Locator copyrightText;
    private final Locator termsOfServiceLink;
    private final Locator privacyPolicyLink;
    // About Us
    private final Locator cookiePreferencesLink;
    // Cookie Preferences Modal
    private final Locator cookiePreferencesModal;
    private final Locator performanceCookiesHeading;
    private final Locator performanceCookiesDescription;
    private final Locator functionalCookiesHeading;
    private final Locator functionalCookiesDescription;
    private final Locator strictlyNecessaryCookiesHeading;
    private final Locator strictlyNecessaryCookiesDescription;
    private final Locator targetingCookiesHeading;
    private final Locator targetingCookiesDescription;

    /**
     * Creates a new FooterComponent instance.
     *
     * @param page Playwright Page instance
     */
    public FooterComponent(Page page) {
        this.page = page;

        // Initialize locators
        this.footerNavigation = page.getByLabel("Footer Navigation");
        this.bottomFooter = page.locator("#imh-bottom-footer");

        // Social media
        this.socialMediaLinks = footerNavigation.locator(".imh-social");

        // Legal
        this.copyrightText = bottomFooter.getByText("Copyright © 2002-");
        this.termsOfServiceLink = bottomFooter.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Terms of Service"));
        this.privacyPolicyLink = bottomFooter.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Privacy Policy"));

        // About Us
        this.cookiePreferencesLink = footerNavigation.locator("#ot-sdk-btn");

        // Cookie Preferences Modal
        this.cookiePreferencesModal = page.locator("#onetrust-pc-sdk");
        this.performanceCookiesHeading = cookiePreferencesModal.locator("#ot-header-id-C0002");
        this.performanceCookiesDescription = cookiePreferencesModal.locator("#ot-desc-id-C0002");
        this.functionalCookiesHeading = cookiePreferencesModal.locator("#ot-header-id-C0003");
        this.functionalCookiesDescription = cookiePreferencesModal.locator("#ot-desc-id-C0003");
        this.strictlyNecessaryCookiesHeading = cookiePreferencesModal.locator("#ot-header-id-C0001");
        this.strictlyNecessaryCookiesDescription = cookiePreferencesModal.locator("#ot-desc-id-C0001");
        this.targetingCookiesHeading = cookiePreferencesModal.locator("#ot-header-id-C0004");
        this.targetingCookiesDescription = cookiePreferencesModal.locator("#ot-desc-id-C0004");
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
        return footerNavigation.isVisible() && bottomFooter.isVisible();
    }

    /**
     * Waits for the footer to be visible.
     */
    public void waitForFooter() {
        WaitUtils.waitForVisible(footerNavigation);
        WaitUtils.waitForVisible(bottomFooter);
    }

    /**
     * Scrolls the footer into view.
     */
    @Step("Scroll footer into view")
    public void scrollIntoView() {
        logger.debug("Scrolling footer into view");
        footerNavigation.scrollIntoViewIfNeeded();
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

    /**
     * Clicks on Cookie Preferences link.
     */
    @Step("Click Cookie Preferences")
    public void clickCookiePreferences() {
        logger.info("Clicking Cookie Preferences");
        cookiePreferencesLink.scrollIntoViewIfNeeded();
        cookiePreferencesLink.click();
        WaitUtils.waitForDomContentLoaded(page);
        WaitUtils.waitForVisible(cookiePreferencesModal);
    }

    /**
     * Clicks on Performance Cookies heading.
     */
    @Step("Click Performance Cookies")
    public void clickPerformanceCookies() {
        logger.info("Clicking Performance Cookies");
        performanceCookiesHeading.scrollIntoViewIfNeeded();
        performanceCookiesHeading.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Clicks on Functional Cookies heading.
     */
    @Step("Click Functional Cookies")
    public void clickFunctionalCookies() {
        logger.info("Clicking Functional Cookies");
        functionalCookiesHeading.scrollIntoViewIfNeeded();
        functionalCookiesHeading.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Clicks on Strictly Necessary Cookies heading.
     */
    @Step("Click Strictly Necessary Cookies")
    public void clickStrictlyNecessaryCookies() {
        logger.info("Clicking Strictly Necessary Cookies");
        strictlyNecessaryCookiesHeading.scrollIntoViewIfNeeded();
        strictlyNecessaryCookiesHeading.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Clicks on Targeting Cookies heading.
     */
    @Step("Click Targeting Cookies")
    public void clickTargetingCookies() {
        logger.info("Clicking Targeting Cookies");
        targetingCookiesHeading.scrollIntoViewIfNeeded();
        targetingCookiesHeading.click();
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
    public Locator getFooterNavigation() {
        return footerNavigation;
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


    /**
     * Gets the Cookie Preferences modal locator.
     *
     * @return Cookie Preferences modal locator
     */
    public Locator getCookiePreferencesModal() {
        return cookiePreferencesModal;
    }

    /**
     * Gets the Cookie Preferences link locator.
     *
     * @return Cookie Preferences link locator
     */
    public Locator getCookiePreferencesLink() {
        return cookiePreferencesLink;
    }

    public Locator getPerformanceCookiesHeading() {
        return performanceCookiesHeading;
    }

    public Locator getPerformanceCookiesDescription() {
        return performanceCookiesDescription;
    }

    public Locator getFunctionalCookiesHeading() {
        return functionalCookiesHeading;
    }

    public Locator getFunctionalCookiesDescription() {
        return functionalCookiesDescription;
    }

    public Locator getStrictlyNecessaryCookiesHeading() {
        return strictlyNecessaryCookiesHeading;
    }

    public Locator getStrictlyNecessaryCookiesDescription() {
        return strictlyNecessaryCookiesDescription;
    }

    public Locator getTargetingCookiesHeading() {
        return targetingCookiesHeading;
    }

    public Locator getTargetingCookiesDescription() {
        return targetingCookiesDescription;
    }
}
