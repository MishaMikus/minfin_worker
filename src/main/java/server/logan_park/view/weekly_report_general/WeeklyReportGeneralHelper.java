package server.logan_park.view.weekly_report_general;

import org.apache.log4j.Logger;
import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import server.logan_park.helper.AutomaticallyWeeklyReportHelper;
import server.logan_park.helper.model.PaymentDriverRecord;
import server.logan_park.helper.model.PaymentOwnerRecord;
import server.logan_park.view.weekly_report_bolt.WeeklyReportBoltHelper;
import server.logan_park.view.weekly_report_bolt.model.WeeklyReportBolt;
import server.logan_park.view.weekly_report_general.model.DriverOwnerStat;
import server.logan_park.view.weekly_report_general.model.DriverStatGeneral;
import server.logan_park.view.weekly_report_general.model.WeekLink;
import server.logan_park.view.weekly_report_general.model.WeeklyReportGeneral;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static server.logan_park.helper.CommonWeeklyReportHelper.*;

public class WeeklyReportGeneralHelper {
    private final static Logger LOGGER = Logger.getLogger(WeeklyReportGeneralHelper.class);

    public static void main(String[] args) {
        makeReport();
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

    private static WeeklyReportGeneral makeReport(WeeklyReportBolt weeklyReportBolt, AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper) {
        WeeklyReportGeneral weeklyReportGeneral = new WeeklyReportGeneral();

        //Week links
        weeklyReportGeneral.setWeekLinksList(new WeekLinksHelper().linkList());

        //BOLT
        weeklyReportBolt.getDriverStatList().forEach(d -> {
            if (!d.getDriverName().equals("Михайло_Мікусь")) {
                DriverStatGeneral driverStatGeneral = new DriverStatGeneral();
                driverStatGeneral.getBoltStat().setAmount(d.getAmount());
                driverStatGeneral.getBoltStat().setCash(d.getCash());
                driverStatGeneral.setDriverName(d.getDriverName());
                weeklyReportGeneral.getDriverStatList().add(driverStatGeneral);
            }
        });


        //Calculate UBER amount
        final Integer[] uberBranding = {0};
        Map<String, PaymentDriverRecord> paymentTable = automaticallyWeeklyReportHelper.makeMap();
        paymentTable.forEach((driverName, paymentDriverRecord) -> {
            if (!driverName.equals("Михайло_Мікусь")) {
                DriverStatGeneral existingDriverStatGeneral = weeklyReportGeneral.getDriverStatList()
                        .stream().filter(d -> d.getDriverName().equals(driverName)).findAny().orElse(null);
                if (existingDriverStatGeneral == null) {

                    DriverStatGeneral newDriverStatGeneral = new DriverStatGeneral();
                    newDriverStatGeneral.setDriverName(driverName);
                    weeklyReportGeneral.getDriverStatList().add(newDriverStatGeneral);
                }

                DriverStatGeneral driverStatGeneral = weeklyReportGeneral.getDriverStatList()
                        .stream().filter(d -> d.getDriverName().equals(driverName)).findAny().orElse(new DriverStatGeneral());

                driverStatGeneral.getUberStat().setAmount(paymentDriverRecord.getSummary().getAmount());
                driverStatGeneral.getUberStat().setCash(paymentDriverRecord.getSummary().getCash());
                driverStatGeneral.getUberStat().setTips(paymentDriverRecord.getSummary().getTips());
            } else {
                uberBranding[0] += paymentDriverRecord.getSummary().getAmount();
            }
        });

        //Calculate salary and Change
        weeklyReportGeneral.getDriverStatList().forEach(d -> {
            Integer amount = d.getBoltStat().getAmount() + d.getUberStat().getAmount();
            Integer cash = d.getBoltStat().getCash() + d.getUberStat().getCash();
            d.getSum().setAmount(amount);
            d.getSum().setCash(cash);
            if (d.getDriverName().equals("Юрій_Горбатий")) {
                d.getUberStat().setSalary((int) round(d.getUberStat().getAmount() * 0.35));
                d.getBoltStat().setSalary((int) round(d.getBoltStat().getAmount() * 0.35));
                if (amount < WEEK_EARN_LIMIT_GORBATY_1) {
                    d.setPlan("35%");
                    d.getSum().setSalary(d.getUberStat().getSalary() + d.getBoltStat().getSalary() + d.getUberStat().getTips());
                } else {
                    d.setPlan("35% + 500");
                    d.getSum().setSalary(d.getUberStat().getSalary() + d.getBoltStat().getSalary() + 500 + d.getUberStat().getTips());
                    if (amount >= WEEK_EARN_LIMIT_GORBATY_2) {
                        d.setPlan("35% + 1000");
                        d.getSum().setSalary(d.getUberStat().getSalary() + d.getBoltStat().getSalary() + 1000 + d.getUberStat().getTips());
                    }
                }
                d.getSum().setChange(cash - d.getSum().getSalary());
            } else {
                if (amount < WEEK_EARN_LIMIT) {
                    setSalary(d, 35);
                    if (d.getDriverName().equals("Олег_Тархов")) {
                        setSalary(d, 60);
                    }

                } else {
                    setSalary(d, 40);
                    if (d.getDriverName().equals("Олег_Тархов")) {
                        setSalary(d, 65);
                    }
                }
                d.getSum().setSalary(d.getBoltStat().getSalary() + d.getUberStat().getSalary() + d.getUberStat().getTips());
                d.getSum().setChange(cash - d.getSum().getSalary());
            }
        });

        //Calculate OWNER
        Map<String, PaymentOwnerRecord> ownerTable = automaticallyWeeklyReportHelper.makeOwnerMap();
        ownerTable.forEach((driverName, paymentDriverRecord) -> {
            DriverOwnerStat driverOwnerStat = new DriverOwnerStat();
            driverOwnerStat.setAmount(paymentDriverRecord.getOwnerPaymentViews().getAmount());
            driverOwnerStat.setCash(paymentDriverRecord.getOwnerPaymentViews().getCash());
            driverOwnerStat.setCommission((int) round(driverOwnerStat.getAmount() * 0.05));
            driverOwnerStat.setName(driverName);
            driverOwnerStat.setWithdraw(driverOwnerStat.getAmount() - driverOwnerStat.getCash() - driverOwnerStat.getCommission());
            weeklyReportGeneral.getDriverOwnerStatList().add(driverOwnerStat);
        });

        weeklyReportGeneral.getCompanyAccountStat().setBrandingUber(uberBranding[0]);

        Integer driverAmount = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getAmount()).sum();
        Integer ownerAmount = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(DriverOwnerStat::getAmount).sum();
        weeklyReportGeneral.getCompanyAccountStat().setGeneralAmount(driverAmount + ownerAmount);

        Integer driverCash = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getCash()).sum();
        Integer ownerCash = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(DriverOwnerStat::getCash).sum();
        weeklyReportGeneral.getCompanyAccountStat().setCash(driverCash + ownerCash);


