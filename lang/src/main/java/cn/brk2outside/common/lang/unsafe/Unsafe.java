package cn.brk2outside.common.lang.unsafe;

/**
 * <p> </p>
 * @author liushenglong_8597@outlook.com
 * @since 2023/9/10
 */
@SuppressWarnings("unused")
public class Unsafe {

    /**
     * execute a consumer and throw a RuntimeException when exception occurs
     * @param consumer  consumer
     * @param t     parameter
     * @param <T>   parameter type
     */
    public static <T> void sneakyThrows(UnsafeConsumer<T> consumer, T t) {
        try {
            consumer.accept(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * execute a consumer and throw a RuntimeException when exception occurs
     * @param consumer consumer
     * @param t         parameter
     * @param u         parameter
     * @param <T>       parameter type
     * @param <U>       parameter type
     */
    public static <T, U> void sneakyThrows(UnsafeBiConsumer<T, U> consumer, T t, U u) {
        try {
            consumer.accept(t, u);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * execute a supplier and throw a RuntimeException when exception occurs
     * @param supplier  supplier
     * @return          result
     * @param <R>       result type
     */
    public static <R> R sneakyThrows(UnsafeSupplier<R> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * execute a function and throw a RuntimeException when exception occurs
     * @param function      function
     * @param t             parameter
     * @return              result
     * @param <T>           parameter type
     * @param <R>           result type
     */
    public static <T, R> R sneakyThrows(UnsafeFunction<T, R> function, T t) {
        try {
            return function.apply(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * execute a function and throw a RuntimeException when exception occurs
     * @param function      function
     * @param t             parameter
     * @param s             parameter
     * @return              result
     * @param <T>           parameter type
     * @param <S>           parameter type
     * @param <R>           result type
     */
    public static <T, S, R> R sneakyThrows(UnsafeBiFunction<T, S, R> function, T t, S s) {
        try {
            return function.apply(t, s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * execute a function and throw a RuntimeException when exception occurs
     * @param function  function
     * @param t         parameter
     * @param s         parameter
     * @param u         parameter
     * @return          result
     * @param <T>       parameter type
     * @param <S>       parameter type
     * @param <U>       parameter type
     * @param <R>       result type
     */
    public static <T, S, U, R> R sneakyThrows(UnsafeTriFunction<T, S, U, R> function, T t, S s, U u) {
        try {
            return function.apply(t, s, u);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
