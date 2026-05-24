package enterprises.iwakura.cirno;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import lombok.experimental.UtilityClass;

/**
 * Various utilities when working with completable futures
 */
@UtilityClass
public class CompletableFutureUtils {

    /**
     * Handles exceptions thrown in the consumer block. Useful to handle exception within
     * {@link CompletableFuture#whenCompleteAsync(BiConsumer)}
     *
     * @param consumer          Consumer
     * @param exceptionConsumer Exception consumer that is invoked when an exception happens inside the consumer block
     * @param <T>               Type
     *
     * @return Exception-safe consumer
     */
    public static <T> BiConsumer<T, Throwable> $safe(
        BiConsumer<T, Throwable> consumer,
        Consumer<Throwable> exceptionConsumer
    ) {
        return (result, ex) -> {
            try {
                consumer.accept(result, ex);
            } catch (Throwable throwable) {
                exceptionConsumer.accept(throwable);
            }
        };
    }

    /**
     * Handles exceptions thrown in the consumer block. Useful to handle exception within
     * {@link CompletableFuture#whenCompleteAsync(BiConsumer)}. Exceptions are printed
     * to {@link System#err}
     *
     * @param consumer Consumer
     * @param <T>      Type
     *
     * @return Exception-safe consumer
     */
    public static <T> BiConsumer<T, Throwable> $safe(
        BiConsumer<T, Throwable> consumer
    ) {
        return (result, ex) -> {
            try {
                consumer.accept(result, ex);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        };
    }
}
