package myapp.service.util.logger;

/**
 * Created by renzogcorzo on 10/21/2018.
 */

public interface MyAppLogger {

    void info(String messageText);
    void warning(String messageText);
    void error(String messageText);

}
