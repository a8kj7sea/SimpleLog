package me.a8kj.logging.util;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

/**
 * Utility class for applying ANSI color codes and styles to terminal text.
 * It provides shorthand methods for common colors and specific logging styles,
 * utilizing the JColor library for cross-platform compatibility.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 */
public class AnsiUtils {

    /**
     * Applies a set of ANSI attributes to the provided text.
     *
     * @param text       The raw string to be colored.
     * @param attributes The JColor {@link Attribute}s to apply (e.g., color, bold, italic).
     * @return A formatted string containing ANSI escape sequences.
     */
    public static String color(String text, Attribute... attributes) {
        return Ansi.colorize(text, attributes);
    }

    /**
     * Colors the text red. Commonly used for errors or negative indicators.
     *
     * @param text The text to color.
     * @return Red-colored string.
     */
    public static String red(String text) {
        return color(text, Attribute.RED_TEXT());
    }

    /**
     * Colors the text green. Commonly used for success messages.
     *
     * @param text The text to color.
     * @return Green-colored string.
     */
    public static String green(String text) {
        return color(text, Attribute.GREEN_TEXT());
    }

    /**
     * Colors the text yellow. Commonly used for warnings.
     *
     * @param text The text to color.
     * @return Yellow-colored string.
     */
    public static String yellow(String text) {
        return color(text, Attribute.YELLOW_TEXT());
    }

    /**
     * Colors the text cyan. Commonly used for info or variable highlights.
     *
     * @param text The text to color.
     * @return Cyan-colored string.
     */
    public static String cyan(String text) {
        return color(text, Attribute.CYAN_TEXT());
    }

    /**
     * Applies a high-visibility error style: Red background with Bold Black text.
     * Ideal for fatal errors or critical failures.
     *
     * @param text The text to style.
     * @return Styled string for critical errors.
     */
    public static String errorStyle(String text) {
        return color(text, Attribute.RED_BACK(), Attribute.BLACK_TEXT(), Attribute.BOLD());
    }

    /**
     * Removes all ANSI escape sequences from a string.
     * Use this before logging to plain text files or systems that do not support ANSI.
     *
     * @param text The string containing ANSI codes.
     * @return A plain text string with all styling removed.
     */
    public static String stripColors(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}