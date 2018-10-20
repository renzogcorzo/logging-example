package myapp.service.util.logger.impl;

import myapp.service.util.logger.MyAppLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class ConsoleLogger implements MyAppLogger {
    private static Logger logger;
    private static final String DATE_PATTERN = "yyyy/MM/dd hh:mm:ss a";

    public ConsoleLogger(String name) {
        logger = Logger.getLogger(name);
        logger.addHandler(new StreamHandler(System.out, new Formatter() {
            @Override
            public String format(LogRecord record) {
                return new SimpleDateFormat(DATE_PATTERN).format( new Date(record.getMillis()) )  + " " +  record.getLoggerName() + "\n"  + record.getLevel() + ": " +record.getMessage() + '\n';
            }
        }));
        logger.setUseParentHandlers(false);
    }

    @Override
    public void info(String messageText) {
        logger.log(Level.INFO, messageText);
    }

    @Override
    public void warning(String messageText) {
        logger.log(Level.WARNING, messageText);
    }

    @Override
    public void error(String messageText) {
        logger.log(Level.SEVERE, messageText);
    }

}
