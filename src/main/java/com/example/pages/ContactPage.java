package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;

public class ContactPage extends BasePage {
    public ContactPage() {
        super();
    }

    public ContactPage(Page page) {
        super(page);
    }

    public ContactPage open() {
        logger.info("Opening Contact page");
        Response response = page.navigate(config.getBaseUrl() + "contact");
        if (!response.ok()) {
            throw new RuntimeException("Failed to open Contact page: " + response.status() + " " + response.statusText());
        }
        return this;
    }
    @Override
    protected String getUrlPattern() {
        return ".*inmotionhosting\\.com/contact$";
    }

    @Override
    protected Locator getPageIdentifier() {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Contact InMotion Hosting")).first();
    }

    public Locator getAccountAndBillingCardMailtoLinkLocator() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("billing@inmotionhosting.com"));
    }

    public String getAccountAndBillingCardMailtoLink() {
        return getAccountAndBillingCardMailtoLinkLocator().getAttribute("href");
    }
}
