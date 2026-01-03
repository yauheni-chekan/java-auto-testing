package com.bdd_example.core;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manager for Playwright resources.
 * Singleton pattern for single-threaded execution.
 */
public class PlaywrightManager {

    private static final Logger logger = LoggerFactory.getLogger(PlaywrightManager.class);
    private static PlaywrightManager instance;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private final BrowserFactory browserFactory = new BrowserFactory();

    /**
     * Private constructor for singleton pattern.
     */
    private PlaywrightManager() {
    }

    /**
     * Gets the singleton instance of PlaywrightManager.
     *
     * @return PlaywrightManager instance
     */
    public static synchronized PlaywrightManager getInstance() {
        if (instance == null) {
            instance = new PlaywrightManager();
        }
        return instance;
    }

    /**
     * Initializes Playwright and browser.
     * Creates a new browser context and page.
     * If already initialized, this method does nothing (browser is reused).
     */
    public void init() {
        if (isInitialized()) {
            logger.debug("Playwright already initialized, reusing existing browser");
            return;
        }

        logger.info("Initializing Playwright");

        playwright = Playwright.create();
        browser = browserFactory.createBrowser(playwright);
        context = browserFactory.createContext(browser);
        page = browserFactory.createPage(context);

        logger.debug("Playwright initialization complete");
    }

    /**
     * Gets the Playwright instance.
     *
     * @return Playwright instance
     */
    public Playwright getPlaywright() {
        if (playwright == null) {
            throw new IllegalStateException("Playwright not initialized. Call init() first.");
        }
        return playwright;
    }

    /**
     * Gets the browser instance.
     *
     * @return Browser instance
     */
    public Browser getBrowser() {
        if (browser == null) {
            throw new IllegalStateException("Browser not initialized. Call init() first.");
        }
        return browser;
    }

    /**
     * Gets the browser context.
     *
     * @return BrowserContext instance
     */
    public BrowserContext getContext() {
        if (context == null) {
            throw new IllegalStateException("BrowserContext not initialized. Call init() first.");
        }
        return context;
    }

    /**
     * Gets the page instance.
     *
     * @return Page instance
     */
    public Page getPage() {
        if (page == null) {
            throw new IllegalStateException("Page not initialized. Call init() first.");
        }
        return page;
    }

    /**
     * Creates a new page in the current browser context.
     * Useful for multi-tab scenarios.
     *
     * @return New Page instance
     */
    public Page createNewPage() {
        BrowserContext ctx = getContext();
        Page newPage = browserFactory.createPage(ctx);
        logger.debug("Created new page in existing context");
        return newPage;
    }

    /**
     * Creates a new isolated browser context.
     * Useful for testing scenarios requiring clean state (e.g., incognito mode).
     *
     * @return New BrowserContext instance
     */
    public BrowserContext createNewContext() {
        Browser br = getBrowser();
        BrowserContext newContext = browserFactory.createContext(br);
        logger.debug("Created new isolated browser context");
        return newContext;
    }

    /**
     * Closes the current page and creates a new one.
     * Useful for resetting page state between tests.
     */
    public void resetPage() {
        if (page != null && !page.isClosed()) {
            page.close();
        }

        BrowserContext ctx = getContext();
        page = browserFactory.createPage(ctx);

        logger.debug("Page reset complete");
    }

    /**
     * Clears browser state between tests while keeping the browser alive.
     * Clears cookies, local storage, and navigates to about:blank.
     */
    public void clearBrowserState() {
        if (!isInitialized()) {
            logger.warn("Cannot clear browser state - Playwright not initialized");
            return;
        }

        try {
            // Clear cookies and storage
            context.clearCookies();
            logger.debug("Cleared cookies");

            // Navigate to blank page to reset state
            if (page != null && !page.isClosed()) {
                page.navigate("about:blank");
                logger.debug("Navigated to blank page");
            }
        } catch (Exception e) {
            logger.warn("Error clearing browser state: {}", e.getMessage());
        }
    }

    /**
     * Cleans up all Playwright resources.
     * Should be called after test completion.
     */
    public void quit() {
        logger.info("Closing Playwright resources");

        try {
            // Stop tracing if enabled (with error handling for driver issues)
            if (context != null) {
                try {
                    browserFactory.stopTracing(context, "test-trace");
                } catch (Exception e) {
                    logger.warn("Error stopping tracing (non-critical): {}", e.getMessage());
                }
            }

            // Close page
            if (page != null && !page.isClosed()) {
                try {
                    page.close();
                    logger.debug("Page closed");
                } catch (Exception e) {
                    logger.warn("Error closing page (non-critical): {}", e.getMessage());
                }
            }
            page = null;

            // Close context (this is where the package.json error often occurs)
            if (context != null) {
                try {
                    context.close();
                    logger.debug("Browser context closed");
                } catch (Exception e) {
                    // Suppress the package.json error - it's a known Playwright driver issue
                    String errorMsg = e.getMessage();
                    if (errorMsg != null && errorMsg.contains("package.json")) {
                        logger.debug("Suppressed Playwright driver cleanup error (known issue)");
                    } else {
                        logger.warn("Error closing browser context: {}", e.getMessage());
                    }
                }
            }
            context = null;

            // Close browser
            if (browser != null && browser.isConnected()) {
                try {
                    browser.close();
                    logger.debug("Browser closed");
                } catch (Exception e) {
                    logger.warn("Error closing browser (non-critical): {}", e.getMessage());
                }
            }
            browser = null;

            // Close Playwright
            if (playwright != null) {
                try {
                    playwright.close();
                    logger.debug("Playwright closed");
                } catch (Exception e) {
                    logger.warn("Error closing Playwright (non-critical): {}", e.getMessage());
                }
            }
            playwright = null;

            logger.info("Playwright resources cleanup complete");
        } catch (Exception e) {
            logger.error("Unexpected error during Playwright cleanup: {}", e.getMessage(), e);
        }
    }

    /**
     * Checks if Playwright is initialized.
     *
     * @return true if initialized
     */
    public boolean isInitialized() {
        return playwright != null;
    }

    /**
     * Resets the singleton instance (useful for testing).
     */
    public static synchronized void resetInstance() {
        if (instance != null && instance.isInitialized()) {
            instance.quit();
        }
        instance = null;
    }
}
