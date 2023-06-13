package cn.brk2outside.common.lang.type;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BoolUtils {

    /**
     * represents which literal letters should be treated as a bool false
     */
    private static final Set<String> FALSE_VALUE_SYMBOLS = new HashSet<>(Arrays.asList(
            "NULL", "FALSE", "F", "NO", "N", "NIL", "否", "0"
    ));

    private static final Set<String> NULL_VALUE_SYMBOLS = new HashSet<>(Arrays.asList(
            "", "NULL", "NIL", "UNDEFINED"
    ));

    /**
     * return the value as it is if the input object is a bool, or else check if it's a
     * {@link BoolUtils#FALSE_VALUE_SYMBOLS}. If {@code obj} is null or one of the
     * {@link BoolUtils#NULL_VALUE_SYMBOLS}, {@code Optional.empty()} will be return
     */
    public static Optional<Boolean> toBoolean(Object obj) {
        return obj instanceof Boolean? Optional.of((Boolean) obj)
                : Optional.ofNullable(obj)
                .map(Object::toString)
                .filter(StringUtils::hasText)
                .map(String::toUpperCase)
                .filter(str -> !NULL_VALUE_SYMBOLS.contains(str))
                .map(FALSE_VALUE_SYMBOLS::contains)
                .map(b -> !b);
    }

    public static boolean absentAsFalse(Object obj) {
        return toBoolean(obj).orElse(Boolean.FALSE);
    }

    public static boolean absentAsTrue(Object obj) {
        return toBoolean(obj).orElse(Boolean.TRUE);
    }

    /**
     * return the input obj itself when it's a bool, else
     * try to parse it to bool strictly according to its literal value
     */
    public static Optional<Boolean> toBooleanStrict(Object obj) {
        return obj instanceof Boolean ? Optional.of((Boolean) obj)
                : Optional.ofNullable(obj)
                .map(Object::toString)
                .map(Boolean::parseBoolean);
    }

}
