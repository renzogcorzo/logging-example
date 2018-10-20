package myapp;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import myapp.service.MyAppService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ContextConfiguration(classes= MyAppServiceTestConfig.class)
public class Stepdefs {

    @Autowired
    private MyAppService myAppService;

    @Given("I have the app running")
    public void i_have_the_app_running() {
        myAppService.runApp();
    }

    @When("I set the logging to console")
    public void i_set_the_logging_to_console() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("it should log to console")
    public void it_should_log_to_console() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
