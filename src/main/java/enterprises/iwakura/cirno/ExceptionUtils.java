package enterprises.iwakura.cirno;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

/**
 * Various utilities when working with exceptions
 */
@UtilityClass
public class ExceptionUtils {

    /**
     * Dumps exception into a string
     *
     * @param prefix    Prefix to prepend to every line
     * @param exception Exception
     *
     * @return Exception stacktrace (returns null if exception is null)
     */
    public static String dumpExceptionStacktrace(String prefix, Throwable exception) {
        if (exception == null) {
            return null;
        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);

        String stacktrace = stringWriter.toString();

        if (prefix == null || prefix.isEmpty()) {
            return stacktrace;
        }

        return Arrays.stream(StringUtils.lines("\\r?\\n"))
            .map(line -> prefix + line)
            .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Dumps exception into a string
     *
     * @param exception Exception
     *
     * @return Exception stacktrace
     */
    public static String dumpExceptionStacktrace(Throwable exception) {
        return dumpExceptionStacktrace(null, exception);
    }
}
