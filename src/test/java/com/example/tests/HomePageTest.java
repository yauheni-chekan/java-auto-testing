package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for InMotion Hosting Homepage.
 * Demonstrates framework usage with Allure reporting and AssertJ assertions.
 */
@Epic("InMotion Hosting Website")
@Feature("Homepage")
@DisplayName("Homepage Tests")
class HomePageTest extends BaseTest {

    private HomePage homePage;

    @BeforeEach
    void initHomePage() {
        homePage = new HomePage();
    }

    @Test
    @Story("Page Load")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Homepage should load successfully")
    @Description("Verify that the InMotion Hosting homepage loads with all essential elements visible")
    void testHomePageLoads() {
        // Arrange & Act
        homePage.open();

        // Assert
        assertThat(homePage.getPageTitle())
                .as("Page title should contain InMotion")
                .containsIgnoringCase("InMotion");

        assertThat(homePage.getCurrentUrl())
                .as("URL should be InMotion Hosting")
                .contains("inmotionhosting.com");

        logger.info("Homepage loaded successfully with title: {}", homePage.getPageTitle());
    }

    @Test
    @Story("Page Elements")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Hero section should be visible with title")
    @Description("Verify that the hero section is displayed with the main title")
    void testHeroSectionVisible() {
        // Arrange
        homePage.open();

        // Act & Assert
        assertThat(homePage.isHeroSectionVisible())
                .as("Hero section should be visible")
                .isTrue();

        String heroTitle = homePage.getHeroTitleText();
        assertThat(heroTitle)
                .as("Hero title should not be empty")
                .isNotBlank();

        logger.info("Hero section visible with title: {}", heroTitle);
        takeScreenshot("Hero Section");
    }

    @Test
    @Story("Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Header navigation should be visible")
    @Description("Verify that the header navigation component is displayed and functional")
    void testHeaderNavigationVisible() {
        // Arrange
        homePage.open();

        // Act & Assert
        assertThat(homePage.header().isVisible())
                .as("Header should be visible")
                .isTrue();

        assertThat(homePage.header().isLogoVisible())
                .as("Logo should be visible in header")
                .isTrue();

        logger.info("Header navigation verified successfully");
    }

    @Test
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Footer should be visible with legal links")
    @Description("Verify that the footer section is displayed with copyright and legal information")
    void testFooterVisible() {
        // Arrange
        homePage.open();

        // Act
        homePage.footer().scrollIntoView();

        // Assert
        assertThat(homePage.footer().isVisible())
                .as("Footer should be visible")
                .isTrue();

        String copyrightText = homePage.footer().getCopyrightText();
        assertThat(copyrightText)
                .as("Copyright text should contain year or InMotion")
                .containsIgnoringCase("InMotion");

        logger.info("Footer verified with copyright: {}", copyrightText);
        takeScreenshot("Footer Section");
    }

    @Test
    @Story("Page Elements")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Page should display hosting plan options")
    @Description("Verify that hosting plan cards are visible on the homepage")
    void testHostingPlansDisplayed() {
        // Arrange
        homePage.open();

        // Act
        homePage.scrollToHostingPlans();
        takeScreenshot("Hosting Plans Section");

        // Assert
        assertThat(homePage.isHostingPlansSectionVisible())
                .as("Hosting plans section should be visible")
                .isTrue();

        logger.info("Hosting plans section verified");
    }

    @Test
    @Story("Navigation")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Logo click should stay on homepage")
    @Description("Verify that clicking the logo navigates to or stays on the homepage")
    void testLogoNavigation() {
        // Arrange
        homePage.open();

        // Act
        homePage.header().clickLogo();

        // Assert
        assertThat(homePage.getCurrentUrl())
                .as("Should remain on homepage after logo click")
                .contains("inmotionhosting.com");

        logger.info("Logo navigation verified - current URL: {}", homePage.getCurrentUrl());
    }

    @Test
    @Story("Page Load")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Page should have reasonable load performance")
    @Description("Verify that the homepage loads within acceptable time limits")
    void testPageLoadPerformance() {
        // Arrange
        long startTime = System.currentTimeMillis();

        // Act
        homePage.open();

        // Assert
        long loadTime = System.currentTimeMillis() - startTime;
        assertThat(loadTime)
                .as("Page should load within 30 seconds")
                .isLessThan(30000);

        logger.info("Page load time: {}ms", loadTime);
        Allure.addAttachment("Load Time", "text/plain", loadTime + " ms");
    }

    @Test
    @Story("Responsiveness")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Page should capture full page screenshot")
    @Description("Verify that full page screenshot functionality works correctly")
    void testFullPageScreenshot() {
        // Arrange
        homePage.open();

        // Act
        homePage.scrollToBottom();

        // Assert - mainly for visual verification via screenshot
        takeFullPageScreenshot("Full Page Screenshot");

        assertThat(homePage.footer().isVisible())
                .as("Footer should be visible after scrolling")
                .isTrue();

        logger.info("Full page screenshot captured successfully");
    }

    @Nested
    @DisplayName("Header Component Tests")
    class HeaderComponentTests {

        @Test
        @Story("Navigation")
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("VPS Hosting link should be visible in header")
        void testVpsHostingLinkVisible() {
            // Arrange
            homePage.open();

            // Assert
            assertThat(homePage.header().getVpsHostingLink().isVisible())
                    .as("VPS Hosting link should be visible")
                    .isTrue();
        }

        @Test
        @Story("Navigation")
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Dedicated Servers link should be visible in header")
        void testDedicatedServersLinkVisible() {
            // Arrange
            homePage.open();

            // Assert
            assertThat(homePage.header().getDedicatedServersLink().isVisible())
                    .as("Dedicated Servers link should be visible")
                    .isTrue();
        }
    }
}

