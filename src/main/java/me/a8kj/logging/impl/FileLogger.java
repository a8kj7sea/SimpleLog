package me.a8kj.logging.impl;

import me.a8kj.logging.LogKind;
import me.a8kj.logging.Logger;
import me.a8kj.logging.util.AnsiUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * A logger implementation that persists log entries to a local file.
 * <p>
 * This implementation uses a {@link BufferedWriter} opened once at initialization
 * for improved performance. Each log message is flushed immediately to ensure
 * that entries are saved in real-time. ANSI color codes are stripped to maintain
 * readability across different text editors.
 * </p>
 * <p>
 * Implements {@link AutoCloseable} to allow proper resource management.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * try (FileLogger logger = new FileLogger("logs.txt")) {
 *     Log.addDestination(logger);
 *     Log.info("Application started");
 * }
 * </pre>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public class FileLogger implements Logger, AutoCloseable {

    private final String fileName;
    private final BufferedWriter writer;

    /**
     * Initializes a new FileLogger writing to the specified file.
     * If the file does not exist, it will be created.
     *
     * @param fileName The path to the log file.
     * @throws IOException If the file cannot be opened for writing.
     */
    public FileLogger(String fileName) throws IOException {
        this.fileName = fileName;
        this.writer = new BufferedWriter(new FileWriter(fileName, true));
    }

    /**
     * Writes a log message to the file, stripping any ANSI color codes.
     * The message format includes a timestamp, the log level, and the message content.
     *
     * @param message The message to log (may contain ANSI codes).
     * @param kind    The severity level of the log entry.
     */
    @Override
    public void log(String message, LogKind kind) {
        try {
            String cleanMessage = AnsiUtils.stripColors(message);
            writer.write(String.format("[%s] [%s] %s%n", LocalDateTime.now(), kind, cleanMessage));
            writer.flush();
        } catch (IOException ignored) {
        }
    }

    /**
     * Closes the underlying file writer. Should be called when logging is complete
     * to release system resources.
     *
     * @throws Exception If an I/O error occurs while closing the writer.
     */
    @Override
    public void close() throws Exception {
        writer.close();
    }
}
