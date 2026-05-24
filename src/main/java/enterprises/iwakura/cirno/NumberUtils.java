package enterprises.iwakura.cirno;

import java.math.BigDecimal;
import java.text.NumberFormat;

import lombok.experimental.UtilityClass;

/**
 * Various utilities when working with numbers
 */
@UtilityClass
public class NumberUtils {

    /**
     * Checks if supplied string is parsable into {@link Number}
     *
     * @param string String that you want to test
     *
     * @return Returns true if is parsable, false otherwise.
     */
    public static boolean isNumber(String string) {
        return parseNumber(string) != null;
    }

    /**
     * Tries to parse the supplied string as a number.
     *
     * @param string String that you want to parse
     *
     * @return Returns {@link Number} if parsable, null otherwise.
     */
    public static Number parseNumber(String string) {
        try {
            return NumberFormat.getNumberInstance().parse(string);
        } catch (Throwable ignored) {
            return null;
        }
    }

    /**
     * Checks if supplied string is parsable into {@link Integer}
     *
     * @param string String that you want to test
     *
     * @return Returns true if is parsable, false otherwise.
     */
    public static boolean isInteger(String string) {
        return parseInteger(string) != null;
    }

    /**
     * Tries to parse supplied string as {@link Integer}
     *
     * @param string String that you want to convert
     *
     * @return Returns {@link Integer} if parsable, null otherwise.
     */
    public static Integer parseInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Throwable ignored) {
            return null;
        }
    }

    /**
     * Checks if supplied string is parsable into {@link Long}
     *
     * @param string String that you want to test
     *
     * @return Returns true if is parsable, false otherwise.
     */
    public static boolean isLong(String string) {
        return parseLong(string) != null;
    }

    /**
     * Tries to parse supplied string as {@link Long}
     *
     * @param string String that you want to convert
     *
     * @return Returns {@link Long} if parsable, null otherwise.
     */
    public static Long parseLong(String string) {
        try {
            return Long.parseLong(string);
        } catch (Throwable ignored) {
            return null;
        }
    }

    /**
     * Checks if supplied string is parsable into {@link Float}
     *
     * @param string String that you want to test
     *
     * @return Returns true if is parsable, false otherwise.
     */
    public static boolean isFloat(String string) {
        return parseFloat(string) != null;
    }

    /**
     * Tries to parse supplied string as {@link Float}
     *
     * @param string String that you want to convert
     *
     * @return Returns {@link Float} if parsable, null otherwise.
     */
    public static Float parseFloat(String string) {
        try {
            return Float.parseFloat(string);
        } catch (Throwable ignored) {
            return null;
        }
    }

    /**
     * Checks if supplied string is parsable into {@link Double}
     *
     * @param string String that you want to test
     *
     * @return Returns true if is parsable, false otherwise.
     */
    public static boolean isDouble(String string) {
        return parseDouble(string) != null;
    }

    /**
     * Tries to parse supplied string as {@link Double}
     *
     * @param string String that you want to convert
     *
     * @return Returns {@link Double} if parsable, null otherwise.
     */
    public static Double parseDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Throwable ignored) {
            return null;
        }
    }

    /**
     * Checks if supplied string is parsable into {@link Short}
     *
     * @param string String that you want to test
     *
     * @return Returns true if is parsable, false otherwise.
     */
    public static boolean isShort(String string) {
        return parseShort(string) != null;
    }

    /**
     * Tries to parse supplied string as {@link Short}
     *
     * @param string String that you want to convert
     *
     * @return Returns {@link Short} if parsable, null otherwise.
     */
    public static Short parseShort(String string) {
        try {
            return Short.parseShort(string);
        } catch (Throwable ignored) {
            return null;
        }
    }

    /**
     * Checks if supplied string is parsable into {@link Byte}
     *
     * @param string String that you want to test
     *
     * @return Returns true if is parsable, false otherwise.
     */
    public static boolean isByte(String string) {
        return parseByte(string) != null;
    }

    /**
     * Tries to parse supplied string as {@link Byte}
     *
     * @param string String that you want to convert
     *
     * @return Returns {@link Byte} if parsable, null otherwise.
     */
    public static Byte parseByte(String string) {
        try {
            return Byte.parseByte(string);
        } catch (Throwable ignored) {
            return null;
        }
    }

    /**
     * Tries to parse supplied string as {@link BigDecimal}
     *
     * @param string String that you want to convert
     *
     * @return Returns {@link BigDecimal} if parsable, null otherwise
     */
    public static BigDecimal parseBigDecimal(String string) {
        try {
            return new BigDecimal(string);
        } catch (Throwable ignored) {
            return null;
        }
    }
}
