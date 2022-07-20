package com.test.soderberg.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(glue = "com.test.soderberg.stepdef", features = "src/test/resources/features/SoderbergGETRequests.feature",
plugin = {"pretty", "summary", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}, monochrome = true)
public class GETRequestsTestRunner extends AbstractTestNGCucumberTests {

}
