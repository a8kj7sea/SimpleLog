package me.a8kj.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A fluent builder class for constructing and dispatching log messages.
 * This class follows the Builder Pattern to allow flexible configuration
 * of log context, severity, and exception data before sending it to a {@link Logger}.
 * <p>
 * It supports:
 * <ul>
 *     <li>Context-specific logging</li>
 *     <li>Severity levels via {@link LogKind}</li>
 *     <li>Formatted messages using {@link String#format}</li>
 *     <li>Exception logging with full stack trace</li>
 * </ul>
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public class LogBuilder {

    private final Logger logger;
    private LogContext context = LogContext.SYSTEM;
    private LogKind kind = LogKind.INFO;
    private String message = "";
    private Throwable throwable;

    /**
     * Initializes a new LogBuilder with a target logger.
     *
     * @param logger The {@link Logger} implementation where the final log will be sent.
     */
    public LogBuilder(Logger logger) {
        this.logger = logger;
    }

    /**
     * Sets the context for this log entry. Defaults to {@link LogContext#SYSTEM}.
     *
     * @param context The {@link LogContext} representing the log source.
     * @return This builder instance for method chaining.
     */
    public LogBuilder context(LogContext context) {
        this.context = context;
        return this;
    }

    /**
     * Sets the severity level of the log.
     *
     * @param kind The {@link LogKind} category.
     * @return This builder instance for method chaining.
     */
    public LogBuilder kind(LogKind kind) {
        this.kind = kind;
        return this;
    }

    /**
     * Sets the log message. Supports standard {@link String#format} placeholders.
     *
     * @param message The message template string.
     * @param args    Arguments referenced by the format specifiers in the message string.
     * @return This builder instance for method chaining.
     */
    public LogBuilder message(String message, Object... args) {
        if (args != null && args.length > 0) {
            this.message = String.format(message, args);
        } else {
            this.message = message;
        }
        return this;
    }

    /**
     * Attaches an exception to the log. If the current level is INFO,
     * it automatically upgrades the level to {@link LogKind#EXCEPTION}.
     *
     * @param t The {@link Throwable} to include in the log.
     * @return This builder instance for method chaining.
     */
    public LogBuilder exception(Throwable t) {
        this.throwable = t;
        if (this.kind == LogKind.INFO) {
            this.kind = LogKind.EXCEPTION;
        }
        return this;
    }

    /**
     * Formats the final log string including context and throwable details,
     * then dispatches it to the underlying logger.
     * <p>
     * If a throwable is attached, its full stack trace is appended to the log message.
     * </p>
     */
    public void send() {
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString()).append(" ").append(message);

        if (throwable != null) {
            sb.append(" | ").append(throwable.getClass().getSimpleName())
                    .append(": ").append(throwable.getMessage());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            sb.append("\n").append(sw.toString());
        }

        logger.log(sb.toString(), kind);
    }
}
