package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import com.example.pages.PricingPage;
import com.example.utils.AllureUtils;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import com.example.assertions.HomePageAssertions;
import com.example.assertions.PricingPageAssertions;

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
    @Tag("smoke")
    @DisplayName("Homepage should load successfully")
    @Description("Verify that the InMotion Hosting homepage loads with all essential elements visible")
    void testHomePageLoads() {
        homePage.open();
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
        homePage.open();
        homePage.footer().scrollIntoView();
        pageAssertions.assertContainsFooter();
        logger.info("Footer verified successfully");
    }

    @Test
    @Story("Navigation")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Logo click should stay on homepage")
    @Description("Verify that clicking the logo navigates to or stays on the homepage")
    void testLogoNavigation() {
        homePage.open();
        homePage.header().clickLogo();

        pageAssertions.assertUrlMatches("https://www.inmotionhosting.com/");
        logger.info("Logo navigation verified - current URL: {}", homePage.getCurrentUrl());
    }

    @Test
    @Story("Page Load")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Page should have reasonable load performance")
    @Description("Verify that the homepage loads within acceptable time limits")
    void testPageLoadPerformance() {
        long startTime = System.currentTimeMillis();
        homePage.open();
        long loadTime = System.currentTimeMillis() - startTime;
        pageAssertions.assertLoadTime(loadTime);
    }


    @Test
    @Tag("smoke")
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Clicking the Pricing link should navigate to the Pricing page")
    void testClickPricingLink() {
        homePage.open();
        PricingPage pricingPage = homePage.header().navigateToPricing();
        PricingPageAssertions pricingPageAssertions = new PricingPageAssertions(pricingPage);
        pricingPageAssertions.assertPageIsLoaded();
        pricingPageAssertions.assertTitleIsCorrect();
    }


    @Nested
    @DisplayName("Header Component Tests")
    class HeaderComponentTests {

        @Test
        @Tag("smoke")
        @Story("Navigation")
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("VPS Hosting link should be visible in header")
        void testVpsHostingLinkVisible() {
            homePage.open();
            pageAssertions.assertVisible(homePage.header().getVpsHostingLink());
        }

        @Test
        @Tag("smoke")
        @Story("Navigation")
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Dedicated Servers dropdown should be visible in header")
        void testDedicatedServersDropdownVisible() {
            homePage.open();
            pageAssertions.assertVisible(homePage.header().getDedicatedServersDropdown());
        }

        @Test
        @Tag("smoke")
        @Story("Navigation")
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Pricing link should be visible in header")
        void testPricingLinkVisible() {
            homePage.open();
            pageAssertions.assertVisible(homePage.header().getPricingLink());
        }
    }

    @Nested
    @DisplayName("Footer Component Tests")
    class FooterComponentTests {
        
        @Test
        @Tag("smoke")
        @Story("Navigation")
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Cookie Preferences link should be visible in footer")
        void testCookiePreferencesLinkVisible() {
            homePage.open();
            pageAssertions.assertVisible(homePage.footer().getCookiePreferencesLink());
        }

        @Test
        @Tag("cookie")
        @Story("Cookie Preferences")
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Cookie Preferences modal should be visible")
        void testCookiePreferencesModalVisible() {
            homePage.open();
            homePage.footer().clickCookiePreferences();
            pageAssertions.assertVisible(homePage.footer().getCookiePreferencesModal());
        }

        @Test
        @Tag("cookie")
        @Story("Cookie Preferences")
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("All Cookies headings should be visible in cookie preferences modal")
        void testAllCookiesHeadingsVisible() {
            homePage.open();
            homePage.footer().clickCookiePreferences();
            pageAssertions.assertVisible(homePage.footer().getPerformanceCookiesHeading());
            pageAssertions.assertVisible(homePage.footer().getFunctionalCookiesHeading());
            pageAssertions.assertVisible(homePage.footer().getStrictlyNecessaryCookiesHeading());
            pageAssertions.assertVisible(homePage.footer().getTargetingCookiesHeading());   
            logger.info("All Cookies headings verified successfully");
        }

        @Test
        @Tags({@Tag("cookie"), @Tag("visual-verification")})
        @Issue("IMH-CP-001")
        @Flaky
        @Story("Cookie Preferences")
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Performance Cookies description should be visible in cookie preferences modal")
        void testPerformanceCookiesDescriptionVisible() {
            homePage.open();
            homePage.footer().clickCookiePreferences();
            homePage.footer().clickPerformanceCookies();
            homePage.getPage().waitForTimeout(1000);
            AllureUtils.attachScreenshotViaLifecycle(homePage.getPage(), "Performance Cookies Description");
            pageAssertions.assertVisible(homePage.footer().getPerformanceCookiesDescription());
        }

        @Test
        @Tags({@Tag("cookie"), @Tag("visual-verification")})
        @Issue("IMH-CP-001")
        @Flaky
        @Story("Cookie Preferences")
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Functional Cookies description should be visible in cookie preferences modal")
        void testFunctionalCookiesDescriptionVisible() {
            homePage.open();
            homePage.footer().clickCookiePreferences();
            homePage.footer().clickFunctionalCookies();
            homePage.getPage().waitForTimeout(1000);
            AllureUtils.attachScreenshotViaLifecycle(homePage.getPage(), "Functional Cookies Description");
            pageAssertions.assertVisible(homePage.footer().getFunctionalCookiesDescription());
        }

        @Test
        @Tags({@Tag("cookie"), @Tag("visual-verification")})
        @Issue("IMH-CP-001")
        @Flaky
        @Story("Cookie Preferences")
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Strictly Necessary Cookies description should be visible in cookie preferences modal")
        void testStrictlyNecessaryCookiesDescriptionVisible() {
            homePage.open();
            homePage.footer().clickCookiePreferences();
            homePage.footer().clickStrictlyNecessaryCookies();
            homePage.getPage().waitForTimeout(1000);
            AllureUtils.attachScreenshotViaLifecycle(homePage.getPage(), "Strictly Necessary Cookies Description");
            pageAssertions.assertVisible(homePage.footer().getStrictlyNecessaryCookiesDescription());
        }

        @Test
        @Tags({@Tag("cookie"), @Tag("visual-verification")})
        @Issue("IMH-CP-001")
        @Flaky
        @Story("Cookie Preferences")
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("Targeting Cookies description should be visible in cookie preferences modal")
        void testTargetingCookiesDescriptionVisible() {
            homePage.open();
            homePage.footer().clickCookiePreferences();
            homePage.footer().clickTargetingCookies();
            homePage.getPage().waitForTimeout(1000);
            AllureUtils.attachScreenshotViaLifecycle(homePage.getPage(), "Targeting Cookies Description");
            pageAssertions.assertVisible(homePage.footer().getTargetingCookiesDescription());
        }
    }
}
