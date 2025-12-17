package com.example.pages.components;

import com.example.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component representing the header navigation of InMotion Hosting website.
 * Encapsulates header elements and navigation actions.
 */
public class HeaderComponent {

    private static final Logger logger = LoggerFactory.getLogger(HeaderComponent.class);
    private final Page page;

    // Header container
    private final Locator headerContainer;

    // Logo
    private final Locator logo;

    // Main navigation items
    private final Locator vpsHostingLink;
    private final Locator dedicatedServersLink;
    private final Locator wordPressHostingLink;
    private final Locator allHostingLink;
    private final Locator servicesLink;
    private final Locator pricingLink;

    // Utility links
    private final Locator supportCenterLink;
    private final Locator loginButton;
    private final Locator liveChatButton;
    private final Locator cartIcon;

    // Mobile menu
    private final Locator mobileMenuButton;
    private final Locator mobileMenu;

    /**
     * Creates a new HeaderComponent instance.
     *
     * @param page Playwright Page instance
     */
    public HeaderComponent(Page page) {
        this.page = page;

        // Initialize locators
        this.headerContainer = page.locator("header").first();
        this.logo = page.locator("header img[alt*='InMotion'], header a:has(img), .logo img").first();

        // Main navigation - using multiple strategies for robustness
        this.vpsHostingLink = page.locator("nav a[href*='vps'], a:text-is('VPS Hosting')").first();
        this.dedicatedServersLink = page.locator("nav a[href*='dedicated'], a:text-is('Dedicated Servers')").first();
        this.wordPressHostingLink = page.locator("nav a[href*='wordpress'], a:text-matches('.*WordPress.*', 'i')").first();
        this.allHostingLink = page.locator("nav a[href*='hosting']:not([href*='vps']):not([href*='wordpress']), a:text-is('All Hosting')").first();
        this.servicesLink = page.locator("nav a[href*='services'], a:text-is('Services')").first();
        this.pricingLink = page.locator("nav a[href*='pricing'], a:text-is('Pricing')").first();

        // Utility navigation
        this.supportCenterLink = page.locator("a[href*='support'], a:text-matches('Support.*', 'i')").first();
        this.loginButton = page.locator("a[href*='login'], button:text-is('Login'), .login-btn").first();
        this.liveChatButton = page.locator("a:text-matches('.*Chat.*', 'i'), button:text-matches('.*Chat.*', 'i'), .chat-button").first();
        this.cartIcon = page.locator("a[href*='cart'], .cart-icon, [aria-label*='cart']").first();

        // Mobile menu
        this.mobileMenuButton = page.locator("button[aria-label*='menu'], .hamburger, .mobile-menu-toggle, [data-toggle='collapse']").first();
        this.mobileMenu = page.locator(".mobile-menu, .nav-mobile, #mobile-nav").first();
    }

    // =========================================================================
    // Visibility Checks
    // =========================================================================

    /**
     * Checks if the header is visible.
     *
     * @return true if header is visible
     */
    public boolean isVisible() {
        return headerContainer.isVisible();
    }

    /**
     * Checks if the logo is visible.
     *
     * @return true if logo is visible
     */
    public boolean isLogoVisible() {
        return logo.isVisible();
    }

    /**
     * Waits for the header to be visible.
     */
    public void waitForHeader() {
        WaitUtils.waitForVisible(headerContainer);
    }

    // =========================================================================
    // Navigation Actions
    // =========================================================================

