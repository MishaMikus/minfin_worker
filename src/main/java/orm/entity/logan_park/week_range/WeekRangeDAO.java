package orm.entity.logan_park.week_range;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static server.logan_park.view.weekly_report_general.DateValidator.SDF;
import static util.DateHelper.getWeekStartDate;
import static util.DateHelper.roundToMidnight;

public class WeekRangeDAO extends GenericAbstractDAO<WeekRange> {
    private static final Logger LOGGER = Logger.getLogger(WeekRangeDAO.class);

    public WeekRangeDAO() {
        super(WeekRange.class);
    }

    private static final WeekRangeDAO INSTANCE = new WeekRangeDAO();

    public static WeekRangeDAO getInstance() {
        return INSTANCE;
    }


//    2019-10-14 14:15:05,088 [main]  INFO WeekRangeDAO.findOrCreateWeek:26 - Wed Jul 10 07:50:19 EEST 2019 -> null
//            2019-10-14 14:15:05,088 [main]  INFO WeekRangeDAO.findOrCreateWeek:28 - create new week_range
//2019-10-14 14:15:05,279 [main]  INFO WeekRangeDAO.save:42 - save WeekRange{id=5, start=Mon Jul 08 07:50:19 EEST 2019, end=Sun Jul 14 07:50:19 EEST 2019}

    public WeekRange findOrCreateWeek(Date date, String creator) {
        date = roundToMidnight(date);
        Date finalDate = date;
        WeekRange weekRange = findAll().stream().filter(r ->
                r.getStart().getTime() <= finalDate.getTime() && r.getEnd().getTime() >= finalDate.getTime()
        ).findAny().orElse(null);
        LOGGER.info(date + " -> " + weekRange);
        if (weekRange == null) {

            //create new week_range
            LOGGER.info("create new week_range");
            Date start = roundToMidnight(getWeekStartDate(date));
            long msInDay = 24L * 60L * 60L * 1000L;
            Date end = new Date(start.getTime() + 6L * msInDay);
            WeekRange newWeekRange = new WeekRange();
            newWeekRange.setStart(start);
            newWeekRange.setEnd(end);
            newWeekRange.setCreator(creator);

            //save
            int id = (int) save(newWeekRange);
            newWeekRange.setId(id);
            weekRange = newWeekRange;
            LOGGER.info("newWeekRange : " + newWeekRange);
        }
        return weekRange;
    }


}
