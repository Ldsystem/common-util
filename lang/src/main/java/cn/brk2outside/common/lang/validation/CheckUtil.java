package cn.brk2outside.common.lang.validation;

import java.util.Collection;
import java.util.Map;

/**
 * <p> </p>
 *
 * @author liushenglong_8597@outlook.com
 * @since 2024/5/16
 */
public class CheckUtil {

    public static boolean isEmpty(Object object) {
        return null == object ||
                (object instanceof String str && str.isEmpty()) ||
                (object instanceof Collection<?> collection && collection.isEmpty()) ||
                (object instanceof Map<?, ?> map && map.isEmpty()) ||
                (object instanceof Object[] arr && arr.length == 0);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

}
