package enterprises.iwakura.cirno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.experimental.UtilityClass;

/**
 * Various utilities when working with {@link List}
 */
@UtilityClass
public class ListUtils {

    /**
     * Returns an empty collection if the supplied collection is null.
     *
     * @param collection Collection
     * @param <T>        Type
     *
     * @return Supplied collection if non-null, otherwise empty {@link ArrayList}
     */
    public static <T> Collection<T> emptyIfNull(Collection<T> collection) {
        return collection == null ? new ArrayList<>() : collection;
    }

    /**
     * Returns a default collection if the supplied collection is null.
     *
     * @param collection  Collection
     * @param defaultList Default collection
     * @param <T>         Type
     *
     * @return Supplied collection if non-null, otherwise the default collection
     */
    public static <T> Collection<T> defaultIfNull(Collection<T> collection, Collection<T> defaultList) {
        return collection == null ? defaultList : collection;
    }

    /**
     * Returns an empty collection if the supplied array is null, otherwise wraps in a list.
     *
     * @param array Array
     * @param <T>   Type
     *
     * @return Array wrapped in a list, otherwise empty {@link ArrayList}
     */
    public static <T> Collection<T> emptyIfNull(T[] array) {
        return array == null ? new ArrayList<>() : Arrays.asList(array);
    }

}
