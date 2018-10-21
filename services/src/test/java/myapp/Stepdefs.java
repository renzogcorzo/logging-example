package myapp;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import myapp.service.MyAppService;
import myapp.service.util.logger.MyAppLogFactory;
import myapp.service.util.logger.MyAppLogger;
import myapp.service.util.logger.impl.DatabaseLogger;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;


@SpringBootTest(classes = Application.class)
@ContextConfiguration
@TestPropertySource("classpath:/application-test.yml")
public class Stepdefs {

    @Autowired
    private MyAppService myAppService;

    @Mock
    private MyAppLogFactory logger;

    @Given("I run the app")
    public void i_run_the_app() {
        myAppService.runApp();
    }

    @When("I set the logging level to {string}")
    public void i_set_the_logging_level_to(String loggingLevel) {
        MockitoAnnotations.initMocks(this);
        myAppService.setLoggingLevel(loggingLevel);
    }

    @When("I set the logging type to {string}")
    public void i_set_the_logging_type_to(String loggingType) {
        myAppService.setLoggingType(loggingType);
        if("database".equalsIgnoreCase(loggingType)){
            myAppService.setLogger(logger);
            doNothing().when(logger).info(Mockito.anyString());
            doNothing().when(logger).warning(Mockito.anyString());
            doNothing().when(logger).error(Mockito.anyString());
        }
        else{
            myAppService.resetLogger();
            myAppService.setLoggingType(loggingType);
        }

    }

    @Then("it should log to console")
    public void it_should_log_to_console() {
        Assert.assertEquals("console", MyAppLogFactory.getLoggingType());
    }


    @Then("it should not log as {string}")
    public void it_should_not_log_as(String value) {
        MockitoAnnotations.initMocks(this);
        Assert.assertNotEquals(value, MyAppLogFactory.getLoggingLevel());
        if ("info".equalsIgnoreCase(value)) {
            verify(logger, never()).info(Mockito.anyString());
        }
        if ("warning".equalsIgnoreCase(value)) {
            verify(logger, never()).warning(Mockito.anyString());
        }

    }

    @Then("it should log as {string}")
    public void it_should_log_as(String value) {
        if ("info".equalsIgnoreCase(value)) {
            verify(logger, atLeastOnce()).info(Mockito.anyString());
        }
    }

    @Then("it should log to file")
    public void it_should_log_to_file() {
        Assert.assertEquals("file", logger.getLoggingType());
    }

    @Then("it should log to database")
    public void it_should_log_to_database() {
        Assert.assertEquals("database", logger.getLoggingType());
        verify(logger, atLeastOnce()).info(Mockito.anyString());

    }
}
