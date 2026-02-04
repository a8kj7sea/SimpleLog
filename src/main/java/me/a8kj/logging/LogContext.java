package me.a8kj.logging;

import lombok.NonNull;

/**
 * Provides contextual information for a log entry, such as a source name or thread identifier.
 * This record is designed to facilitate different logging strategies (e.g., Discord webhooks,
 * database logging) by allowing them to categorize or format messages based on the source.
 * * @param name The name of the context (e.g., "System", "Thread-1", or a specific Bot name).
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public record LogContext(String name) {

    /**
     * A default global context used for general system-level logs.
     */
    public static final LogContext SYSTEM = new LogContext("System");

    /**
     * Returns a formatted string representation of the context.
     *
     * @return The context name enclosed in square brackets.
     */
    @Override
    public @NonNull String toString() {
        return "[" + name + "]";
    }
}