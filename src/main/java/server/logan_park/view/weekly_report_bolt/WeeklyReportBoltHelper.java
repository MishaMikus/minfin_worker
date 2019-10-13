package server.logan_park.view.weekly_report_bolt;

import org.apache.log4j.Logger;
import orm.entity.bolt.BoltPaymentRecordDay;
import orm.entity.bolt.BoltPaymentRecordDayDAO;
import server.logan_park.view.weekly_report_bolt.model.DriverStat;
import server.logan_park.view.weekly_report_bolt.model.WeeklyReportBolt;
import server.logan_park.view.weekly_report_bolt.model.Workout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static ui_automation.bolt.RecordHelper.SDF;

public class WeeklyReportBoltHelper {

    private final static Logger LOGGER = Logger.getLogger(WeeklyReportBoltHelper.class);

    public static WeeklyReportBolt makeReport() {
        WeeklyReportBolt weeklyReportBolt = new WeeklyReportBolt();
        Date previousMonday = prevMonday();
        List<BoltPaymentRecordDay> list = BoltPaymentRecordDayDAO.getInstance().findAllByCurrentWeek(previousMonday);
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
            entry.getValue().setCash(cash );
            entry.getValue().setSalary(salary);
            entry.getValue().setChange(change);
            entry.getValue().setWorkoutCount(workoutCount);

            if (amount > 0) {
                weeklyReportBolt.getDriverStatList().add(entry.getValue());
            }

        }

        //clear zero day
        weeklyReportBolt.getDriverStatList().forEach(w ->
                w.setWorkoutList(w.getWorkoutList()
                        .stream().filter(ww -> ww.getAmount()!=0)
                        .collect(Collectors.toList())));

        //add Company summary
        weeklyReportBolt.setGeneralAmount(generalAmount);
        weeklyReportBolt.setGeneralProfit((int) round(generalAmount * 0.65));
        LOGGER.info(weeklyReportBolt);
        return weeklyReportBolt;
    }


    private static Workout makeWorkout(BoltPaymentRecordDay boltPaymentRecordDay) {
        Workout workout = new Workout();
        int clearAmount=(boltPaymentRecordDay.getAmount().intValue() + boltPaymentRecordDay.getBolt_commission().intValue());
        workout.setAmount(clearAmount );//without commission
        workout.setCash(-boltPaymentRecordDay.getCash().intValue());
        workout.setName(SDF.format(boltPaymentRecordDay.getTimestamp()));
        long salary = round(clearAmount * 0.35);
        workout.setSalary((int) salary);
        workout.setChange((int) ((-boltPaymentRecordDay.getCash().longValue() - salary)));
        return workout;
    }

    public static Date prevMonday() {
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        long millisInDay = 24 * 60 * 60 * 1000L;
        if (date.getTime() / millisInDay == new Date().getTime() / millisInDay) {
            date = new Date(date.getTime() - millisInDay * 7);
        }
        return date;
    }
}
