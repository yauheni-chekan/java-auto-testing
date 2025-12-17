package com.example.pages;

import com.example.pages.components.FooterComponent;
import com.example.pages.components.HeaderComponent;
import com.example.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
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
        return page.locator("a:text-matches('View.*Plans.*|See.*Plans.*', 'i'), button:text-matches('View.*Plans.*', 'i')").first();
    }

    private Locator getTalkWithExpertButton() {
        return page.locator("a:text-matches('Talk.*Expert.*', 'i'), button:text-matches('Talk.*Expert.*', 'i')").first();
    }

    private Locator getHostingPlansSection() {
        return page.locator("section:has(h2:text-matches('.*Hosting.*|.*Plans.*', 'i')), [class*='pricing'], [class*='plans']").first();
    }

    private Locator getVpsHostingCard() {
        return page.locator("[class*='card']:has(:text('VPS')), .hosting-card:has(:text('VPS'))").first();
    }

    private Locator getDedicatedHostingCard() {
        return page.locator("[class*='card']:has(:text('Dedicated')), .hosting-card:has(:text('Dedicated'))").first();
    }

    private Locator getSharedHostingCard() {
        return page.locator("[class*='card']:has(:text('Shared')), .hosting-card:has(:text('Shared'))").first();
    }

    private Locator getWordpressHostingCard() {
        return page.locator("[class*='card']:has(:text('WordPress')), .hosting-card:has(:text('WordPress'))").first();
    }

    private Locator getFeaturesSection() {
        return page.locator("section:has(h2:text-matches('.*Why.*Choose.*|.*Features.*', 'i'))").first();
    }

    private Locator getFeatureItems() {
        return page.locator("[class*='feature'], .feature-item, [class*='benefit']");
    }

    private Locator getDomainSearchInput() {
        return page.locator("input[type='text'][placeholder*='domain'], input[name*='domain'], .domain-search input").first();
    }

    private Locator getDomainSearchButton() {
        return page.locator("button:text-matches('.*Search.*|.*Go.*', 'i'), .domain-search button").first();
    }

    private Locator getChatWithUsButton() {
        return page.locator("a:text-matches('Chat.*Us.*', 'i'), button:text-matches('Chat.*Us.*', 'i')").first();
    }

    private Locator getComparePlansButtons() {
        return page.locator("a:text-matches('Compare.*Plans.*', 'i'), button:text-matches('Compare.*Plans.*', 'i')");
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
     * Clicks the Chat With Us button.
     */
    @Step("Click Chat With Us button")
    public void clickChatWithUs() {
        logger.info("Clicking Chat With Us button");
        click(getChatWithUsButton());
    }

    /**
     * Searches for a domain.
     *
     * @param domainName Domain name to search
     */
    @Step("Search for domain: {domainName}")
    public void searchDomain(String domainName) {
        logger.info("Searching for domain: {}", domainName);
        Locator searchInput = getDomainSearchInput();
        if (searchInput.isVisible()) {
            scrollIntoView(searchInput);
            clearAndType(searchInput, domainName);
            click(getDomainSearchButton());
        } else {
            logger.warn("Domain search input not visible on page");
        }
    }

    /**
     * Scrolls to the hosting plans section.
     */
    @Step("Scroll to hosting plans")
    public void scrollToHostingPlans() {
        logger.info("Scrolling to hosting plans section");
        scrollIntoView(getHostingPlansSection());
    }

    /**
     * Scrolls to the features section.
     */
    @Step("Scroll to features section")
    public void scrollToFeatures() {
        logger.info("Scrolling to features section");
        scrollIntoView(getFeaturesSection());
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

    /**
     * Gets the number of feature items displayed.
     *
     * @return Count of feature items
     */
    public int getFeatureItemsCount() {
        return getFeatureItems().count();
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
     * Checks if the hosting plans section is visible.
     *
     * @return true if hosting plans section is visible
     */
    public boolean isHostingPlansSectionVisible() {
        return getHostingPlansSection().isVisible();
    }

    /**
     * Checks if the features section is visible.
     *
     * @return true if features section is visible
     */
    public boolean isFeaturesSectionVisible() {
        return getFeaturesSection().isVisible();
    }

    /**
     * Checks if domain search is available.
     *
     * @return true if domain search input is visible
     */
    public boolean isDomainSearchVisible() {
        return getDomainSearchInput().isVisible();
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

    /**
     * Gets the hosting plans section locator for assertions.
     *
     * @return Hosting plans section locator
     */
    public Locator hostingPlansSection() {
        return getHostingPlansSection();
    }
}

