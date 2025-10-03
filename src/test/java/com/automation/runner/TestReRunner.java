package com.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@target/rerun.txt",
        glue = "com.automation.steps",
        plugin = {
                "json:target/rerun-cucumber.json",
                "html:target/rerun-cucumber.html"
        }
)
public class TestReRunner {

}