        Integer driverAmountUber = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getUberStat().getAmount()).sum();
        Integer driverCashUber = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getUberStat().getCash()).sum();
        int noCashDriverUber = driverAmountUber - driverCashUber;
        int noCashDriverOwnerUber = ownerAmount - ownerCash;
        int noCash = noCashDriverUber + noCashDriverOwnerUber;
        weeklyReportGeneral.getCompanyAccountStat().setTax((int) round(noCash * 0.05));

        int driverSalary = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getSalary()).sum();
        int ownerWithdraw = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(DriverOwnerStat::getWithdraw).sum();
        int profit = weeklyReportGeneral.getCompanyAccountStat().getGeneralAmount() - driverSalary - ownerWithdraw - ownerCash;

        weeklyReportGeneral.getCompanyAccountStat().setGeneralProfit(profit - weeklyReportGeneral.getCompanyAccountStat().getTax());

        weeklyReportGeneral.getCompanyAccountStat().setClearDriverOwnerProfit((int) round(ownerCash * 0.05));

        return weeklyReportGeneral;
    }

    private static void setSalary(DriverStatGeneral d, int percentage) {
        d.setPlan(percentage + "%");
        d.getUberStat().setSalary((int) round(d.getUberStat().getAmount() * (percentage / 100.0)));
        d.getBoltStat().setSalary((int) round(d.getBoltStat().getAmount() * (percentage / 100.0)));
    }

}
