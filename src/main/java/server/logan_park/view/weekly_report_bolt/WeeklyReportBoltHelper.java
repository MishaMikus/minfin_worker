package server.logan_park.view.weekly_report_bolt;

import org.apache.log4j.Logger;
import orm.entity.bolt.payment_record_day.BoltPaymentRecordDay;
import orm.entity.bolt.payment_record_day.BoltPaymentRecordDayDAO;
import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import server.logan_park.view.weekly_report_bolt.model.DriverStat;
import server.logan_park.view.weekly_report_bolt.model.WeeklyReportBolt;
import server.logan_park.view.weekly_report_bolt.model.Workout;
import server.logan_park.view.weekly_report_general.WeekLinksHelper;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static ui_automation.bolt.RecordHelper.SDF_DATE;

public class WeeklyReportBoltHelper {

    private final static Logger LOGGER = Logger.getLogger(WeeklyReportBoltHelper.class);

    public static WeeklyReportBolt makeReport(Date weekFlag) {
        WeeklyReportBolt weeklyReportBolt = new WeeklyReportBolt();
        //Week links
        weeklyReportBolt.setWeekLinksList(new WeekLinksHelper().linkList());

        WeekRange weekRange = WeekRangeDAO.getInstance().findOrCreateWeek(weekFlag, "bolt_worker");
        List<BoltPaymentRecordDay> list = BoltPaymentRecordDayDAO.getInstance().findAllByWeekRangeId(weekRange.getId());
        Map<String, DriverStat> tmpMap = new HashMap<>();

        //firstRun (separate driver)
        for (BoltPaymentRecordDay boltPaymentRecordDay : list) {
            tmpMap.putIfAbsent(boltPaymentRecordDay.getDriverName(), new DriverStat());
            tmpMap.get(boltPaymentRecordDay.getDriverName()).getWorkoutList().add(makeWorkout(boltPaymentRecordDay));
            tmpMap.get(boltPaymentRecordDay.getDriverName()).setDriverName(boltPaymentRecordDay.getDriverName());

            tmpMap.get(boltPaymentRecordDay.getDriverName()).setPlan("35 %");
        }

        //second run (calculate summary)
        int generalAmount = 0;
        int generalProfit = 0;
        for (Map.Entry<String, DriverStat> entry : tmpMap.entrySet()) {
            int workoutCount = 0;
            int amount = 0;
            int cash = 0;
            int salary = 0;
            int change = 0;
            for (Workout workout : entry.getValue().getWorkoutList()) {
                workoutCount += (workout.getAmount() > 0) ? 1 : 0;
                amount += workout.getAmount();
                cash += workout.getCash();
                salary += workout.getSalary();
                change += workout.getChange();
            }
            generalAmount += amount;
            entry.getValue().setAmount(amount);
            entry.getValue().setCash(cash);
            entry.getValue().setSalary(salary);
            entry.getValue().setChange(change);
            entry.getValue().setWorkoutCount(workoutCount);
            generalProfit+=amount* 0.65;
            if (amount > 0) {
                weeklyReportBolt.getDriverStatList().add(entry.getValue());
            }

        }

        //clear zero day
        weeklyReportBolt.getDriverStatList().forEach(w ->
                w.setWorkoutList(w.getWorkoutList()
                        .stream().filter(ww -> ww.getAmount() != 0)
                        .collect(Collectors.toList())));

        //add Company summary
        weeklyReportBolt.setGeneralAmount(generalAmount);
        weeklyReportBolt.setGeneralProfit(generalProfit);
        return weeklyReportBolt;
    }


    private static Workout makeWorkout(BoltPaymentRecordDay boltPaymentRecordDay) {
        Workout workout = new Workout();
        int clearAmount = (int) round((boltPaymentRecordDay.getAmount() + boltPaymentRecordDay.getBolt_commission()+boltPaymentRecordDay.getBonus()));

        workout.setAmount(clearAmount);//without commission
        workout.setCash(-boltPaymentRecordDay.getCash().intValue());
        workout.setName(SDF_DATE.format(boltPaymentRecordDay.getTimestamp()));
        long salary = round(clearAmount * 0.35);
        workout.setSalary((int) salary);
        workout.setChange((int) ((-boltPaymentRecordDay.getCash().longValue() - salary)));
        return workout;
    }
}
