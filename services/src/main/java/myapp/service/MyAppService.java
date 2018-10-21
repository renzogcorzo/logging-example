package myapp.service;

import myapp.model.EnvProperties;
import myapp.service.util.logger.MyAppLogFactory;
import myapp.service.util.logger.MyAppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyAppService {

    @Autowired
    private EnvProperties envProperties;

    private MyAppLogFactory logger;

    public void runApp(){
        initLogger(false);

        logger.info("info message");
        logger.warning("warning message");
        logger.error("error message");
    }

    public void initLogger(boolean reset){
        if(logger == null || reset){
            logger = new MyAppLogFactory(MyAppService.class.getName(), envProperties);
        }
    }

    public void setLogger(MyAppLogFactory logger){
        this.logger = logger;
    }

    public void setLoggingType(String loggingType){
        initLogger(false);
        logger.setLoggingType(loggingType);
    }

    public void setLoggingLevel(String loggingLevel){
        initLogger(false);
        logger.setLoggingLevel(loggingLevel);
    }

    public void resetLogger(){
        initLogger(true);
    }
}
