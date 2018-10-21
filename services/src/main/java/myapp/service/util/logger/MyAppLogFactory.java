package myapp.service.util.logger;

import myapp.model.EnvProperties;
import myapp.service.util.logger.impl.ConsoleLogger;
import myapp.service.util.logger.impl.DatabaseLogger;
import myapp.service.util.logger.impl.FileLogger;

import java.util.HashMap;

public class MyAppLogFactory implements MyAppLogger {

    private static String loggingLevel;
    private static String loggingType = "console";
    private String name;
    private EnvProperties environment;

    private MyAppLogger myAppLogger;

    public MyAppLogger getMyAppLogger() {
        return myAppLogger;
    }

    public MyAppLogFactory(String name, EnvProperties environment) {
        this.name = name;
        this.environment = environment;
        setMyAppLogger(name);
    }

    public void setLoggingType(String loggingType) {
        if (loggingType != null) {
            this.loggingType = loggingType;
            setMyAppLogger(this.name);
        }
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
            myAppLogger.info(messageText);
        }

    }

    @Override
    public void warning(String messageText) {
        if (getLogLevel(loggingLevel).level >= LogLevel.WARN.level) {
            myAppLogger.warning(messageText);
        }
    }

    @Override
    public void error(String messageText) {
        if (getLogLevel(loggingLevel).level >= LogLevel.ERROR.level) {
            myAppLogger.error(messageText);
        }
    }

    public static String getLoggingType() {
        return loggingType;
    }

    public static String getLoggingLevel() {
        return loggingLevel;
    }

    private void setMyAppLogger(String category) {
        if (this.loggingType.equalsIgnoreCase("console")) {
            myAppLogger = new ConsoleLogger(category);
        } else if (this.loggingType.equalsIgnoreCase("file")) {
            myAppLogger = new FileLogger(category);
        } else if (this.loggingType.equalsIgnoreCase("database")) {
            HashMap<String, String> dbParams = new HashMap<>();
            dbParams.put("userName", environment.getUserName());
            dbParams.put("password",environment.getPassword());
            dbParams.put("dbms",environment.getDbms());
            dbParams.put("portNumber",environment.getPortNumber());
            dbParams.put("serverName",environment.getServerName());

            myAppLogger = new DatabaseLogger(category, dbParams);
        }else {
            myAppLogger = new ConsoleLogger(category);
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
