package cn.brk2outside.common.lang.validation;

/**
 * @author liushenglong_8597@outlook.com
 * @Date 2023/6/13
 * @Description
 */
public class StrUtil {

    /**
     * whether given string has word chars
     */
    public static boolean hasWord(String str) {
        // 20240516, replace regexp search with java17 string api.
        return str != null && !str.isBlank();
    }

}
