package example;

import me.a8kj.logging.Log;
import me.a8kj.logging.LogContext;
import me.a8kj.logging.LogKind;
import me.a8kj.logging.impl.ConsoleLogger;
import me.a8kj.logging.impl.FileLogger;
import java.io.IOException;

/**
 * Demonstrates the functionality of the logging system including
 * multiple log destinations, context-based logging, exception handling,
 * and the fluent builder API.
 * <p>
 * This class is a manual test suite intended to showcase and verify
 * the logging system behavior in a generic application environment.
 * </p>
 *
 * <ul>
 *     <li>Console logging</li>
 *     <li>File logging</li>
 *     <li>Debug and info messages</li>
 *     <li>Chat logging</li>
 *     <li>Exception logging with full stack trace</li>
 *     <li>Fatal logging with system exit</li>
 * </ul>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public class Testing {

    /**
     * Entry point of the test suite. Initializes log destinations,
     * enables debug messages, and executes the example logging routines.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        Log.addDestination(new ConsoleLogger());

        try {
            FileLogger fileLogger = new FileLogger("logs.txt");
            Log.addDestination(fileLogger);

            Log.setDebugEnabled(true);

            runExamples();

            fileLogger.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a series of example logging operations demonstrating:
     * <ul>
     *     <li>Informational, debug, and warning messages</li>
     *     <li>Context-specific logging</li>
     *     <li>Chat message logging</li>
     *     <li>Exception logging with full stack trace</li>
     *     <li>Using the fluent LogBuilder API</li>
     *     <li>Conditional fatal logging</li>
     * </ul>
     */
    private static void runExamples() {
        Log.info("Application starting...");

        Log.debug(
                "OS: %s | Java Version: %s",
                System.getProperty("os.name"),
                System.getProperty("java.version")
        );

        Log.warn("Configuration file not found, using default values.");

        LogContext authModule = new LogContext("AuthModule");
        LogContext networkModule = new LogContext("NetworkModule");

        Log.info(authModule, "Initializing authentication services...");
        Log.info(networkModule, "Establishing network connections...");

        Log.chat(authModule, "User123", "Login request received");
        Log.chat(networkModule, "System", "Connection established successfully");

        try {
            String value = null;
            value.length();
        } catch (Exception e) {
            Log.exception(e);
            Log.exception(authModule, "Failed to validate user session", e);
        }

        Log.create()
                .context(new LogContext("Performance"))
                .kind(LogKind.WARN)
                .message("High memory usage detected: %d MB", 2048)
                .send();

        Log.debug("Feature flag enabled: %s", true);

        if (System.getProperty("os.name").startsWith("Windows")) {
            Log.info("Running on Windows environment.");
        } else {
            Log.fatal(LogContext.SYSTEM, "Unsupported operating system detected!");
        }
    }
}
