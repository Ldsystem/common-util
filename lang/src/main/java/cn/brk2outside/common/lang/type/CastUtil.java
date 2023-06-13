package cn.brk2outside.common.lang.type;

import java.util.Date;
import java.util.Optional;

/**
 * @author liushenglong_8597@outlook.com
 * Type Utils that's used to handle type-convert things
 */
@SuppressWarnings("unused")
public class CastUtil {

    /**
     * cast the input obj to String
     * @param obj Object
     * @return Optional<String>
     */
    public static Optional<String> toString(Object obj) {
        return Optional.ofNullable(obj).map(Object::toString);
    }

    public static Optional<Integer> toInt(Object obj) {
        return NumUtils.toInt(obj);
    }

    public static Optional<Long> toLong(Object obj) {
        return NumUtils.toLong(obj);
    }

    public static Optional<Double> toDouble(Object obj) {
        return NumUtils.toDouble(obj);
    }

    public static Optional<Boolean> toBoolean(Object obj) {
        return BoolUtils.toBoolean(obj);
    }

    public static boolean absentAsFalse(Object obj) {
        return BoolUtils.absentAsFalse(obj);
    }

    public static boolean absentAsTrue(Object obj) {
        return BoolUtils.absentAsTrue(obj);
    }

    /**
     * return the input obj itself when it's a bool, else
     * try to parse it to bool strictly according to its literal value
     */
    public static Optional<Boolean> toBooleanStrict(Object obj) {
        return BoolUtils.toBooleanStrict(obj);
    }

    public static Optional<Date> toDate(Object obj) {
        return DateUtils.toDate(obj);
    }

    public static Date absentAsNow(Object obj) {
        return DateUtils.absentAsNow(obj);
    }

}
