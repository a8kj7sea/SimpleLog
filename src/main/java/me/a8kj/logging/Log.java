package me.a8kj.logging;


import lombok.Getter;
import me.a8kj.logging.impl.CompositeLogger;

import java.util.Objects;

/**
 * The main entry point for the logging system.
 * This class provides a static facade to simplify logging operations across the application.
 * It utilizes a {@link CompositeLogger} to dispatch messages to multiple registered destinations.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */

public class Log {
    private static final CompositeLogger composite = new CompositeLogger();
    @Getter
    private static boolean debugEnabled = false;

    /**
     * Toggles the visibility of debug-level log messages.
     *
     * @param enabled True to enable debug logging, false to ignore it.
     */
    @SuppressWarnings("unused")
    public static void setDebugEnabled(boolean enabled) {
        debugEnabled = enabled;
    }

    /**
     * Registers a new logging destination (e.g., Console, File, or Discord).
     *
     * @param logger The {@link Logger} implementation to add.
     * @throws NullPointerException if the provided logger is null.
     */
    public static void addDestination(Logger logger) {
        composite.addLogger(Objects.requireNonNull(logger));
    }

    /**
     * Creates a new fluent builder instance for complex log requirements.
     *
     * @return A new {@link LogBuilder} instance.
     */
    public static LogBuilder create() {
        return new LogBuilder(composite);
    }

    /**
     * Logs a debug message if debugging is enabled.
     *
     * @param msg  The message template.
     * @param args Formatting arguments.
     */
    public static void debug(String msg, Object... args) {
        if (debugEnabled) {
            create().kind(LogKind.DEBUG).message(msg, args).send();
        }
    }

    /**
     * Logs an informational message.
     *
     * @param msg  The message template.
     * @param args Formatting arguments.
     */
    public static void info(String msg, Object... args) {
        create().kind(LogKind.INFO).message(msg, args).send();
    }

    /**
     * Logs a warning message.
     *
     * @param msg  The message template.
     * @param args Formatting arguments.
     */
    public static void warn(String msg, Object... args) {
        create().kind(LogKind.WARN).message(msg, args).send();
    }

    /**
     * Logs an error message.
     *
     * @param msg  The message template.
     * @param args Formatting arguments.
     */
    public static void error(String msg, Object... args) {
        create().kind(LogKind.ERROR).message(msg, args).send();
    }

    /**
     * Logs an informational message with a specific context.
     *
     * @param ctx  The {@link LogContext} source.
     * @param msg  The message template.
     * @param args Formatting arguments.
     */
    public static void info(LogContext ctx, String msg, Object... args) {
        create().context(ctx).kind(LogKind.INFO).message(msg, args).send();
    }

    /**
     * Logs a formatted chat message.
     *
     * @param ctx  The {@link LogContext} source.
     * @param user The username of the sender.
     * @param msg  The chat content.
     */
    public static void chat(LogContext ctx, String user, String msg) {
        create().kind(LogKind.CHAT).context(ctx).message("[%s]: %s", user, msg).send();
    }

    /**
     * Logs an exception with its stack trace details.
     *
     * @param t The throwable to log.
     */
    public static void exception(Throwable t) {
        create().kind(LogKind.EXCEPTION).exception(t).send();
    }

    /**
     * Logs an exception accompanied by a custom descriptive message.
     *
     * @param message Descriptive text about the error.
     * @param t       The throwable to log.
     */
    public static void exception(String message, Throwable t) {
        create().kind(LogKind.EXCEPTION).exception(t).message(message).send();
    }

    /**
     * Logs an exception with a specific context and descriptive message.
     *
     * @param ctx     The {@link LogContext} source.
     * @param message Descriptive text about the error.
     * @param t       The throwable to log.
     */
    public static void exception(LogContext ctx, String message, Throwable t) {
        create().context(ctx).kind(LogKind.EXCEPTION).exception(t).message(message).send();
    }

    /**
     * Logs a custom message. This is useful for user-defined events or
     * plugin-specific logs that don't fit standard severity levels.
     *
     * @param msg  The message template.
     * @param args Formatting arguments.
     */
    public static void custom(String msg, Object... args) {
        create().kind(LogKind.CUSTOM).message(msg, args).send();
    }

    /**
     * Logs a custom message with a specific context.
     *
     * @param ctx  The {@link LogContext} source identifying the origin of the log.
     * @param msg  The message template.
     * @param args Formatting arguments.
     */
    public static void custom(LogContext ctx, String msg, Object... args) {
        create().context(ctx).kind(LogKind.CUSTOM).message(msg, args).send();
    }

    /**
     * Logs a fatal error and immediately terminates the application.
     * Use this only for unrecoverable system failures.
     *
     * @param ctx  The {@link LogContext} source.
     * @param msg  The message template.
     * @param args Formatting arguments.
     */
    public static void fatal(LogContext ctx, String msg, Object... args) {
        create().context(ctx).kind(LogKind.FATAL).message(msg, args).send();
        System.exit(1);
    }
}