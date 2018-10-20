package myapp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = MyAppServiceTestConfig.class)
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
@ContextConfiguration(classes= MyAppServiceTestConfig.class)

public class MyAppServiceTest {
}
