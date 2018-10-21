package myapp;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import myapp.service.MyAppService;
import myapp.service.util.logger.MyAppLogFactory;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.mockito.Mockito.*;


@SpringBootTest(classes = Application.class)
@ContextConfiguration
@TestPropertySource("classpath:/application-test.yml")
public class Stepdefs {

    @Autowired
    private MyAppService myAppService;

    @Mock
    private MyAppLogFactory logger;

    @BeforeEach
    public void init(){
        MyAppLogFactory.emptyLoggingTypes();
    }

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
        if("database".equalsIgnoreCase(loggingType)){
            myAppService.setLogger(logger);
            myAppService.setLoggingType(loggingType);
            MyAppLogFactory.setLoggingType(loggingType);

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
        Assert.assertTrue(MyAppLogFactory.hasLoggingType("console"));
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
        Assert.assertTrue(MyAppLogFactory.hasLoggingType("file"));
    }

    @Then("it should log to database")
    public void it_should_log_to_database() {

        System.out.println("hasLoggingType: " + MyAppLogFactory.hasLoggingType("database"));
        System.out.println("logging types: " + MyAppLogFactory.getLoggingTypes());
        Assert.assertTrue(MyAppLogFactory.hasLoggingType("database"));
        verify(logger, atLeastOnce()).info(Mockito.anyString());
    }

    @Given("I set the logging type to")
    public void i_set_the_logging_type_to(List<String> logTypes) {
        if(logTypes != null){
            MyAppLogFactory.emptyLoggingTypes();
            for(String l: logTypes){
                myAppService.setLoggingType(l);
            }
        }
    }

    @Then("it should log to")
    public void it_should_log_to(List<String> logTypes) {
        if(logTypes != null){
            for(String l: logTypes){
                Assert.assertTrue(MyAppLogFactory.hasLoggingType(l));
            }
        }
    }

    @Then("it should not log to")
    public void it_should_not_log_to(List<String> logTypes) {
        if(logTypes != null){
            for(String l: logTypes){
                Assert.assertFalse(MyAppLogFactory.hasLoggingType(l));
            }
        }
    }

}
