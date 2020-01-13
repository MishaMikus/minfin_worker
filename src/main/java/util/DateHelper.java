package util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static server.logan_park.view.weekly_report_general.DateValidator.SDF;

public class DateHelper {
    public static final long MS_IN_DAY = 24 * 60 * 60 * 1000L;

    public static Date getDayDate(Date date) {
        return new Date((date.getTime() / MS_IN_DAY) * MS_IN_DAY);
    }

    public static Date roundToMidnight(Date date) {
        try {
            return SDF.parse(SDF.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getWeekStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }
}
