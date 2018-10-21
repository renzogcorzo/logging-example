package myapp.service.util.logger;

import myapp.model.EnvProperties;
import myapp.service.util.logger.impl.ConsoleLogger;
import myapp.service.util.logger.impl.DatabaseLogger;
import myapp.service.util.logger.impl.FileLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAppLogFactory implements MyAppLogger {

    private static String loggingLevel;
    private static List<String> loggingTypes;
    private String name;
    private EnvProperties environment;
    private MyAppLogger consoleLogger = null;
    private MyAppLogger fileLogger = null;;
    private MyAppLogger databaseLogger = null;


    static {
        loggingTypes = new ArrayList<>();
    }

    public MyAppLogFactory(String name, EnvProperties environment) {
        this.name = name;
        this.environment = environment;
        setMyAppLogger(name);
    }

    public void setLoggingTypeAndSetMyAppLogger(String loggingType) {
        setLoggingType(loggingType);
        setMyAppLogger(name);
    }

    public static void setLoggingType(String loggingType){
        if (loggingType != null) {
            if(!loggingTypes.contains(loggingType)){
                loggingTypes.add(loggingType);
            }
        }
    }

    public static List<String> getLoggingTypes(){
        return loggingTypes;
    }

    public static void emptyLoggingTypes(){
        loggingTypes = new ArrayList<>();
    }

    public void setLoggingLevel(String logLevel) {
        if (logLevel != null) {
            loggingLevel = logLevel;
            setMyAppLogger(this.name);
        }
    }

    @Override
    public void info(String messageText) {
        if (getLogLevel(loggingLevel).level >= LogLevel.INFO.level) {
            if(consoleLogger != null){
                consoleLogger.info(messageText);
            }
            if(fileLogger != null){
                fileLogger.info(messageText);
            }
            if(databaseLogger != null){
                databaseLogger.info(messageText);
            }
        }
    }

    @Override
    public void warning(String messageText) {
        if (getLogLevel(loggingLevel).level >= LogLevel.WARN.level) {
            if(consoleLogger != null){
                consoleLogger.warning(messageText);
            }
            if(fileLogger != null){
                fileLogger.warning(messageText);
            }
            if(databaseLogger != null){
                databaseLogger.warning(messageText);
            }
        }
    }

    @Override
    public void error(String messageText) {
        if (getLogLevel(loggingLevel).level >= LogLevel.ERROR.level) {
            if(consoleLogger != null){
                consoleLogger.error(messageText);
            }
            if(fileLogger != null){
                fileLogger.error(messageText);
            }
            if(databaseLogger != null){
                databaseLogger.error(messageText);
            }
        }
    }

    public static boolean hasLoggingType(String loggingType) {
       return loggingTypes.stream().filter(l -> l.equalsIgnoreCase(loggingType))
               .findFirst().isPresent();
    }

    public static String getLoggingLevel() {
        return loggingLevel;
    }

    private void setMyAppLogger(String category) {
        consoleLogger = null;
        fileLogger = null;
        databaseLogger = null;
        if (hasLoggingType("console")) {
            consoleLogger = new ConsoleLogger(category);
        } else if (hasLoggingType("file")) {
            fileLogger = new FileLogger(category);
        } else if (hasLoggingType("database")) {
            HashMap<String, String> dbParams = new HashMap<>();
            dbParams.put("userName", environment.getUserName());
            dbParams.put("password",environment.getPassword());
            dbParams.put("dbms",environment.getDbms());
            dbParams.put("portNumber",environment.getPortNumber());
            dbParams.put("serverName",environment.getServerName());

            databaseLogger = new DatabaseLogger(category, dbParams);
        }else {
            consoleLogger = new ConsoleLogger(category);
        }
    }

    private LogLevel getLogLevel(String logLevel) {
        LogLevel level = LogLevel.ALL;
        if (logLevel != null) {
            switch (logLevel) {
                case "console": {
                    level = LogLevel.INFO;
                    break;
                }
                case "warning": {
                    level = LogLevel.WARN;
                    break;
                }
                case "error": {
                    level = LogLevel.ERROR;
                    break;
                }

                default:
                    level = LogLevel.INFO;
            }
        }

        return level;
    }

    protected enum LogLevel {
        ERROR(200),
        WARN(300),
        INFO(400),
        ALL(Integer.MAX_VALUE);

        private final int level;

        LogLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
