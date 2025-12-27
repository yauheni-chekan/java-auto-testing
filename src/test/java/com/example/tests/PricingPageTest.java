package com.example.tests;

import com.example.pages.PricingPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import com.example.assertions.PricingPageAssertions;
import com.example.base.BaseTest;

@Epic("InMotion Hosting Website")
@Feature("Pricing Page")
@DisplayName("Pricing Page Tests")
public class PricingPageTest extends BaseTest {
    private PricingPage pricingPage;
    private PricingPageAssertions pageAssertions;

    @BeforeEach
    void initPricingPage() {
        pricingPage = new PricingPage();
        pageAssertions = new PricingPageAssertions(pricingPage);
    }
    
    @Test
    @Tag("smoke")
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Pricing page should load successfully")
    void testPricingPageLoads() {
        pricingPage.open();
        pageAssertions.assertPageIsLoaded();
        pageAssertions.assertTitleIsCorrect();
    }
}
