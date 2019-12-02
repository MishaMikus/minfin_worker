package server.logan_park.view.weekly_report_general;

import org.apache.log4j.Logger;
import orm.entity.logan_park.week_range.WeekRangeDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator {
    private final static Logger LOGGER = Logger.getLogger(DateValidator.class);
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy.MM.dd");

    public boolean isValidDate(String stringDate) {
        //valid : 2019.10.14
        try {
            Date date = SDF.parse(stringDate);
            return WeekRangeDAO.getInstance().findOrCreateWeek(date, "web_user") != null;
        } catch (ParseException e) {
            LOGGER.warn("invalid date : " + stringDate + " for pattern : " + SDF + "\n" + e.getMessage());
        }
        return false;
    }

    public Date parseDate(String stringDate) {
        try {
            return SDF.parse(stringDate);
        } catch (ParseException e) {
            LOGGER.warn("invalid date : " + stringDate + " for pattern : " + SDF + "\n" + e.getMessage());
        }
        return new Date();
    }
}
