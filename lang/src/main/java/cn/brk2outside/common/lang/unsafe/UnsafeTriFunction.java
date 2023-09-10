package cn.brk2outside.common.lang.unsafe;

/**
 * <p> </p>
 *
 * @author liushenglong_8597@outlook.com
 * @since 2023/9/10
 */
@FunctionalInterface
public interface UnsafeTriFunction<T, S, U, R> {
        R apply(T t, S s, U u) throws Exception;
}
