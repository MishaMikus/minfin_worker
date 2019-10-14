package server.logan_park.view.weekly_report_general;

import org.apache.log4j.Logger;
import server.logan_park.view.weekly_report_general.model.DateRange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DateRangeRepo {
    private final static Logger LOGGER = Logger.getLogger(DateRangeRepo.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy.MM.dd");

    public boolean validateDate(String stringDate) {
        //valid : 2019.10.14
        try {
            Date date = SDF.parse(stringDate);
            return findRange(date) != null;
        } catch (ParseException e) {
            LOGGER.warn("invalid date : " + stringDate + " for pattern : " + SDF + "\n" + e.getMessage());
        }

        return false;
    }

    private Set<DateRange> dateRangeSet = findAllDataRanges();

    private Set<DateRange> findAllDataRanges() {
        Set<DateRange> res = new HashSet<>();
        return res;

    }

    private DateRange findRange(Date date) {
        return new DateRange();
    }
}
