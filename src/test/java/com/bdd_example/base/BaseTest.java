package com.bdd_example.base;

import com.bdd_example.config.Configuration;
import com.bdd_example.core.PlaywrightManager;
import com.bdd_example.utils.AllureUtils;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base test class providing common utilities for all tests.
 * Does not contain lifecycle hooks - those are handled by Cucumber hooks in step definitions.
 * This class provides access to common resources like PlaywrightManager, Configuration, and utility methods.
 */
public abstract class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Configuration config = Configuration.getInstance();
    protected static final PlaywrightManager playwrightManager = PlaywrightManager.getInstance();

    /**
     * Gets the current page instance.
     *
     * @return Page instance
     */
    protected Page getPage() {
        return playwrightManager.getPage();
    }

    /**
     * Takes a screenshot and attaches it to the report.
     *
     * @param name Screenshot name
     */
    protected void takeScreenshot(String name) {
        AllureUtils.attachScreenshot(getPage(), name);
    }

    /**
     * Takes a full page screenshot and attaches it to the report.
     *
     * @param name Screenshot name
     */
    protected void takeFullPageScreenshot(String name) {
        AllureUtils.attachFullPageScreenshot(getPage(), name);
    }

    /**
     * Attaches the current page source to the report.
     */
    protected void attachPageSource() {
        AllureUtils.attachPageSource(getPage(), "Page Source");
    }

    /**
     * Attaches the current URL to the report.
     */
    protected void attachCurrentUrl() {
        AllureUtils.attachCurrentUrl(getPage());
    }
}
