package me.a8kj.logging;

/**
 * Interface representing a generic logging system for the MCBots application.
 * Implementations of this interface handle the output and formatting of
 * log messages based on their severity or category.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public interface Logger {

    /**
     * Records a message with a specific logging level.
     * * @param message The string content to be logged.
     *
     * @param level The {@link LogKind} representing the severity or type of the log.
     */
    void log(String message, LogKind level);

}