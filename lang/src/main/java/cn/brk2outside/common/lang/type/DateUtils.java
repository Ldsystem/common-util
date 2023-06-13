package cn.brk2outside.common.lang.type;

import cn.brk2outside.common.lang.StrUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

    public static Optional<Date> toDate(Object obj) {
        return obj instanceof Date ? Optional.of((Date) obj)
                : Optional.ofNullable(obj)
                .map(Object::toString)
                .map(DateUtils::strToDate);
    }

    static Pattern DATE_PATTERN = Pattern.compile("([0-9]{2,4})-([0-9]{1,2})-([0-9]{1,2})");
    static Pattern TIME_PATTERN = Pattern.compile("([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})(.[0-9]+)?");
    static Pattern DATETIME_PATTERN = Pattern.compile("([0-9]{2,4})-([0-9]{1,2})-([0-9]{1,2})\\s+([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})(.[0-9]+)?");

    private static Date strToDate(String str) {
        if (StrUtil.hasWord(str)) {
            Calendar res = Calendar.getInstance();
            str = str.trim();
            Matcher matcher = DATETIME_PATTERN.matcher(str);
            System.out.println(matcher.groupCount());
            if (matcher.matches()) {
                int year = Integer.parseInt(matcher.group(1));
                res.set(Calendar.YEAR, year);
                int month = Integer.parseInt(matcher.group(2));
                res.set(Calendar.MONTH, month - 1);
                int dayOfMonth = Integer.parseInt(matcher.group(3));
                res.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int hourOfDay = Integer.parseInt(matcher.group(4));
                res.set(Calendar.HOUR_OF_DAY, hourOfDay);
                int minutes = Integer.parseInt(matcher.group(5));
                res.set(Calendar.MINUTE, minutes);
                int seconds = Integer.parseInt(matcher.group(6));
                res.set(Calendar.SECOND, seconds);
                return res.getTime();
            } else if ((matcher = DATE_PATTERN.matcher(str)).matches()) {
                int year = Integer.parseInt(matcher.group(1));
                res.set(Calendar.YEAR, year);
                int month = Integer.parseInt(matcher.group(2));
                res.set(Calendar.MONTH, month - 1);
                int dayOfMonth = Integer.parseInt(matcher.group(3));
                res.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                return res.getTime();
            } else if ((matcher = TIME_PATTERN.matcher(str)).matches()) {
                int hourOfDay = Integer.parseInt(matcher.group(1));
                res.set(Calendar.HOUR_OF_DAY, hourOfDay);
                int minutes = Integer.parseInt(matcher.group(2));
                res.set(Calendar.MINUTE, minutes);
                int seconds = Integer.parseInt(matcher.group(3));
                res.set(Calendar.SECOND, seconds);
                return res.getTime();
            } else if (str.matches("^[0-9]+$")) {
                return new Date(Long.parseLong(str));
            }
        }
        throw new NumberFormatException("Not a valid date string: " + str);
    }

    public static Date absentAsNow(Object obj) {
        return toDate(obj).orElse(new Date());
    }
}
