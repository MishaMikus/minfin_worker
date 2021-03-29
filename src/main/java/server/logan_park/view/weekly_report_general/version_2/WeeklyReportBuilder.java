package server.logan_park.view.weekly_report_general.version_2;

import orm.entity.logan_park.driver.UberDriver;
import orm.entity.logan_park.driver.UberDriverDAO;
import orm.entity.logan_park.partner.Partner;
import orm.entity.logan_park.partner.PartnerDAO;
import server.logan_park.helper.AutomaticallyWeeklyReportHelper;
import server.logan_park.helper.model.PaymentDriverRecord;
import server.logan_park.helper.model.PaymentOwnerRecord;
import server.logan_park.view.weekly_report_bolt.model.WeeklyReportBolt;
import server.logan_park.view.weekly_report_general.WeekLinksHelper;
import server.logan_park.view.weekly_report_general.model.DriverOwnerStat;
import server.logan_park.view.weekly_report_general.model.DriverStatGeneral;
import server.logan_park.view.weekly_report_general.model.WeeklyReportGeneral;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static server.logan_park.helper.CommonWeeklyReportHelper.WEEK_EARN_LIMIT;

public class WeeklyReportBuilder {
    private final static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(WeeklyReportBuilder.class);
    private final WeeklyReportGeneral weeklyReportGeneral;
    private final WeeklyReportBolt weeklyReportBolt;
    private final AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper;

    public WeeklyReportBuilder(WeeklyReportBolt weeklyReportBolt, AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper) {
        this.weeklyReportGeneral = new WeeklyReportGeneral();
        this.weeklyReportBolt = weeklyReportBolt;
        this.automaticallyWeeklyReportHelper = automaticallyWeeklyReportHelper;
    }


    public WeeklyReportGeneral build() {
        return weeklyReportGeneral;
    }

    public WeeklyReportBuilder addWeekLinksList() {
        weeklyReportGeneral.setWeekLinksList(new WeekLinksHelper().linkList());
        return this;
    }

    public WeeklyReportBuilder initBoltData() {
        weeklyReportBolt.getDriverStatList().forEach(d -> {
            if (!d.getDriverName().equals("Михайло_Мікусь")) {
                DriverStatGeneral driverStatGeneral = new DriverStatGeneral();
                driverStatGeneral.getBoltStat().setAmount(d.getAmount());
                driverStatGeneral.getBoltStat().setCash(d.getCash());
                driverStatGeneral.getBoltStat().setTips(d.getTips());
                driverStatGeneral.setDriverName(d.getDriverName());
                weeklyReportGeneral.getDriverStatList().add(driverStatGeneral);
            }
        });
        return this;
    }

    public WeeklyReportBuilder initUserData() {
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
        weeklyReportGeneral.getCompanyAccountStat().setBrandingUber(uberBranding[0]);
        return this;
    }

    public WeeklyReportBuilder calculateSalaryAndChange() {
        weeklyReportGeneral.getDriverStatList().forEach(d -> {
            Integer amount = d.getBoltStat().getAmount() + d.getUberStat().getAmount();
            Integer cash = d.getBoltStat().getCash() + d.getUberStat().getCash();
            Integer tips = d.getBoltStat().getTips() + d.getUberStat().getTips();
            d.getSum().setAmount(amount);
            d.getSum().setTips(tips);
            d.getSum().setCash(cash);
            if (amount < WEEK_EARN_LIMIT) {
                setSalary(d, 35);
            } else {
                setSalary(d, 40);
            }
            d.getSum().setSalary(d.getBoltStat().getSalary() + d.getUberStat().getSalary());
            d.getSum().setChange(d.getBoltStat().getChange() + d.getUberStat().getChange());
        });
        return this;
    }

    private static void setSalary(DriverStatGeneral d, int percentage) {
        d.setPlan(percentage + "%");
        d.getUberStat().setSalary((int) round(d.getUberStat().getAmount() * (percentage / 100.0)));
        d.getBoltStat().setSalary((int) round(d.getBoltStat().getAmount() * (percentage / 100.0)));
    }

