package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import com.example.assertions.HomePageAssertions;

/**
 * Test class for InMotion Hosting Homepage.
 * Demonstrates framework usage with Allure reporting and AssertJ assertions.
 */
@Epic("InMotion Hosting Website")
@Feature("Homepage")
@DisplayName("Homepage Tests")
class HomePageTest extends BaseTest {

    private HomePage homePage;
    private HomePageAssertions pageAssertions;
    @BeforeEach
    void initHomePage() {
        homePage = new HomePage();
        pageAssertions = new HomePageAssertions(homePage);
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
        pageAssertions.assertTitleIsCorrect();
        pageAssertions.assertUrlMatches("https://www.inmotionhosting.com/");
        logger.info("Homepage loaded successfully with title: {}", homePage.getPageTitle());
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
        pageAssertions.assertContainsNavBar();
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
        pageAssertions.assertContainsFooter();
        logger.info("Footer verified successfully");
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
        pageAssertions.assertUrlMatches("https://www.inmotionhosting.com/");
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
        pageAssertions.assertLoadTime(loadTime);
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
            pageAssertions.assertVisible(homePage.header().getVpsHostingLink());
        }

        @Test
        @Story("Navigation")
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Dedicated Servers dropdown should be visible in header")
        void testDedicatedServersDropdownVisible() {
            // Arrange
            homePage.open();

            // Assert
            pageAssertions.assertVisible(homePage.header().getDedicatedServersDropdown());
        }
    }
}
