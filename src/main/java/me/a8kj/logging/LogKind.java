package me.a8kj.logging;

/**
 * Defines the different types of log messages used within the application.
 * Each constant represents a specific level or category of logging.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public enum LogKind {

    /**
     * General informational messages that highlight the progress of the application.
     */
    INFO,

    /**
     * Error events that might still allow the application to continue running.
     */
    ERROR,

    /**
     * Fine-grained informational events that are most useful to debug the application.
     */
    DEBUG,

    /**
     * Logs specifically capturing stack traces and details from caught exceptions.
     */
    EXCEPTION,

    /**
     * Potentially harmful situations that should be noted but do not stop execution.
     */
    WARN,

    /**
     * Specifically used for logging in-game chat messages or communications.
     */
    CHAT,

    /**
     * Used for user-defined or plugin-specific log categories or monitoring events that do not
     * fall under standard severity levels.
     */
    CUSTOM,

    /**
     * Severe error events that will presumably lead the application to abort.
     */
    FATAL

}