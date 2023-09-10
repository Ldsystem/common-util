package cn.brk2outside.common.lang.unsafe;

/**
 * <p> </p>
 *
 * @author liushenglong_8597@outlook.com
 * @since 2023/9/10
 */
public interface UnsafeSupplier <R> {

    R get() throws Exception;

}
