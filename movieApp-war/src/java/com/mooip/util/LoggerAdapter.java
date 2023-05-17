package com.mooip.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A one-way adapter of the java logging utility to look like log4j.
 * 
 * @author masterofoneinchpunch
 */
public final class LoggerAdapter {    
    private final Logger logger;

    /**
     * A constructor for a LLoggerAdapter.
     * 
     * @param loggerName The name of the logger.
     */
    public LoggerAdapter(String loggerName) {
        this.logger = Logger.getLogger(loggerName);
    }
    
    /**
     * A factory static method to get a LoggerAdapter.
     * 
     * @param loggerName The logger name which will be the string name.
     * @return LoggerAdapter A Logger Adapter.
     */
    public static LoggerAdapter getLogger(String loggerName) {
        return new LoggerAdapter(loggerName);
    }

    /**
     * A factory static method to get a LoggerAdapter.
     * 
     * @param loggerName The logger name which will be the class name.
     * @return LoggerAdapter A Logger Adapter.
     */
    public static LoggerAdapter getLogger(Class loggerName) {
        return new LoggerAdapter(loggerName.getName());
    }
    
    /**
     * returns the logger.
     * 
     * @return logger A logger.
     */
    public Logger getLogger() {
        return logger;
    }
    
    /**
     * A warning is a mid-level issue.
     * 
     * @param message The message.
     */
    public void warn(String message) {
        logger.log(Level.WARNING, message);
    }
    
    /**
     * An error is a severe issue.
     * 
     * @param message The message.
     */
    public void error(String message) {
        logger.log(Level.SEVERE, message);
    }
    
    /**
     * An error is a severe issue.
     * 
     * @param exception The exception.
     */
    public void error(Exception exception) {
        logger.log(Level.SEVERE, exception.getMessage(), exception);
    }
    
    /**
     * An error is a severe issue.
     * 
     * @param message The message.
     * @param exception The exception.
     */
    public void error(String message, Exception exception) {
        logger.log(Level.SEVERE, message, exception);
    }
}    