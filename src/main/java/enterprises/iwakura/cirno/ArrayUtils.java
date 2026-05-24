package enterprises.iwakura.cirno;

import lombok.experimental.UtilityClass;

/**
 * Various utilities when working with arrays
 */
@UtilityClass
public class ArrayUtils {

    /**
     * Returns an empty array if the supplied array is null.
     *
     * @param array Array
     * @param <T>   Type
     *
     * @return Supplied array if non-null, otherwise empty array of {@link Object}
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] emptyIfNull(T[] array) {
        return array == null ? (T[]) new Object[0] : array;
    }

    /**
     * Returns a default array if the supplied array is null.
     *
     * @param array        Array
     * @param defaultArray Default array
     * @param <T>          Type
     *
     * @return Supplied array if non-null, otherwise the default array
     */
    public static <T> T[] defaultIfNull(T[] array, T[] defaultArray) {
        return array == null ? defaultArray : array;
    }

    /**
     * Checks if the array is null or empty
     *
     * @param array Array
     * @param <T>   Type
     *
     * @return True if null or empty
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Returns the last element of an array if the supplied array is not null or empty.
     *
     * @param array Array
     * @param <T>   Type
     *
     * @return Last element of an array if not null or empty, otherwise null
     */
    public static <T> T getLastSafe(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        return array[array.length - 1];
    }
}
