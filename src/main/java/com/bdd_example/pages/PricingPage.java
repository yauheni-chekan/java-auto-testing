package com.bdd_example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;

public class PricingPage extends BasePage {

    private final Locator pricingPageIdentifier = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("InMotion Hosting Pricing")).first();
    private final String urlPattern = ".*inmotionhosting\\.com/pricing?$";

    public PricingPage() {
        super();
    }

    public PricingPage(Page page) {
        super(page);
    }

    public PricingPage open() {
        logger.info("Opening Pricing page");
        Response response = page.navigate(config.getBaseUrl() + "pricing");
        if (!response.ok()) {
            throw new RuntimeException("Failed to open Pricing page: " + response.status() + " " + response.statusText());
        }
        return this;
    }

    @Override
    protected String getUrlPattern() {
        return urlPattern;
    }

    @Override
    protected Locator getPageIdentifier() {
        return pricingPageIdentifier;
    }
}
