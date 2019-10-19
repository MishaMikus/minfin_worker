package util;

import java.util.Date;

public class DateHelper {
    public static final long MS_IN_DAY = 24 * 60 * 60 * 1000L;

    public static Date getDayDate(Date date) {
        return new Date((date.getTime() / MS_IN_DAY) * MS_IN_DAY);
    }
}
