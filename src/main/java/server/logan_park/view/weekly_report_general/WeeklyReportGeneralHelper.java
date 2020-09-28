package server.logan_park.view.weekly_report_general;

import org.apache.log4j.Logger;
import orm.entity.logan_park.partner.Partner;
import orm.entity.logan_park.partner.PartnerDAO;
import server.logan_park.helper.AutomaticallyWeeklyReportHelper;
import server.logan_park.helper.model.PaymentDriverRecord;
import server.logan_park.helper.model.PaymentOwnerRecord;
import server.logan_park.view.weekly_report_bolt.WeeklyReportBoltHelper;
import server.logan_park.view.weekly_report_bolt.model.WeeklyReportBolt;
import server.logan_park.view.weekly_report_general.model.DriverOwnerStat;
import server.logan_park.view.weekly_report_general.model.DriverStatGeneral;
import server.logan_park.view.weekly_report_general.model.OwnerStat;
import server.logan_park.view.weekly_report_general.model.WeeklyReportGeneral;

import java.util.*;
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

    private static WeeklyReportGeneral makeReport(WeeklyReportBolt weeklyReportBolt,
                                                  AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper) {
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
            if (amount < WEEK_EARN_LIMIT) {
                setSalary(d, 35);
            } else {
                setSalary(d, 40);
            }
            d.getSum().setSalary(d.getBoltStat().getSalary() + d.getUberStat().getSalary() + d.getUberStat().getTips());
            d.getSum().setChange(cash - d.getSum().getSalary());
        });

        //Calculate OWNER
        Map<String, PaymentOwnerRecord> ownerTable = automaticallyWeeklyReportHelper.makeOwnerMap();
        ownerTable.forEach((driverName, paymentDriverRecord) -> {
            DriverOwnerStat driverOwnerStat = new DriverOwnerStat();

            //Uber
            driverOwnerStat.getUber_stat().setAmount(paymentDriverRecord.getOwnerPaymentViews().getAmount());
            driverOwnerStat.getUber_stat().setCash(paymentDriverRecord.getOwnerPaymentViews().getCash());
            driverOwnerStat.getUber_stat().setCommission((int) round(driverOwnerStat.getUber_stat().getAmount() * 0.05));
            driverOwnerStat.getUber_stat().setWithdraw(driverOwnerStat.getUber_stat().getAmount() - driverOwnerStat.getUber_stat().getCash() - driverOwnerStat.getUber_stat().getCommission());

            //Bolt
            OwnerStat ownerStat = weeklyReportBolt
                    .getOwnerStatList()
                    .stream()
                    .filter(s -> s.getDriverName()
                            .equals(driverName))
                    .findAny().orElse(null);

            driverOwnerStat.getBolt_stat().setAmount(ownerStat == null ? 0 : ownerStat.getAmount());
            driverOwnerStat.getBolt_stat().setCash(ownerStat == null ? 0 : ownerStat.getCash());
            driverOwnerStat.getBolt_stat().setCommission(ownerStat == null ? 0 : ownerStat.getCommission());
            driverOwnerStat.getBolt_stat().setWithdraw(driverOwnerStat.getBolt_stat().getAmount() - driverOwnerStat.getBolt_stat().getCash());

            //General
            driverOwnerStat.getGeneral_stat().setAmount(driverOwnerStat.getUber_stat().getAmount() + driverOwnerStat.getBolt_stat().getAmount());
            driverOwnerStat.getGeneral_stat().setCommission(driverOwnerStat.getUber_stat().getCommission() + driverOwnerStat.getBolt_stat().getCommission());
            driverOwnerStat.getGeneral_stat().setCash(driverOwnerStat.getUber_stat().getCash() + driverOwnerStat.getBolt_stat().getCash());
            driverOwnerStat.getGeneral_stat().setWithdraw(driverOwnerStat.getUber_stat().getWithdraw() + driverOwnerStat.getBolt_stat().getWithdraw());

            driverOwnerStat.setName(driverName);
            weeklyReportGeneral.getDriverOwnerStatList().add(driverOwnerStat);
        });

        weeklyReportGeneral.getCompanyAccountStat().setBrandingUber(uberBranding[0]);

        Integer driverAmount = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getAmount()).sum();
        Integer ownerAmount_bolt = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getBolt_stat().getAmount()).sum();
        Integer ownerAmount_uber = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getUber_stat().getAmount()).sum();
        weeklyReportGeneral.getCompanyAccountStat().setGeneralAmount(driverAmount + ownerAmount_bolt + ownerAmount_uber);

        Integer driverCash = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getCash()).sum();
        Integer ownerCashUber = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getUber_stat().getCash()).sum();
        Integer ownerCashBolt = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getBolt_stat().getCash()).sum();
        weeklyReportGeneral.getCompanyAccountStat().setCash(driverCash + ownerCashUber + ownerCashBolt);


        Integer driverAmountUber = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getUberStat().getAmount()).sum();
        Integer driverCashUber = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getUberStat().getCash()).sum();
        int noCashDriverUber = driverAmountUber - driverCashUber;
        int noCashDriverOwnerUber = ownerAmount_uber - ownerCashUber;
        int noCash = noCashDriverUber + noCashDriverOwnerUber;
        weeklyReportGeneral.getCompanyAccountStat().setTax((int) round(noCash * 0.05));

        int driverSalary = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getSalary()).sum();
        int ownerWithdrawUber = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getUber_stat().getWithdraw()).sum();
        int ownerWithdrawBolt = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getBolt_stat().getWithdraw()).sum();
        int profit = weeklyReportGeneral.getCompanyAccountStat().getGeneralAmount() - driverSalary - ownerWithdrawUber - ownerWithdrawBolt - ownerCashUber - ownerCashBolt;

        weeklyReportGeneral.getCompanyAccountStat().setGeneralProfit(profit - weeklyReportGeneral.getCompanyAccountStat().getTax());

        weeklyReportGeneral.getCompanyAccountStat().setClearDriverOwnerProfit((int) round(ownerCashUber * 0.05));


        return weeklyReportGeneral;
    }


    private static void setSalary(DriverStatGeneral d, int percentage) {
        d.setPlan(percentage + "%");
        d.getUberStat().setSalary((int) round(d.getUberStat().getAmount() * (percentage / 100.0)));
        d.getBoltStat().setSalary((int) round(d.getBoltStat().getAmount() * (percentage / 100.0)));
    }

}