    public WeeklyReportBuilder calculateOwner() {
        LOGGER.info("calculateOwner");
        //Calculate OWNER
        Map<String, PaymentOwnerRecord> uberOwnerTable = automaticallyWeeklyReportHelper.makeOwnerMap();
        Map<String, PaymentOwnerRecord> boltOwnerTable = automaticallyWeeklyReportHelper.makeOwnerMapBolt(weeklyReportBolt);
        Set<String> driverNameSet = new HashSet<>();
        driverNameSet.addAll(uberOwnerTable.keySet());
        driverNameSet.addAll(boltOwnerTable.keySet());
        driverNameSet.forEach((driverName) -> {
            DriverOwnerStat driverOwnerStat = new DriverOwnerStat();

            //Uber
            if (uberOwnerTable.get(driverName) != null) {
                driverOwnerStat.getUber_stat().setAmount(uberOwnerTable.get(driverName).getOwnerPaymentViews().getAmount());
                driverOwnerStat.getUber_stat().setCash(uberOwnerTable.get(driverName).getOwnerPaymentViews().getCash());
                driverOwnerStat.getUber_stat().setTips(uberOwnerTable.get(driverName).getOwnerPaymentViews().getTips());
                driverOwnerStat.setPartner(uberOwnerTable.get(driverName).getPartner());
            }

            if (boltOwnerTable.get(driverName) != null) {
                driverOwnerStat.getBolt_stat().setAmount(boltOwnerTable.get(driverName).getOwnerPaymentViews().getAmount());
                driverOwnerStat.getBolt_stat().setCash(boltOwnerTable.get(driverName).getOwnerPaymentViews().getCash());
                driverOwnerStat.getBolt_stat().setTips(boltOwnerTable.get(driverName).getOwnerPaymentViews().getTips());
                driverOwnerStat.setPartner(boltOwnerTable.get(driverName).getPartner());
            }

            //General
            driverOwnerStat.getGeneral_stat().setAmount(driverOwnerStat.getUber_stat().getAmount() + driverOwnerStat.getBolt_stat().getAmount());
            driverOwnerStat.getGeneral_stat().setTips(driverOwnerStat.getUber_stat().getTips() + driverOwnerStat.getBolt_stat().getTips());
            driverOwnerStat.getGeneral_stat().setCommission((int) round(driverOwnerStat.getGeneral_stat().getAmount() * partnerCommission(driverName)));
            driverOwnerStat.getGeneral_stat().setCash(driverOwnerStat.getUber_stat().getCash() + driverOwnerStat.getBolt_stat().getCash());

            driverOwnerStat.getGeneral_stat().setWithdraw(
                    driverOwnerStat.getGeneral_stat().getAmount()
                            - driverOwnerStat.getGeneral_stat().getCash()
                            - driverOwnerStat.getGeneral_stat().getCommission()
                            + driverOwnerStat.getGeneral_stat().getTips());

            driverOwnerStat.setName(driverName);
            if (driverOwnerStat.getGeneral_stat().getAmount() > 0) {
                weeklyReportGeneral.getDriverOwnerStatList().add(driverOwnerStat);
            }
        });

        return this;
    }

    static final double PARTNER_5 = 0.05;
    static final double PARTNER_0 = 0;

    private static Double partnerCommission(String driverName) {
        UberDriver driver = UberDriverDAO.getInstance().findDriverByDriverName(driverName);
        return driver.getDriverType().equals("owner_5")?PARTNER_5:PARTNER_0;
    }

    List<Partner> partnerList = PartnerDAO.getInstance().findAll();

    private String getPartnerName(UberDriver currentDriver) {
        Partner partner = partnerList.stream()
                .filter(p -> p.getIdpartner().intValue() == currentDriver.getPartner_id())
                .findAny().orElse(null);
        if (partner == null) {
            return null;
        } else return partner.getName();
    }


