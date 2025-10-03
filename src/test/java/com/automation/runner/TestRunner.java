package com.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/feature",
        glue = "com.automation.steps",
        plugin = {"json:target/cucumber.json", "html:target/cucumber.html","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm","rerun:target/rerun.txt" }
)
public class TestRunner {

}
