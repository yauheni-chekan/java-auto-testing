package com.bdd_example.pages;

import com.bdd_example.pages.components.FooterComponent;
import com.bdd_example.pages.components.HeaderComponent;
import com.bdd_example.utils.WaitUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import io.qameta.allure.Step;

/**
 * Page Object for InMotion Hosting Shared Hosting Page.
 * Contains elements and actions specific to the shared hosting page.
 */
public class SharedHostingPage extends BasePage {

    // Components
    private final HeaderComponent header;
    private final FooterComponent footer;

    // Page identifier
    private final Locator pageIdentifier;

    /**
     * Creates a new SharedHostingPage instance using the current thread's page.
     */
    public SharedHostingPage() {
        super();
        this.header = new HeaderComponent(page);
        this.footer = new FooterComponent(page);
        this.pageIdentifier = page.getByRole(AriaRole.HEADING).filter(
            new Locator.FilterOptions().setHasText("Shared Hosting")
        ).first();
    }

    /**
     * Creates a new SharedHostingPage instance with a specific page.
     *
     * @param page Playwright Page instance
     */
    public SharedHostingPage(Page page) {
        super(page);
        this.header = new HeaderComponent(page);
        this.footer = new FooterComponent(page);
        this.pageIdentifier = page.getByRole(AriaRole.HEADING).filter(
            new Locator.FilterOptions().setHasText("Shared Hosting")
        ).first();
    }

    @Override
    protected String getUrlPattern() {
        return ".*inmotionhosting\\.com/shared-hosting.*";
    }

    @Override
    protected Locator getPageIdentifier() {
        return pageIdentifier;
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
     * Clicks the Login link on the Shared Hosting page.
     *
     * @return LoginPage instance
     */
    @Step("Click Login link on Shared Hosting page")
    public LoginPage clickLoginLink() {
        logger.info("Clicking Login link on Shared Hosting page");
        header().clickLogin();
        WaitUtils.waitForDomContentLoaded(page);
        return new LoginPage(page);
    }
}
