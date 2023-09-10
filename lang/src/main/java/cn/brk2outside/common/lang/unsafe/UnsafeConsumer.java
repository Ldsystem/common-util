package cn.brk2outside.common.lang.unsafe;

/**
 * <p> </p>
 *
 * @author liushenglong_8597@outlook.com
 * @since 2023/9/10
 */
@FunctionalInterface
public interface UnsafeConsumer<T> {

    void accept(T t) throws Exception;

}
