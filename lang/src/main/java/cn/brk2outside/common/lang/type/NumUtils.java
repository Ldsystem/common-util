package cn.brk2outside.common.lang.type;

import cn.brk2outside.common.lang.validation.StrUtil;

import java.math.BigDecimal;
import java.util.Optional;

public class NumUtils {

    public static Optional<Integer> toInt(Object obj) {
        return obj instanceof Integer?
                Optional.of((Integer) obj) : toNumber(obj).map(Number::intValue);
    }

    public static Optional<Long> toLong(Object obj) {
        return obj instanceof Long?
                Optional.of((Long) obj) : toNumber(obj).map(Number::longValue);
    }

    public static Optional<Double> toDouble(Object obj) {
        return obj instanceof Double ?
                Optional.of((Double) obj) : toNumber(obj).map(Number::doubleValue);
    }

    public static Optional<Number> toNumber(Object obj) {
        return obj instanceof Number ? Optional.of((Number) obj)
                : Optional.ofNullable(obj)
                .map(Object::toString)
                .map(str -> {
                    try {
                        return new BigDecimal(str);
                    } catch (Exception ignore) {
                    }
                    return null;
                });
    }

    public static Optional<Long> parseHexString(String hex) {
        return Optional.ofNullable(hex)
                .filter(StrUtil::hasWord)
                // 20240516 bug fix, prepend '^' to regexp.
                .map(str -> str.replaceFirst("^0?[Xx]?0*", ""))
                .map(x -> Long.parseLong(x, 16));
    }

}
