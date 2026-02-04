package me.a8kj.logging.impl;


import me.a8kj.logging.Log;
import me.a8kj.logging.LogKind;
import me.a8kj.logging.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A composite implementation of the {@link Logger} interface.
 * This class acts as a container for multiple loggers, distributing a single log
 * message to all registered destinations. It is the core of the multi-channel
 * logging system.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public class CompositeLogger implements Logger {

    /**
     * The collection of registered loggers that will receive broadcasted messages.
     */
    private final List<Logger> loggers = Collections.synchronizedList(new ArrayList<>());
    /**
     * Adds a new logger destination to the broadcast list.
     *
     * @param logger The {@link Logger} implementation to register.
     */
    public void addLogger(Logger logger) {
        loggers.add(logger);
    }

    /**
     * Broadcasts a log message to every registered logger in the composite list.
     * Iterates through all internal loggers and invokes their respective
     * {@code log} methods.
     *
     * @param message The message content to be logged.
     * @param level   The {@link LogKind} severity level.
     */
    @Override
    public void log(String message, LogKind kind) {
        if(kind == LogKind.DEBUG && !Log.isDebugEnabled()) return;
        for (Logger logger : loggers) {
            logger.log(message, kind);
        }
    }

}