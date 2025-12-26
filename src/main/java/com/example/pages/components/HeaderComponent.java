package com.example.pages.components;

import com.example.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

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

    // Primary navigation
    private final Locator resourcesDropdown;
    private final Locator contactUsDropdown;

    // Main navigation items
    private final Locator vpsHostingLink;
    private final Locator dedicatedServersDropdown;
    private final Locator wordPressHostingDropdown;
    private final Locator allHostingDropdown;
    private final Locator servicesDropdown;
    private final Locator pricingLink;

    // Utility links
    private final Locator loginButton;
    private final Locator cartIcon;

    /**
     * Creates a new HeaderComponent instance.
     *
     * @param page Playwright Page instance
     */
    public HeaderComponent(Page page) {
        this.page = page;

        // Initialize locators
        // Locator header = page.locator("header#masthead > div#imh-main-menu");
        Locator header = page.getByLabel("InMotion Hosting Main Menu");
        Locator primaryNav = header.locator("div.primary-nav");
        Locator navArea = header.locator("div#navbarNavDropdown ul.nav1").first();
        Locator desktopLogo = header.locator("#navbarNavDropdown > ul.nav1 > a.desktop-logo").first();

        this.headerContainer = header;
        this.logo = desktopLogo;

        // Primary navigation
        this.resourcesDropdown = primaryNav.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Resources")).first();
        this.contactUsDropdown = primaryNav.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Contact Us")).first();
        this.loginButton = primaryNav.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Login")).first();

        // Navigation links - scoped to navArea (ul.nav1 or div#navbarNavDropdown)
        this.vpsHostingLink = navArea.getByLabel("VPS Hosting").first();
        this.dedicatedServersDropdown = navArea.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Dedicated Servers")).first(); 
        this.wordPressHostingDropdown = navArea.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Hosting for WordPress")).first();
        this.allHostingDropdown = navArea.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("All Hosting")).first();
        this.servicesDropdown = navArea.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Website Services")).first();
        this.pricingLink = navArea.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Pricing")).first();
        this.cartIcon = navArea.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Shopping Cart")).first();
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
     * Waits for the logo to be visible (useful for lazy-loaded images).
     * Waits for the desktop logo specifically (excludes mobile logo).
     * Targets logo in ul.nav1 (desktop navigation) based on actual DOM structure.
     */
    public void waitForLogo() {
        // Wait for desktop logo in ul.nav1 first (most specific - desktop navigation)
        // Then fallback to any desktop logo (excluding mobile)
        Locator navLogo = page.locator("header#masthead ul.nav1 a[class*='desktop-logo']:has(img[alt='InMotion Hosting Logo'])")
            .or(page.locator("header#masthead ul.nav1 a.desktop-logo"));
        
        Locator desktopLogo = page.locator("header#masthead a[class*='desktop-logo']:not([class*='mobile-logo']):has(img[alt='InMotion Hosting Logo'])")
            .or(page.locator("header#masthead a[class*='desktop-logo']:not([class*='mobile-logo'])"));
        
        // Try nav logo first, then desktop logo
        try {
            WaitUtils.waitForVisible(navLogo);
        } catch (Exception e) {
            logger.debug("Nav logo not found, trying desktop logo: {}", e.getMessage());
            WaitUtils.waitForVisible(desktopLogo);
        }
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
        dedicatedServersDropdown.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to WordPress Hosting page.
     */
    @Step("Navigate to WordPress Hosting")
    public void navigateToWordPressHosting() {
        logger.info("Navigating to WordPress Hosting");
        wordPressHostingDropdown.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to All Hosting page.
     */
    @Step("Navigate to All Hosting")
    public void navigateToAllHosting() {
        logger.info("Navigating to All Hosting");
        allHostingDropdown.click();
        WaitUtils.waitForDomContentLoaded(page);
    }

    /**
     * Navigates to Services page.
     */
    @Step("Navigate to Services")
    public void navigateToServices() {
        logger.info("Navigating to Services");
        servicesDropdown.click();
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
     * Clicks on the Login button.
     */
    @Step("Click Login button")
    public void clickLogin() {
        logger.info("Clicking Login button");
        loginButton.click();
    }

    /**
     * Clicks on the Contact Us dropdown.
     */
    @Step("Click Contact Us dropdown")
    public void clickContactUs() {
        logger.info("Clicking 'Contact Us' dropdown");
        contactUsDropdown.click();
    }

    /**
     * Clicks on the Resources dropdown.
     */
    @Step("Click Resources dropdown")
    public void clickResources() {
        logger.info("Clicking 'Resources' dropdown");
        resourcesDropdown.click();
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
        dedicatedServersDropdown.hover();
    }

    /**
     * Hovers over WordPress Hosting to show dropdown menu.
     */
    @Step("Hover over WordPress Hosting menu")
    public void hoverWordPressHosting() {
        logger.debug("Hovering over WordPress Hosting");
        wordPressHostingDropdown.hover();
    }

    /**
     * Hovers over All Hosting to show dropdown menu.
     */
    @Step("Hover over All Hosting menu")
    public void hoverAllHosting() {
        logger.debug("Hovering over All Hosting");
        allHostingDropdown.hover();
    }

    /**
     * Hovers over Services to show dropdown menu.
     */
    @Step("Hover over Services menu")
    public void hoverServices() {
        logger.debug("Hovering over Services");
        servicesDropdown.hover();
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
     * Gets the Dedicated Servers dropdown locator.
     *
     * @return Dedicated Servers dropdown locator
     */
    public Locator getDedicatedServersDropdown() {
        return dedicatedServersDropdown;
    }

    /**
     * Gets the WordPress Hosting dropdown locator.
     *
     * @return WordPress Hosting dropdown locator
     */
    public Locator getWordPressHostingDropdown() {
        return wordPressHostingDropdown;
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
