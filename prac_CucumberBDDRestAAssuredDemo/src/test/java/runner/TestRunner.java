package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
	@CucumberOptions(
			 features = ".//FeatureFile/", // Path to your feature files
		        glue = {"stepDefinitions"}, // Path to your step definitions
		        monochrome = true, // Makes the console output more readable
		        dryRun = true, // Set to true for checking if step definitions are missing
		        plugin = {
		                "json:build/cucumber-reports/cucumber.json", // JSON report
		                "html:build/cucumber-reports/cucumber.html", // HTML report
		                "junit:build/cucumber-reports/cucumber.xml", // JUnit XML report
		                "rerun:build/cucumber-reports/rerun.txt" // Rerun failed scenarios
		        },
		        tags = "@regression and not @ignore" // Example of using tags to include/exclude tests
		)
	public class TestRunner {
	}


