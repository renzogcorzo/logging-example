package myapp.service;

import myapp.service.util.logger.MyAppLogFactory;
import org.springframework.stereotype.Service;

@Service
public class MyAppService {

    public static final MyAppLogFactory logger = new MyAppLogFactory(MyAppService.class.getName());

    public void runApp(){
        logger.info("info message");
        logger.warning("warning message");
        logger.error("error message");
    }

    public void setLoggingType(String loggingType){
        logger.setLoggingType(loggingType);
    }

    public void setLoggingLevel(String loggingLevel){
        System.out.printf("Setting logging level to: " + loggingLevel);
        logger.setLoggingLevel(loggingLevel);
    }
}
