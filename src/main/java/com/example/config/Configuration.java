package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration singleton for managing framework settings.
 * Loads properties from config.properties and supports system property overrides.
 */
public final class Configuration {

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    private static final String CONFIG_FILE = "config.properties";
    private static Configuration instance;
    private final Properties properties;

    private Configuration() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Gets the singleton instance of Configuration.
     *
     * @return Configuration instance
     */
    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                logger.warn("Configuration file '{}' not found. Using default values.", CONFIG_FILE);
                return;
            }
            properties.load(inputStream);
            logger.info("Configuration loaded successfully from '{}'", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", e.getMessage());
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    /**
     * Gets a property value. System properties take precedence over config file values.
     *
     * @param key          Property key
     * @param defaultValue Default value if property is not found
     * @return Property value
     */
    public String getProperty(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            logger.debug("Using system property for '{}': {}", key, systemValue);
            return systemValue;
        }
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Gets a property value. System properties take precedence over config file values.
     *
     * @param key Property key
     * @return Property value or null if not found
     */
    public String getProperty(String key) {
        return getProperty(key, null);
    }

    /**
     * Gets a boolean property value.
     *
     * @param key          Property key
     * @param defaultValue Default value if property is not found
     * @return Boolean value
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    /**
     * Gets an integer property value.
     *
     * @param key          Property key
     * @param defaultValue Default value if property is not found
     * @return Integer value
     */
    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer value for '{}': {}. Using default: {}", key, value, defaultValue);
            return defaultValue;
        }
    }

    /**
     * Gets a long property value.
     *
     * @param key          Property key
     * @param defaultValue Default value if property is not found
     * @return Long value
     */
    public long getLongProperty(String key, long defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid long value for '{}': {}. Using default: {}", key, value, defaultValue);
            return defaultValue;
        }
    }

    // =========================================================================
    // Convenience methods for common configuration values
    // =========================================================================

    /**
     * Gets the base URL for the application under test.
     *
     * @return Base URL
     */
    public String getBaseUrl() {
        return getProperty("base.url", "https://www.inmotionhosting.com/");
    }

    /**
     * Gets the browser type to use.
     *
     * @return Browser type (chromium, firefox, webkit)
     */
    public String getBrowserType() {
        return getProperty("browser.type", "chromium").toLowerCase();
    }

    /**
     * Checks if tests should run in headless mode.
     *
     * @return true if headless mode is enabled
     */
    public boolean isHeadless() {
        return getBooleanProperty("browser.headless", true);
    }

    /**
     * Gets the viewport width.
     *
     * @return Viewport width in pixels
     */
    public int getViewportWidth() {
        return getIntProperty("browser.viewport.width", 1920);
    }

    /**
     * Gets the viewport height.
     *
     * @return Viewport height in pixels
     */
    public int getViewportHeight() {
        return getIntProperty("browser.viewport.height", 1080);
    }

    /**
     * Gets the slow motion delay in milliseconds.
     *
     * @return Slow motion delay
     */
    public int getSlowMo() {
        return getIntProperty("browser.slow.mo", 0);
    }

    /**
     * Gets the default timeout in milliseconds.
     *
     * @return Default timeout
     */
    public int getDefaultTimeout() {
        return getIntProperty("timeout.default", 30000);
    }

    /**
     * Gets the navigation timeout in milliseconds.
     *
     * @return Navigation timeout
     */
    public int getNavigationTimeout() {
        return getIntProperty("timeout.navigation", 60000);
    }

    /**
     * Gets the element interaction timeout in milliseconds.
     *
     * @return Element timeout
     */
    public int getElementTimeout() {
        return getIntProperty("timeout.element", 10000);
    }

    /**
     * Checks if screenshots should be taken on test failure.
     *
     * @return true if screenshot on failure is enabled
     */
    public boolean isScreenshotOnFailure() {
        return getBooleanProperty("screenshot.on.failure", true);
    }

    /**
     * Gets the screenshot format.
     *
     * @return Screenshot format (png or jpeg)
     */
    public String getScreenshotFormat() {
        return getProperty("screenshot.format", "png");
    }

    /**
     * Gets the screenshot directory.
     *
     * @return Screenshot directory path
     */
    public String getScreenshotDirectory() {
        return getProperty("screenshot.directory", "target/screenshots");
    }

    /**
     * Checks if Playwright tracing is enabled.
     *
     * @return true if tracing is enabled
     */
    public boolean isTracingEnabled() {
        return getBooleanProperty("tracing.enabled", false);
    }

    /**
     * Gets the retry count for flaky tests.
     *
     * @return Retry count
     */
    public int getRetryCount() {
        return getIntProperty("retry.count", 0);
    }
}

