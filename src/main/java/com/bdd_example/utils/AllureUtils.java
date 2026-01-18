package com.bdd_example.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

/**
 * Utility class for Allure report attachments and enhancements.
 */
public final class AllureUtils {

    private static final Logger logger = LoggerFactory.getLogger(AllureUtils.class);

    private AllureUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Takes a screenshot and attaches it to the Allure report.
     * Works both in test methods and TestWatcher context.
     *
     * @param page Page instance
     * @param name Screenshot name
     * @return Screenshot bytes
     */
    @Attachment(value = "{name}", type = "image/png")
    public static byte[] attachScreenshot(Page page, String name) {
        logger.debug("Taking screenshot: {}", name);
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(false));
        logger.debug("Screenshot captured: {} ({} bytes)", name, screenshot.length);
        return screenshot;
    }

    /**
     * Attaches a screenshot using Allure Lifecycle API.
     * Use this method when attaching from TestWatcher or other non-test contexts.
     *
     * @param page Page instance
     * @param name Screenshot name
     */
    public static void attachScreenshotViaLifecycle(Page page, String name) {
        try {
            logger.debug("Taking screenshot via lifecycle: {}", name);
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(false));

            safeAddAttachment(name, "image/png", screenshot, ".png");

            logger.debug("Screenshot attached via lifecycle: {} ({} bytes)", name, screenshot.length);
        } catch (Exception e) {
            logger.error("Failed to attach screenshot via lifecycle: {}", e.getMessage(), e);
        }
    }

    /**
     * Takes a full page screenshot and attaches it to the Allure report.
     *
     * @param page Page instance
     * @param name Screenshot name
     * @return Screenshot bytes
     */
    @Attachment(value = "{name}", type = "image/png")
    public static byte[] attachFullPageScreenshot(Page page, String name) {
        logger.debug("Taking full page screenshot: {}", name);
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        logger.debug("Full page screenshot captured: {} ({} bytes)", name, screenshot.length);
        return screenshot;
    }

    /**
     * Attaches a full page screenshot using Allure Lifecycle API.
     * Use this method when attaching from TestWatcher or other non-test contexts.
     *
     * @param page Page instance
     * @param name Screenshot name
     */
    public static void attachFullPageScreenshotViaLifecycle(Page page, String name) {
        try {
            logger.debug("Taking full page screenshot via lifecycle: {}", name);
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));

            safeAddAttachment(name, "image/png", screenshot, ".png");

            logger.debug("Full page screenshot attached via lifecycle: {} ({} bytes)", name, screenshot.length);
        } catch (Exception e) {
            logger.error("Failed to attach full page screenshot via lifecycle: {}", e.getMessage(), e);
        }
    }

    /**
     * Attaches page HTML source to the Allure report.
     *
     * @param page Page instance
     * @param name Attachment name
     * @return HTML content
     */
    @Attachment(value = "{name}", type = "text/html")
    public static String attachPageSource(Page page, String name) {
        logger.debug("Attaching page source: {}", name);
        return page.content();
    }

    /**
     * Attaches page HTML source using Allure Lifecycle API.
     * Use this method when attaching from TestWatcher or other non-test contexts.
     *
     * @param page Page instance
     * @param name Attachment name
     */
    public static void attachPageSourceViaLifecycle(Page page, String name) {
        try {
            String pageSource = page.content();
            byte[] contentBytes = pageSource.getBytes(StandardCharsets.UTF_8);
            safeAddAttachment(name, "text/html", contentBytes, ".html");

            logger.debug("Page source attached via lifecycle: {}", name);
        } catch (Exception e) {
            logger.error("Failed to attach page source via lifecycle: {}", e.getMessage(), e);
        }
    }

    /**
     * Attaches text content to the Allure report.
     *
     * @param name    Attachment name
     * @param content Text content
     * @return Text content
     */
    @Attachment(value = "{name}", type = "text/plain")
    public static String attachText(String name, String content) {
        logger.debug("Attaching text: {}", name);
        return content;
    }

    /**
     * Attaches text content using Allure Lifecycle API.
     * Use this method when attaching from TestWatcher or other non-test contexts.
     *
     * @param name    Attachment name
     * @param content Text content
     */
    public static void attachTextViaLifecycle(String name, String content) {
        try {
            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
            safeAddAttachment(name, "text/plain", contentBytes, ".txt");

            logger.debug("Text attached via lifecycle: {}", name);
        } catch (Exception e) {
            logger.error("Failed to attach text via lifecycle: {}", e.getMessage(), e);
        }
    }

    /**
     * Attaches JSON content to the Allure report.
     *
     * @param name    Attachment name
     * @param content JSON content
     * @return JSON content
     */
    @Attachment(value = "{name}", type = "application/json")
    public static String attachJson(String name, String content) {
        logger.debug("Attaching JSON: {}", name);
        return content;
    }

    /**
     * Attaches the current URL to the Allure report.
     *
     * @param page Page instance
     * @return Current URL
     */
    @Attachment(value = "Current URL", type = "text/plain")
    public static String attachCurrentUrl(Page page) {
        String url = page.url();
        logger.debug("Attaching current URL: {}", url);
        return url;
    }

    /**
     * Attaches browser console logs to the Allure report.
     *
     * @param logs Console log content
     * @return Console logs
     */
    @Attachment(value = "Browser Console Logs", type = "text/plain")
    public static String attachConsoleLogs(String logs) {
        logger.debug("Attaching console logs");
        return logs;
    }

    /**
     * Saves screenshot to file system.
     *
     * @param page      Page instance
     * @param directory Directory path
     * @param fileName  File name (without extension)
     * @return Path to saved screenshot
     */
    public static Path saveScreenshotToFile(Page page, String directory, String fileName) {
        try {
            Path dirPath = Paths.get(directory);
            Files.createDirectories(dirPath);

            Path filePath = dirPath.resolve(fileName + ".png");
            page.screenshot(new Page.ScreenshotOptions().setPath(filePath));

            logger.info("Screenshot saved to: {}", filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }

    /**
     * Attaches a file to the Allure report.
     *
     * @param filePath Path to the file
     * @param name     Attachment name
     */
    public static void attachFile(Path filePath, String name) {
        try {
            byte[] content = Files.readAllBytes(filePath);
            String mimeType = Files.probeContentType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            Allure.addAttachment(name, mimeType, new ByteArrayInputStream(content), getFileExtension(filePath));
            logger.debug("File attached to report: {}", name);
        } catch (IOException e) {
            logger.error("Failed to attach file: {}", e.getMessage(), e);
        }
    }

    /**
     * Adds a step to the Allure report programmatically.
     *
     * @param stepName Name of the step
     * @param runnable Step execution logic
     */
    public static void step(String stepName, Runnable runnable) {
        logger.info("Step: {}", stepName);
        Allure.step(stepName, () -> {
            runnable.run();
        });
    }

    /**
     * Adds an informational message to the Allure report.
     *
     * @param message Message content
     */
    public static void addMessage(String message) {
        Allure.addAttachment("Info", "text/plain", message);
        logger.info("Allure message: {}", message);
    }

    /**
     * Adds a link to the Allure report.
     *
     * @param name Link name
     * @param url  Link URL
     */
    public static void addLink(String name, String url) {
        Allure.link(name, url);
        logger.debug("Added link to report: {} -> {}", name, url);
    }

    /**
     * Sets the test description in the Allure report.
     *
     * @param description Test description
     */
    public static void setDescription(String description) {
        Allure.description(description);
        logger.debug("Set test description: {}", description);
    }

    /**
     * Adds a parameter to the current test in Allure report.
     *
     * @param name  Parameter name
     * @param value Parameter value
     */
    public static void addParameter(String name, String value) {
        Allure.parameter(name, value);
        logger.debug("Added parameter: {} = {}", name, value);
    }

    /**
     * Ensures the Allure results directory exists.
     * This is required before writing attachments.
     * Also ensures subdirectories can be created for attachment files.
     */
    private static void ensureAllureResultsDirectory() {
        try {
            String allureResultsDir = System.getProperty("allure.results.directory", "target/allure-results");
            Path resultsPath = Paths.get(allureResultsDir);
            if (!Files.exists(resultsPath)) {
                Files.createDirectories(resultsPath);
                logger.debug("Created Allure results directory: {}", resultsPath);
            }
        } catch (IOException e) {
            logger.warn("Failed to ensure Allure results directory exists: {}", e.getMessage());
        }
    }

    /**
     * Safely adds an attachment using Allure API.
     * Uses Allure.addAttachment which handles directory creation automatically.
     *
     * @param name          Attachment name
     * @param mimeType      MIME type (e.g., "image/png", "text/plain")
     * @param content       Attachment content as byte array
     * @param fileExtension File extension (e.g., ".png", ".txt", ".html")
     */
    private static void safeAddAttachment(String name, String mimeType, byte[] content, String fileExtension) {
        try {
            // Ensure base directory exists
            ensureAllureResultsDirectory();

            // Use Allure.addAttachment which handles directory creation better
            Allure.addAttachment(name, mimeType, new ByteArrayInputStream(content), fileExtension);

        } catch (Exception e) {
            // Log the error but don't fail the test
            logger.error("Failed to attach to Allure report ({}): {}", name, e.getMessage());
            logger.debug("Attachment error details", e);
        }
    }

    private static String getFileExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex > 0 ? fileName.substring(dotIndex) : "";
    }
}