    /**
     * Clicks on the logo to navigate to the homepage.
     */
    @Step("Click on logo")
    public void clickLogo() {
        logger.info("Clicking on logo");
        logo.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to VPS Hosting page.
     */
    @Step("Navigate to VPS Hosting")
    public void navigateToVpsHosting() {
        logger.info("Navigating to VPS Hosting");
        vpsHostingLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to Dedicated Servers page.
     */
    @Step("Navigate to Dedicated Servers")
    public void navigateToDedicatedServers() {
        logger.info("Navigating to Dedicated Servers");
        dedicatedServersLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to WordPress Hosting page.
     */
    @Step("Navigate to WordPress Hosting")
    public void navigateToWordPressHosting() {
        logger.info("Navigating to WordPress Hosting");
        wordPressHostingLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to All Hosting page.
     */
    @Step("Navigate to All Hosting")
    public void navigateToAllHosting() {
        logger.info("Navigating to All Hosting");
        allHostingLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to Services page.
     */
    @Step("Navigate to Services")
    public void navigateToServices() {
        logger.info("Navigating to Services");
        servicesLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to Pricing page.
     */
    @Step("Navigate to Pricing")
    public void navigateToPricing() {
        logger.info("Navigating to Pricing");
        pricingLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to Support Center.
     */
    @Step("Navigate to Support Center")
    public void navigateToSupportCenter() {
        logger.info("Navigating to Support Center");
        supportCenterLink.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Clicks on the Login button.
     */
    @Step("Click Login button")
    public void clickLogin() {
        logger.info("Clicking Login button");
        loginButton.click();
    }

    /**
     * Clicks on the Live Chat button.
     */
    @Step("Click Live Chat button")
    public void clickLiveChat() {
        logger.info("Clicking Live Chat button");
        liveChatButton.click();
    }

    /**
     * Clicks on the Cart icon.
     */
    @Step("Click Cart icon")
    public void clickCart() {
        logger.info("Clicking Cart icon");
        cartIcon.click();
    }

    // =========================================================================
    // Mobile Navigation
    // =========================================================================

    /**
     * Opens the mobile menu.
     */
    @Step("Open mobile menu")
    public void openMobileMenu() {
        logger.info("Opening mobile menu");
        if (mobileMenuButton.isVisible()) {
            mobileMenuButton.click();
            WaitUtils.waitForVisible(mobileMenu);
        }
    }

    /**
     * Checks if mobile menu button is visible (indicating mobile viewport).
     *
     * @return true if in mobile view
     */
    public boolean isMobileView() {
        return mobileMenuButton.isVisible();
    }

    // =========================================================================
    // Hover Actions for Dropdown Menus
    // =========================================================================

    /**
     * Hovers over VPS Hosting to show dropdown menu.
     */
    @Step("Hover over VPS Hosting menu")
    public void hoverVpsHosting() {
        logger.debug("Hovering over VPS Hosting");
        vpsHostingLink.hover();
    }

    /**
     * Hovers over Dedicated Servers to show dropdown menu.
     */
    @Step("Hover over Dedicated Servers menu")
    public void hoverDedicatedServers() {
        logger.debug("Hovering over Dedicated Servers");
        dedicatedServersLink.hover();
    }

    /**
     * Hovers over WordPress Hosting to show dropdown menu.
     */
    @Step("Hover over WordPress Hosting menu")
    public void hoverWordPressHosting() {
        logger.debug("Hovering over WordPress Hosting");
        wordPressHostingLink.hover();
    }

    /**
     * Hovers over All Hosting to show dropdown menu.
     */
    @Step("Hover over All Hosting menu")
    public void hoverAllHosting() {
        logger.debug("Hovering over All Hosting");
        allHostingLink.hover();
    }

    /**
     * Hovers over Services to show dropdown menu.
     */
    @Step("Hover over Services menu")
    public void hoverServices() {
        logger.debug("Hovering over Services");
        servicesLink.hover();
    }

    // =========================================================================
    // Element Getters for Assertions
    // =========================================================================

    /**
     * Gets the header container locator.
     *
     * @return Header container locator
     */
    public Locator getHeaderContainer() {
        return headerContainer;
    }

    /**
     * Gets the logo locator.
     *
     * @return Logo locator
     */
    public Locator getLogo() {
        return logo;
    }

    /**
     * Gets the VPS Hosting link locator.
     *
     * @return VPS Hosting link locator
     */
    public Locator getVpsHostingLink() {
        return vpsHostingLink;
    }

    /**
     * Gets the Dedicated Servers link locator.
     *
     * @return Dedicated Servers link locator
     */
    public Locator getDedicatedServersLink() {
        return dedicatedServersLink;
    }

    /**
     * Gets the Login button locator.
     *
     * @return Login button locator
     */
    public Locator getLoginButton() {
        return loginButton;
    }
}

