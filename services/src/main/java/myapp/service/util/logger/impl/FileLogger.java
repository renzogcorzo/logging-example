package myapp.service.util.logger.impl;

import myapp.service.util.logger.MyAppLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileLogger implements MyAppLogger {

    private final Logger log;

    public FileLogger(String category) {
        log = LoggerFactory.getLogger(category);
    }

    @Override
    public void info(String messageText) {
        log.info(messageText);
    }

    @Override
    public void warning(String messageText) {
        log.warn(messageText);
    }

    @Override
    public void error(String messageText) {
        log.error(messageText);
    }
}
