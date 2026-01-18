package com.example.core;

import com.example.config.Configuration;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Factory class for creating and configuring browser instances.
 * Supports Chromium, Firefox, and WebKit browsers with configurable options.
 */
public class BrowserFactory {

    private static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class);
    private final Configuration config;

    public BrowserFactory() {
        this.config = Configuration.getInstance();
    }

    /**
     * Creates a new browser instance based on configuration.
     *
     * @param playwright Playwright instance
     * @return Configured browser instance
     */
    public Browser createBrowser(Playwright playwright) {
        String browserType = config.getBrowserType();
        logger.info("Creating browser of type: {}", browserType);

        BrowserType.LaunchOptions launchOptions = getLaunchOptions();

        return switch (browserType.toLowerCase()) {
            case "firefox" -> {
                logger.debug("Launching Firefox browser");
                yield playwright.firefox().launch(launchOptions);
            }
            case "webkit" -> {
                logger.debug("Launching WebKit browser");
                yield playwright.webkit().launch(launchOptions);
            }
            default -> {
                logger.debug("Launching Chromium browser");
                yield playwright.chromium().launch(launchOptions);
            }
        };
    }

    /**
     * Creates browser launch options from configuration.
     *
     * @return Launch options
     */
    private BrowserType.LaunchOptions getLaunchOptions() {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(config.isHeadless())
                .setSlowMo(config.getSlowMo());

        // Add common browser arguments for stability
        if (config.getBrowserType().equals("chromium")) {
            options.setArgs(Arrays.asList(
                    "--disable-dev-shm-usage",
                    "--no-sandbox",
                    "--disable-gpu",
                    "--disable-extensions"
            ));
        }

        logger.debug("Browser launch options - Headless: {}, SlowMo: {}ms",
                config.isHeadless(), config.getSlowMo());

        return options;
    }

    /**
     * Creates a new browser context with configured viewport and settings.
     *
     * @param browser Browser instance
     * @return Configured browser context
     */
    public BrowserContext createContext(Browser browser) {
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(config.getViewportWidth(), config.getViewportHeight())
                .setIgnoreHTTPSErrors(true)
                .setJavaScriptEnabled(true);

        logger.debug("Creating browser context with viewport: {}x{}",
                config.getViewportWidth(), config.getViewportHeight());

        BrowserContext context = browser.newContext(contextOptions);

        // Configure tracing if enabled
        if (config.isTracingEnabled()) {
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
            logger.info("Tracing enabled for browser context");
        }

        return context;
    }

    /**
     * Creates a new page with configured timeouts.
     *
     * @param context Browser context
     * @return Configured page instance
     */
    public Page createPage(BrowserContext context) {
        Page page = context.newPage();

        // Set default timeouts
        page.setDefaultTimeout(config.getDefaultTimeout());
        page.setDefaultNavigationTimeout(config.getNavigationTimeout());

        logger.debug("Created new page with default timeout: {}ms, navigation timeout: {}ms",
                config.getDefaultTimeout(), config.getNavigationTimeout());

        return page;
    }

    /**
     * Stops tracing and saves the trace file.
     *
     * @param context   Browser context
     * @param traceName Name for the trace file
     */
    public void stopTracing(BrowserContext context, String traceName) {
        if (config.isTracingEnabled()) {
            String tracePath = String.format("target/traces/%s.zip", traceName);
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(tracePath)));
            logger.info("Trace saved to: {}", tracePath);
        }
    }
}

