package server.logan_park.view.weekly_report_general;

import org.apache.log4j.Logger;
import server.logan_park.helper.AutomaticallyWeeklyReportHelper;
import server.logan_park.helper.model.PaymentDriverRecord;
import server.logan_park.helper.model.PaymentOwnerRecord;
import server.logan_park.view.weekly_report_bolt.WeeklyReportBoltHelper;
import server.logan_park.view.weekly_report_bolt.model.WeeklyReportBolt;
import server.logan_park.view.weekly_report_general.model.DriverOwnerStat;
import server.logan_park.view.weekly_report_general.model.DriverStatGeneral;
import server.logan_park.view.weekly_report_general.model.WeeklyReportGeneral;

import java.util.Map;

import static java.lang.Math.round;
import static server.logan_park.helper.CommonWeeklyReportHelper.*;

public class WeeklyReportGeneralHelper {
    private final static Logger LOGGER = Logger.getLogger(WeeklyReportGeneralHelper.class);

    public static void main(String[] args) {
        makeReport();
    }
    public static WeeklyReportGeneral makeReport() {
        WeeklyReportGeneral weeklyReportGeneral = new WeeklyReportGeneral();

        //Calculate BOLT amount
        WeeklyReportBolt weeklyReportBolt = WeeklyReportBoltHelper.makeReport();
        weeklyReportBolt.getDriverStatList().forEach(d -> {
            if (!d.getDriverName().equals("Михайло_Мікусь")) {
                DriverStatGeneral driverStatGeneral = new DriverStatGeneral();
                driverStatGeneral.getBoltStat().setAmount(d.getAmount());
                driverStatGeneral.getBoltStat().setCash(d.getCash());
                driverStatGeneral.setDriverName(d.getDriverName());
                weeklyReportGeneral.getDriverStatList().add(driverStatGeneral);
            }
        });

        //UBER driver map
        AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper = new AutomaticallyWeeklyReportHelper();

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
                    d.setPlan("35%");
                    d.getUberStat().setSalary((int) round(d.getUberStat().getAmount() * 0.35));
                    d.getBoltStat().setSalary((int) round(d.getBoltStat().getAmount() * 0.35));

                } else {
                    d.setPlan("40%");
                    d.getUberStat().setSalary((int) round(d.getUberStat().getAmount() * 0.4));
                    d.getBoltStat().setSalary((int) round(d.getBoltStat().getAmount() * 0.4));
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
        int ownerWithdrad = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(DriverOwnerStat::getWithdraw).sum();
        int profit = weeklyReportGeneral.getCompanyAccountStat().getGeneralAmount() - driverSalary - ownerWithdrad;

        weeklyReportGeneral.getCompanyAccountStat().setGeneralProfit(profit - weeklyReportGeneral.getCompanyAccountStat().getTax());

        weeklyReportGeneral.getCompanyAccountStat().setClearDriverOwnerProfit((int) round(ownerCash * 0.05));

        return weeklyReportGeneral;
    }

}