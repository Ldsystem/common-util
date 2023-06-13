package cn.brk2outside.common.lang;

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
        return str != null && str.matches(".*\\w.*");
    }

}
