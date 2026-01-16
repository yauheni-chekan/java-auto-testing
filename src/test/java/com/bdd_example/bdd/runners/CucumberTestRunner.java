package com.bdd_example.bdd.runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Cucumber test runner using JUnit 5 Platform Suite.
 * This class configures and runs all Cucumber feature files.
 * 
 * Note: Features are discovered via cucumber.features system property or default classpath:features
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, 
    value = "pretty, html:target/cucumber-reports/cucumber.html, json:target/cucumber-reports/cucumber.json, " +
            "junit:target/cucumber-reports/cucumber.xml, " +
            "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, 
    value = "com.bdd_example.bdd.steps")
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_ENABLED_PROPERTY_NAME, 
    value = "false")
public class CucumberTestRunner {
}
