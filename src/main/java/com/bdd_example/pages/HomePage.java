package com.bdd_example.pages;

import com.bdd_example.pages.components.FooterComponent;
import com.bdd_example.pages.components.HeaderComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import io.qameta.allure.Step;

/**
 * Page Object for InMotion Hosting Homepage.
 * Contains elements and actions specific to the main landing page.
 */
public class HomePage extends BasePage {

    // Components
    private final HeaderComponent header;
    private final FooterComponent footer;

    // Locators are lazily initialized via getter methods

    /**
     * Creates a new HomePage instance using the current thread's page.
     */
    public HomePage() {
        super();
        this.header = new HeaderComponent(page);
        this.footer = new FooterComponent(page);
    }

    /**
     * Creates a new HomePage instance with a specific page.
     *
     * @param page Playwright Page instance
     */
    public HomePage(Page page) {
        super(page);
        this.header = new HeaderComponent(page);
        this.footer = new FooterComponent(page);
    }

    // Locator getter methods
    private Locator getHeroSection() {
        return page.locator("section.hero, .hero-section, [class*='hero'], main section:first-child").first();
    }

    private Locator getHeroTitle() {
        return page.locator("h1, .hero h1, [class*='hero'] h1").first();
    }

    private Locator getHeroSubtitle() {
        return page.locator(".hero h2, .hero p, [class*='hero'] h2, [class*='hero'] .subtitle").first();
    }

    private Locator getViewPlansButton() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View Plans & Pricing")).first();
    }

    private Locator getTalkWithExpertButton() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Talk with an Expert")).first();
    }

    private Locator getVpsHostingCard() {
        return page.locator("[class*='imh-clickable-card']:has(:text('VPS Hosting'))").first();
    }

    private Locator getDedicatedHostingCard() {
        return page.locator("[class*='imh-clickable-card']:has(:text('Dedicated Hosting'))").first();
    }

    private Locator getSharedHostingCard() {
        return page.locator("[class*='imh-clickable-card']:has(:text('Shared Hosting'))").first();
    }

    private Locator getWordpressHostingCard() {
        return page.locator("[class*='imh-clickable-card']:has(:text('Hosting for WordPress'))").first();
    }

    @Override
    protected String getUrlPattern() {
        return ".*inmotionhosting\\.com/?$";
    }

    @Override
    protected Locator getPageIdentifier() {
        return getHeroTitle();
    }

    // =========================================================================
    // Component Accessors
    // =========================================================================

    /**
     * Gets the header component.
     *
     * @return HeaderComponent instance
     */
    public HeaderComponent header() {
        return header;
    }

    /**
     * Gets the footer component.
     *
     * @return FooterComponent instance
     */
    public FooterComponent footer() {
        return footer;
    }

    // =========================================================================
    // Page Actions
    // =========================================================================

    /**
     * Opens the InMotion Hosting homepage.
     *
     * @return This HomePage instance for method chaining
     */
    @Step("Open InMotion Hosting homepage")
    public HomePage open() {
        logger.info("Opening InMotion Hosting homepage");
        navigateToBaseUrl();
        waitForPageLoad();
        return this;
    }

    /**
     * Clicks the View Plans button in the hero section.
     */
    @Step("Click View Plans button")
    public void clickViewPlans() {
        logger.info("Clicking View Plans button");
        click(getViewPlansButton());
    }

    /**
     * Clicks the Talk With Expert button.
     */
    @Step("Click Talk With Expert button")
    public void clickTalkWithExpert() {
        logger.info("Clicking Talk With Expert button");
        click(getTalkWithExpertButton());
    }

    /**
     * Clicks on VPS Hosting card.
     */
    @Step("Click VPS Hosting card")
    public void clickVpsHostingCard() {
        logger.info("Clicking VPS Hosting card");
        scrollIntoView(getVpsHostingCard());
        click(getVpsHostingCard());
    }

    /**
     * Clicks on Dedicated Hosting card.
     */
    @Step("Click Dedicated Hosting card")
    public void clickDedicatedHostingCard() {
        logger.info("Clicking Dedicated Hosting card");
        scrollIntoView(getDedicatedHostingCard());
        click(getDedicatedHostingCard());
    }

    /**
     * Clicks on Shared Hosting card.
     */
    @Step("Click Shared Hosting card")
    public void clickSharedHostingCard() {
        logger.info("Clicking Shared Hosting card");
        scrollIntoView(getSharedHostingCard());
        click(getSharedHostingCard());
    }

    /**
     * Clicks on WordPress Hosting card.
     */
    @Step("Click WordPress Hosting card")
    public void clickWordPressHostingCard() {
        logger.info("Clicking WordPress Hosting card");
        scrollIntoView(getWordpressHostingCard());
        click(getWordpressHostingCard());
    }

    // =========================================================================
    // Page Information
    // =========================================================================
    
    /**
     * Gets the hero section title text.
     *
     * @return Hero title text
     */
    public String getHeroTitleText() {
        return getText(getHeroTitle());
    }

    /**
     * Gets the hero section subtitle text.
     *
     * @return Hero subtitle text
     */
    public String getHeroSubtitleText() {
        Locator subtitle = getHeroSubtitle();
        if (subtitle.isVisible()) {
            return getText(subtitle);
        }
        return "";
    }

    // =========================================================================
    // Visibility Checks
    // =========================================================================

    /**
     * Checks if the hero section is visible.
     *
     * @return true if hero section is visible
     */
    public boolean isHeroSectionVisible() {
        return getHeroSection().isVisible();
    }

    /**
     * Checks if VPS Hosting card is visible.
     *
     * @return true if VPS card is visible
     */
    public boolean isVpsHostingCardVisible() {
        return getVpsHostingCard().isVisible();
    }

    /**
     * Checks if Dedicated Hosting card is visible.
     *
     * @return true if Dedicated card is visible
     */
    public boolean isDedicatedHostingCardVisible() {
        return getDedicatedHostingCard().isVisible();
    }

    // =========================================================================
    // Element Getters for Assertions
    // =========================================================================

    /**
     * Gets the hero title locator for assertions.
     *
     * @return Hero title locator
     */
    public Locator heroTitle() {
        return getHeroTitle();
    }

    /**
     * Gets the View Plans button locator for assertions.
     *
     * @return View Plans button locator
     */
    public Locator viewPlansButton() {
        return getViewPlansButton();
    }
}