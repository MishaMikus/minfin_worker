package server.logan_park.view.weekly_report_general.version_2;

import org.apache.log4j.Logger;
import server.logan_park.helper.AutomaticallyWeeklyReportHelper;
import server.logan_park.helper.model.PaymentDriverRecord;
import server.logan_park.helper.model.PaymentOwnerRecord;
import server.logan_park.view.weekly_report_bolt.WeeklyReportBoltHelper;
import server.logan_park.view.weekly_report_bolt.model.WeeklyReportBolt;
import server.logan_park.view.weekly_report_general.DateValidator;
import server.logan_park.view.weekly_report_general.WeekLinksHelper;
import server.logan_park.view.weekly_report_general.model.DriverOwnerStat;
import server.logan_park.view.weekly_report_general.model.DriverStatGeneral;
import server.logan_park.view.weekly_report_general.model.OwnerStat;
import server.logan_park.view.weekly_report_general.model.WeeklyReportGeneral;

import java.util.Date;
import java.util.Map;

import static java.lang.Math.round;
import static server.logan_park.helper.CommonWeeklyReportHelper.WEEK_EARN_LIMIT;

public class NewWeeklyReportGeneralHelper {
    private final static Logger LOGGER = Logger.getLogger(NewWeeklyReportGeneralHelper.class);

    public static void main(String[] args) {
        makeReport( new DateValidator().parseDate("2020.08.18"));
    }

    public static WeeklyReportGeneral makeReport() {
        WeeklyReportBolt weeklyReportBolt = WeeklyReportBoltHelper.makeReport(new Date());
        AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper = new AutomaticallyWeeklyReportHelper(new Date());
        return makeReport(weeklyReportBolt, automaticallyWeeklyReportHelper);
    }

    public static WeeklyReportGeneral makeReport(Date date) {
        WeeklyReportBolt weeklyReportBolt = WeeklyReportBoltHelper.makeReport(date);
        AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper = new AutomaticallyWeeklyReportHelper(date);
        return makeReport(weeklyReportBolt, automaticallyWeeklyReportHelper);
    }

    private static WeeklyReportGeneral makeReport(WeeklyReportBolt weeklyReportBolt,
                                                  AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper) {

        return new WeeklyReportBuilder(weeklyReportBolt,automaticallyWeeklyReportHelper)
                .addWeekLinksList()
                .initBoltData()
                .initUserData()
                .calculateSalaryAndChange()
                .updateBoltChange()
                .updateUberChange()
                .updateSummaryChange()
                .calculateOwner()
                .calculateGeneralAmount()
                .calculateGeneralCash()
                .calculateTax()
                .calculateProfit()
                .build();
    }


}
