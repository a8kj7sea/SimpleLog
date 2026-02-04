package me.a8kj.logging.impl;

import com.diogonunes.jcolor.Attribute;
import me.a8kj.logging.LogKind;
import me.a8kj.logging.Logger;
import me.a8kj.logging.util.AnsiUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * An implementation of {@link Logger} that outputs log messages
 * to the standard console (System.out) with ANSI color coding.
 * <p>
 * Each {@link LogKind} is assigned a specific color for better
 * readability. Timestamps are formatted in "HH:mm:ss".
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * Log.addDestination(new ConsoleLogger());
 * Log.info("Application started");
 * </pre>
 *
 * <p>Supported log levels and colors:</p>
 * <ul>
 *     <li>{@link LogKind#INFO} - Cyan</li>
 *     <li>{@link LogKind#WARN} - Yellow</li>
 *     <li>{@link LogKind#CUSTOM} - Bright Blue</li>
 *     <li>{@link LogKind#DEBUG} - Bright White</li>
 *     <li>{@link LogKind#ERROR}, {@link LogKind#EXCEPTION} - Red</li>
 *     <li>{@link LogKind#FATAL} - Red background with bold black text</li>
 *     <li>{@link LogKind#CHAT} - Green</li>
 * </ul>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public class ConsoleLogger implements Logger {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Logs a message to the console with color coding based on {@link LogKind}.
     * The message is prefixed with a timestamp and the log level.
     *
     * @param message The message content to be logged.
     * @param kind    The severity level or category of the log message.
     */
    @Override
    public void log(String message, LogKind kind) {
        String time = LocalTime.now().format(formatter);

        String coloredLevel = switch (kind) {
            case INFO -> AnsiUtils.cyan("INFO");
            case WARN -> AnsiUtils.yellow("WARN");
            case CUSTOM -> AnsiUtils.color(" CUSTOM ", Attribute.BRIGHT_BLUE_TEXT());
            case DEBUG -> AnsiUtils.color("DEBUG", Attribute.BRIGHT_WHITE_TEXT());
            case ERROR, EXCEPTION -> AnsiUtils.red("ERROR");
            case FATAL -> AnsiUtils.errorStyle(" FATAL ");
            case CHAT -> AnsiUtils.green("CHAT");
        };

        System.out.println("[" + time + "] " + coloredLevel + " | " + message);
    }
}
