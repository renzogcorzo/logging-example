package myapp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import myapp.model.EnvProperties;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by renzogcorzo on 10/21/2018.
 */

@SpringBootTest
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"}
)
@ComponentScan(basePackages = {"myapp"})


public class TestApplication {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }

    @Bean
    public EnvProperties envProperties() {
        return new EnvProperties();
    }

}