    public WeeklyReportBuilder calculateGeneralAmount() {
        Integer driverAmount = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getAmount()).sum();
        Integer ownerAmount_bolt = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getBolt_stat().getAmount()).sum();
        Integer ownerAmount_uber = calculateOwnerAmountUber();
        weeklyReportGeneral.getCompanyAccountStat().setGeneralAmount(driverAmount + ownerAmount_bolt + ownerAmount_uber);
        return this;
    }

    private Integer calculateOwnerAmountUber() {
        return weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getUber_stat().getAmount()).sum();
    }

    public WeeklyReportBuilder calculateGeneralCash() {
        Integer driverCash = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getCash()).sum();
        Integer ownerCashUber = calculateOwnerCashUber();
        Integer ownerCashBolt = calculateOwnerCashBolt();
        weeklyReportGeneral.getCompanyAccountStat().setCash(driverCash + ownerCashUber + ownerCashBolt);
        return this;
    }

    private Integer calculateOwnerCashBolt() {
        return weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getBolt_stat().getCash()).sum();
    }

    private Integer calculateOwnerCashUber() {
        return weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getUber_stat().getCash()).sum();
    }

    public WeeklyReportBuilder calculateTax() {
        Integer driverAmountUber = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getUberStat().getAmount()).sum();
        Integer driverCashUber = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getUberStat().getCash()).sum();
        int noCashDriverUber = driverAmountUber - driverCashUber;
        int noCashDriverOwnerUber = calculateOwnerAmountUber() - calculateOwnerCashUber();
        int noCash = noCashDriverUber + noCashDriverOwnerUber;
        weeklyReportGeneral.getCompanyAccountStat().setTax((int) round(noCash * 0.05));
        return this;
    }

    public WeeklyReportBuilder calculateProfit() {
        int driverSalary = weeklyReportGeneral.getDriverStatList().stream().mapToInt(d -> d.getSum().getSalary()).sum();
        int ownerWithdrawUber = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getUber_stat().getWithdraw()).sum();
        int ownerWithdrawBolt = weeklyReportGeneral.getDriverOwnerStatList().stream().mapToInt(d -> d.getBolt_stat().getWithdraw()).sum();
        int profit = weeklyReportGeneral.getCompanyAccountStat().getGeneralAmount()
                - driverSalary
                - ownerWithdrawUber
                - ownerWithdrawBolt
                - calculateOwnerCashUber()
                - calculateOwnerCashBolt();

        weeklyReportGeneral.getCompanyAccountStat().setGeneralProfit(profit - weeklyReportGeneral.getCompanyAccountStat().getTax());

        weeklyReportGeneral.getCompanyAccountStat().setClearDriverOwnerProfit((int) round(calculateOwnerCashUber() * 0.05));
        return this;
    }

    public WeeklyReportBuilder updateBoltChange() {
        weeklyReportGeneral.getDriverStatList().forEach(d -> {
            d.getBoltStat().setChange(d.getBoltStat().getCash() - d.getBoltStat().getSalary() - d.getBoltStat().getTips());
        });
        return this;
    }

    public WeeklyReportBuilder updateUberChange() {
        weeklyReportGeneral.getDriverStatList().forEach(d -> {
            d.getUberStat().setChange(d.getUberStat().getCash() - d.getUberStat().getSalary() - d.getUberStat().getTips());
        });
        return this;
    }

    public WeeklyReportBuilder updateSummaryChange() {
        weeklyReportGeneral.getDriverStatList().forEach(d -> {
            d.getSum().setChange(d.getSum().getCash() - d.getSum().getSalary() - d.getSum().getTips());
        });
        return this;
    }

    public WeeklyReportBuilder makeSortedOwnerMapTable() {
        List<Partner> partnerList = PartnerDAO.getInstance().findAll();
        partnerList.forEach(p -> {
            weeklyReportGeneral.getDriverOwnerStatMap().put(p.getName(),
                    getOwnerDriverList(p.getName(), weeklyReportGeneral.getDriverOwnerStatList()))
            ;
        });
        return this;
    }


    private static List<DriverOwnerStat> getOwnerDriverList(String partner, List<DriverOwnerStat> driverOwnerStatList) {
        return driverOwnerStatList.stream().filter(d ->

                d.getPartner() != null && d.getPartner().equals(partner)

        ).collect(Collectors.toList());
    }

    public WeeklyReportBuilder clearZeroOwnerMap() {
        weeklyReportGeneral.setDriverOwnerStatMap(weeklyReportGeneral
                .getDriverOwnerStatMap().entrySet().stream()
                .filter(e -> isZeroOwner(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        return this;
    }

    private boolean isZeroOwner(List<DriverOwnerStat> driverOwnerStatList) {
        return driverOwnerStatList.stream().anyMatch(d -> d.getGeneral_stat().getAmount() > 0);
    }
}
