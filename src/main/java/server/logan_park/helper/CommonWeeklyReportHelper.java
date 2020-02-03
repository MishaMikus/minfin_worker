package server.logan_park.helper;

import org.apache.log4j.Logger;
import orm.entity.logan_park.driver.UberDriver;
import orm.entity.logan_park.driver.UberDriverDAO;
import server.logan_park.helper.model.GeneralPartnerSummary;
import server.logan_park.helper.model.PaymentDriverRecord;
import server.logan_park.helper.model.PaymentOwnerRecord;
import server.logan_park.helper.model.SummaryPaymentDriverRecord;
import server.logan_park.service.*;
import server.logan_park.view.weekly_report_manual_uber.OwnerPaymentView;
import server.logan_park.view.weekly_report_manual_uber.PaymentView;
import server.logan_park.view.weekly_report_manual_uber.TripView;
import util.ApplicationPropertyUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class CommonWeeklyReportHelper {
    public static final Integer WEEK_EARN_LIMIT = ApplicationPropertyUtil.getInteger("week_limit",8000);
    List<UberDriver> driverList = UberDriverDAO.getInstance().getDriverList();

    abstract Map<String, Map<Date, PaymentRecordRawRow>> parsePrimaryData(Date weekFlag);

    String content;
    private static final SimpleDateFormat SDF_DAY = new SimpleDateFormat("dd.MM");
    private static final SimpleDateFormat SDF_DAY_YEAR = new SimpleDateFormat("dd.MM.yyyy");
    private Map<String, PaymentDriverRecord> paymentDriverRecordMap;
    private Map<String, PaymentOwnerRecord> paymentOwnerRecordMap;
    private static final SimpleDateFormat SDF_DD_MM_YYYY_HH_MM = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    private static final int WORKOUT_HOUR_DIFF = 5;

    public CommonWeeklyReportHelper(Date weekFlag) {
        primaryParsedData = parsePrimaryData(weekFlag);
    }

    public CommonWeeklyReportHelper(String content, Date weekFlag) {
        this.content = content;
        primaryParsedData = parsePrimaryData(weekFlag);
    }

    public Map<String, Map<Date, PaymentRecordRawRow>> getPrimaryParsedData() {
        return primaryParsedData;
    }

    private Integer getTripCount(Map<Date, PaymentRecordRawRow> rangeMap) {
        Integer count = 0;
        for (PaymentRecordRawRow paymentRecordRawRow : rangeMap.values()) {
            if ("trip".equals(paymentRecordRawRow.getItemType())) {
                count++;
            }
        }
        return count;
    }


    private Long getAmount(Map<Date, PaymentRecordRawRow> rangeMap) {
        return collectAmount(rangeMap.values(), "trip")
                + collectAmount(rangeMap.values(), "promotion");
    }

    private Long collectAmount(Collection<PaymentRecordRawRow> paymentRecordRawRowSet, String key) {
        Double amount = 0d;
        for (PaymentRecordRawRow trip : paymentRecordRawRowSet) {
            if (key.equals(trip.getItemType())) {
                amount += trip.getAmount();
            }
        }
        return Math.round(amount);
    }

    private Long getCash(Map<Date, PaymentRecordRawRow> rangeMap) {
        return -collectAmount(rangeMap.values(), "cash_collected");
    }

    private Long getTips(Map<Date, PaymentRecordRawRow> rangeMap) {
        return collectAmount(rangeMap.values(), "tip");
    }

    private Long getPromotion(Map<Date, PaymentRecordRawRow> rangeMap) {
        return collectAmount(rangeMap.values(), "promotion");
    }


    private String dayLabel(Date start, Date end) {
        if (start.getTime() / 1000 / 60 / 60 / 24 != end.getTime() / 1000 / 60 / 60 / 24) {
            return "ніч з " + SDF_DAY.format(start) + " по " + SDF_DAY.format(end);
        } else return "день " + SDF_DAY_YEAR.format(start);
    }

    private Map<Date, PaymentRecordRawRow> getRangeMap(Date start, Date end, Map<Date, PaymentRecordRawRow> dateListMap) {
        Map<Date, PaymentRecordRawRow> res = new HashMap<>();
        for (Date date : dateListMap.keySet()) {
            if (date.getTime() >= start.getTime() && date.getTime() <= end.getTime()) {
                res.putIfAbsent(date, dateListMap.get(date));
            }
        }
        return res;
    }

    private Map<Date, PaymentRecordRawRow> getRangeMap(Map<Date, PaymentRecordRawRow> dateListMap) {
        Map<Date, PaymentRecordRawRow> res = new HashMap<>();
        for (Date date : dateListMap.keySet()) {
            res.putIfAbsent(date, dateListMap.get(date));
        }
        return res;
    }

    private List<List<Date>> calculateDateRange(Map<Date, PaymentRecordRawRow> dateListMap) {
        List<Date> daleList = new ArrayList<>(dateListMap.keySet());
        Collections.sort(daleList);
        Date start = daleList.get(0);
        Date end = daleList.get(0);
        List<List<Date>> res = new ArrayList<>();
        for (Date date : daleList) {
            int diff = end == null ? 0 : (int) (((date.getTime() - end.getTime()) / 1000) / 60) / 60;
            if (diff > WORKOUT_HOUR_DIFF) {
                List<Date> currentRange = new ArrayList<>();
                currentRange.add(start);
                currentRange.add(end);
                res.add(currentRange);
                start = date;
                end = null;
            } else {
                end = date;
            }
        }
        List<Date> currentRange = new ArrayList<>();
        currentRange.add(start);
        currentRange.add(end == null ? start : end);
        res.add(currentRange);
        return res;
    }

    public Map<String, PaymentDriverRecord> makeMap() {
        Map<String, PaymentDriverRecord> map = new HashMap<>();
        Map<String, Map<Date, PaymentRecordRawRow>> driverMap = getPrimaryParsedData();
        for (String driver : driverMap.keySet()) {
            UberDriver currentDriver = driverByName(driver, driverList);
            if (currentDriver != null && !currentDriver.getDriverType().startsWith("owner")) {
                map.putIfAbsent(driver, new PaymentDriverRecord());
                List<List<Date>> dateRange = calculateDateRange(driverMap.get(driver));
                if (dateRange.size() > 0) {
                    for (List<Date> range : dateRange) {
                        Date start = range.get(0);
                        Date end = range.get(1);
                        String workoutName = dayLabel(start, end);
                        Map<Date, PaymentRecordRawRow> rangeMap = getRangeMap(start, end, driverMap.get(driver));
                        Integer count = getTripCount(rangeMap);
                        Long cash = getCash(rangeMap);
                        Long tips = getTips(rangeMap);
                        Long amount = getAmount(rangeMap);
                        Long promotion = getPromotion(rangeMap);

                        Long salary = Math.round(amount.doubleValue() * 0.35);
                        PaymentView paymentView = new PaymentView(workoutName, count + "", amount + "", cash + "", salary + "", (cash - salary) + "");
                        paymentView.setTips(tips + "");
                        paymentView.setPromotion(promotion + "");
                        paymentView.setStart(SDF_DD_MM_YYYY_HH_MM.format(start));
                        paymentView.setEnd(SDF_DD_MM_YYYY_HH_MM.format(end));

                        long duration = end.getTime() - start.getTime();
                        double hours = duration / 1000d / 60d / 60d;
                        String hoursString = (Math.round(hours * 10) / 10d) + "";
                        paymentView.setDuration(hoursString);
                        paymentView.setTripListId("tripListId_" + UUID.randomUUID().toString());
                        paymentView.setTripList(makeTripList(rangeMap));
                        if (map.get(driver).getRecordList() == null) {
                            map.get(driver).setRecordList(new ArrayList<>());
                        }
                        map.get(driver).getRecordList().add(paymentView);
                    }
                    map.get(driver).setSummary(makeSummary(map.get(driver).getRecordList()));
                    map.get(driver).setDriverName(driver);
                }
            }
        }
        map = makeRateAndSortByRate(map);
        map = recalculateWithMotivation(map);
        paymentDriverRecordMap = map;
        return map;
    }

    private Map<String, PaymentDriverRecord> recalculateWithMotivation(Map<String, PaymentDriverRecord> map) {
        for (Map.Entry<String, PaymentDriverRecord> entry : map.entrySet()) {
            Integer amount = Integer.valueOf(entry.getValue().getSummary().getAmount());
            if (amount >= WEEK_EARN_LIMIT) {
                Integer salary = Long.valueOf(Math.round(entry.getValue().getSummary().getAmount() * 0.4d)).intValue();
                entry.getValue().getSummary().setSalary(salary);
                Integer salaryWithTips = salary + entry.getValue().getSummary().getTips();
                entry.getValue().getSummary().setSalaryWithTips(salaryWithTips);

                Integer change = entry.getValue().getSummary().getCash() - salary;
                entry.getValue().getSummary().setChange(change);
                Integer changeWithoutTips = change - entry.getValue().getSummary().getTips();
                entry.getValue().getSummary().setChangeWithoutTips(changeWithoutTips);
                entry.getValue().getSummary().setFormula("40%");
                entry.getValue().setRecordList(recalculate40(entry.getValue().getRecordList()));
            } else {
                entry.getValue().getSummary().setFormula("35%");
            }
        }
        return map;
    }

    private List<PaymentView> recalculate40(List<PaymentView> recordList) {
        return recalculate(40, recordList);
    }

    private List<PaymentView> recalculate(int percentage, List<PaymentView> recordList) {
        for (PaymentView paymentView : recordList) {
            Integer salary = Math.toIntExact(Math.round(Integer.parseInt(paymentView.getAmount()) * (percentage / 100.0)));
            Integer cash = Integer.parseInt(paymentView.getCash());
            paymentView.setSalary(salary + "");
            paymentView.setChange((cash - salary) + "");
        }
        return recordList;
    }

    private Map<String, PaymentDriverRecord> makeRateAndSortByRate(Map<String, PaymentDriverRecord> map) {
        TreeMap<Double, PaymentDriverRecord> sortMap = new TreeMap<>();
        for (PaymentDriverRecord paymentDriverRecord : map.values()) {
            //TODO improve to avoid overriting
            sortMap.put(-paymentDriverRecord.getSummary().getUahPerHour()+new Random().nextDouble(), paymentDriverRecord);
        }

        List<PaymentDriverRecord> list = new ArrayList<>(sortMap.values());
        for (PaymentDriverRecord paymentDriverRecord : new ArrayList<>(sortMap.values())) {
            paymentDriverRecord.getSummary().setRate(list.indexOf(paymentDriverRecord));
            System.out.println(paymentDriverRecord.getDriverName());
        }
        list.forEach(System.out::println);
        Map<String, PaymentDriverRecord> linkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<Double, PaymentDriverRecord> entry : sortMap.entrySet()) {
            linkedHashMap.put(entry.getValue().getDriverName(), entry.getValue());
        }

        return linkedHashMap;
    }

    private SummaryPaymentDriverRecord makeSummary(List<PaymentView> recordList) {
        SummaryPaymentDriverRecord summaryPaymentDriverRecord = new SummaryPaymentDriverRecord();

        int count = 0;
        int amount = 0;
        int cash = 0;
        int salary = 0;
        int change = 0;
        int tips = 0;
        int promotion = 0;
        double duration = 0.0;

        for (PaymentView paymentView : recordList) {
            count += Integer.parseInt(paymentView.getCount());
            amount += Integer.parseInt(paymentView.getAmount());
            cash += Integer.parseInt(paymentView.getCash());
            salary += Integer.parseInt(paymentView.getSalary());
            change += Integer.parseInt(paymentView.getChange());
            tips += Integer.parseInt(paymentView.getTips());
            promotion += Integer.parseInt(paymentView.getPromotion());
            duration += Double.parseDouble(paymentView.getDuration());
        }

        summaryPaymentDriverRecord.setCount(count);
        summaryPaymentDriverRecord.setAmount(amount);
        summaryPaymentDriverRecord.setCash(cash);

        summaryPaymentDriverRecord.setTips(tips);
        summaryPaymentDriverRecord.setPromotion(promotion);
        if (duration > 0) {
            summaryPaymentDriverRecord.setDuration(Math.round(duration * 100) / 100.0);
            summaryPaymentDriverRecord.setUahPerHour(Math.round(amount / duration * 100) / 100.0);
        } else {

            summaryPaymentDriverRecord.setDuration(0.0);
            summaryPaymentDriverRecord.setUahPerHour(0.0);
        }
        if (count > 0) {
            summaryPaymentDriverRecord.setUahPerTrip(Math.round(amount / (double) count * 100) / 100.0);
        } else {

            summaryPaymentDriverRecord.setUahPerTrip(0.0);

        }

        summaryPaymentDriverRecord.setSalary(salary);
        summaryPaymentDriverRecord.setSalaryWithTips(salary + tips);
        summaryPaymentDriverRecord.setChange(change);
        summaryPaymentDriverRecord.setChangeWithoutTips(change - tips);
        return summaryPaymentDriverRecord;
    }

    private List<TripView> makeTripList(Map<Date, PaymentRecordRawRow> rangeMap) {
        Map<String, List<PaymentRecordRawRow>> groupByTripMap = new HashMap<>();
        for (PaymentRecordRawRow parsedRow : rangeMap.values()) {
            groupByTripMap.putIfAbsent(parsedRow.getTripUUID(), new ArrayList<>());
            groupByTripMap.get(parsedRow.getTripUUID()).add(parsedRow);
        }

        List<TripView> res = new ArrayList<>();
        for (Map.Entry<String, List<PaymentRecordRawRow>> entry : groupByTripMap.entrySet()) {
            //[16.08.2019 11:38] [39.91] [-12.33] [trip]
            res.add(makeTripView(entry.getKey(), entry.getValue()));
        }
        res.sort(Comparator.comparing(TripView::getDate));

        return res;
    }

    private TripView makeTripView(String id, List<PaymentRecordRawRow> rowList) {
        TripView tripView = new TripView();
        tripView.setId(id);
        if (rowList.size() == 1) {
            tripView.setText(makeSingleUsualText(rowList.get(0)));
            tripView.setDate(rowList.get(0).getTimestamp());
        }
        if (rowList.size() == 2) {
            if (tipAndTrip(rowList)) {
                tripView.setText(makeUsualTipTripText(rowList));
                tripView.setDate(oldestDate(rowList));

            } else {
                tripView.setText(makeCashUsualText(rowList));
                tripView.setDate(oldestDate(rowList));
            }
        }
        if (rowList.size() > 2) {
            tripView.setText(makeUnUsualText(rowList));
            tripView.setDate(oldestDate(rowList));
        }
        return tripView;
    }

    private String makeUsualTipTripText(List<PaymentRecordRawRow> rowList) {
        //[16.08.2019 18:04] [77.41] [tip : 10] [trip]
        PaymentRecordRawRow tipRow;
        PaymentRecordRawRow row;
        if (rowList.get(0).getItemType().equals("tip")) {
            tipRow = rowList.get(0);
            row = rowList.get(1);
        } else {
            tipRow = rowList.get(1);
            row = rowList.get(0);
        }
        return "[" + SDF_DD_MM_YYYY_HH_MM.format(row.getTimestamp()) + "] [" + row.getAmount() + "] [tip : " + tipRow.getAmount() + "]";
    }

    private boolean tipAndTrip(List<PaymentRecordRawRow> rowList) {
        String recordType0 = rowList.get(0).getItemType();
        String recordType1 = rowList.get(1).getItemType();
        return (recordType0.equals("tip") || recordType0.equals("trip")) && (recordType1.equals("tip") || recordType1.equals("trip"));
    }

    private Date oldestDate(List<PaymentRecordRawRow> rowList) {
        Date date = new Date();
        for (PaymentRecordRawRow row : rowList) {
            Date currentDate = row.getTimestamp();
            if (date.getTime() > currentDate.getTime()) {
                date = currentDate;
            }
        }
        return date;
    }

    private String makeCashUsualText(List<PaymentRecordRawRow> rowList) {
        //[16.08.2019 18:04] [77.41] [-103.22] [trip]
        PaymentRecordRawRow cashRow;
        PaymentRecordRawRow row;
        if (rowList.get(0).getItemType().equals("cash_collected")) {
            cashRow = rowList.get(0);
            row = rowList.get(1);
        } else {
            cashRow = rowList.get(1);
            row = rowList.get(0);
        }
        return "[" + SDF_DD_MM_YYYY_HH_MM.format(row.getTimestamp()) + "] [" + row.getAmount() + "] [cash : " + cashRow.getAmount() + "]";
    }

    private String makeSingleUsualText(PaymentRecordRawRow row) {
        //[16.08.2019 11:38] [39.91] [trip]
        if (row.getItemType().equals("trip")) {
            return "[" + SDF_DD_MM_YYYY_HH_MM.format(row.getTimestamp()) + "] [" + row.getAmount() + "]";
        } else {
            return makeUnUsualText(new ArrayList<>(Collections.singletonList(row)));
        }
    }

    private Date parseRowDate(String[] rowArray) {
        Date res = null;
        String timestamp = rowArray[5];
        try {
            res = SDF.parse(timestamp.split("\\+")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    private String makeUnUsualText(List<PaymentRecordRawRow> rowList) {
        StringBuilder text = new StringBuilder();
        for (PaymentRecordRawRow row : rowList) {
            try {
                text.append("[")
                        .append(SDF_DD_MM_YYYY_HH_MM.format(row.getTimestamp())).append("] ").append("[")
                        .append(row.getAmount()).append("] ").append("[")
                        .append(row.getItemType()).append("] ").append("[")
                        .append(row.getDescription()).append("] ").append("[")
                        .append(row.getDisclaimer()).append("]");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(row);
            }

        }
        return text.toString();
    }

    Map<String, Map<Date, PaymentRecordRawRow>> primaryParsedData;


    public Map<String, PaymentOwnerRecord> makeOwnerMap() {
        Map<String, PaymentOwnerRecord> map = new HashMap<>();
        for (String driver : primaryParsedData.keySet()) {
            UberDriver currentDriver = driverByName(driver, driverList);
            if (currentDriver != null && currentDriver.getDriverType().startsWith("owner")) {
                map.putIfAbsent(driver, new PaymentOwnerRecord());
                Map<Date, PaymentRecordRawRow> rangeMap = getRangeMap(primaryParsedData.get(driver));
                Date start = rangeMap.keySet().stream().min(Date::compareTo).orElse(new Date());
                Date end = rangeMap.keySet().stream().max(Date::compareTo).orElse(new Date());
                map.get(driver).setDriverName(driver);

                Long cash = getCash(rangeMap);
                // готівка

                Long amount = getAmountForOwner(rangeMap);
                // дохід

                Integer count = getTripCount(rangeMap);

                Long tips = getTips(rangeMap);

                Long promotion = getPromotion(rangeMap);

                OwnerPaymentView ownerPaymentView = new OwnerPaymentView(count, amount.intValue(), cash.intValue());
                ownerPaymentView.setTips(tips.intValue());
                ownerPaymentView.setPromotion(promotion.intValue());

                ownerPaymentView.setTripListId("tripListId_" + UUID.randomUUID().toString());
                ownerPaymentView.setTripList(makeTripList(rangeMap));


                Integer taxPercentage = Integer.parseInt(currentDriver.getDriverType().split("_")[1]);
                ownerPaymentView.setTaxPercentage(taxPercentage.doubleValue());
                //відсоток комісії

                String dateRangeName = SDF_DAY_YEAR.format(start) + "-" + SDF_DAY_YEAR.format(end);
                ownerPaymentView.setDateRangeName(dateRangeName + "");
                //дата

                Long amountMinusCommission = Math.round(amount * ((100 - taxPercentage) / 100d));
                ownerPaymentView.setAmountMinusCommission(amountMinusCommission.intValue());
                // чистий дохід (без комісії)

                Long commission = Math.round(amount * ((taxPercentage) / 100d));
                ownerPaymentView.setCommission(commission.intValue());
                // комісія

                Long nonCash = amount - cash;
                ownerPaymentView.setNonCash(nonCash.intValue());
                // безготівка

                Long withdraw = nonCash - commission;
                ownerPaymentView.setWithdraw(withdraw.intValue());
                // на виведення

                map.get(driver).setOwnerPaymentViews(ownerPaymentView);
            }
        }
        paymentOwnerRecordMap = map;
        return map;
    }

    private Long getAmountForOwner(Map<Date, PaymentRecordRawRow> rangeMap) {
        return collectAmount(rangeMap.values(), "trip")
                + collectAmount(rangeMap.values(), "tip")
                + collectAmount(rangeMap.values(), "promotion");
    }

    private UberDriver driverByName(String driver, List<UberDriver> driverList) {
        for (UberDriver uberDriver : driverList) {
            if (uberDriver.getName().equals(driver)) {
                return uberDriver;
            }
        }
        return null;
    }


    public GeneralPartnerSummary makeGeneralPartnerSummary() {
        GeneralPartnerSummary res = new GeneralPartnerSummary();
        Double profit = makeGeneralPartnerSummaryNonCash();
        Double cash = makeGeneralPartnerSummaryCash();
        double transfer = profit - cash;
        double tax = transfer * 0.05;
        double withdraw = collectAllWithdraws();
        double realProfit = profit - tax - withdraw;
        res.setProfit(Math.round(profit) + "");
        res.setCash(Math.round(cash) + "");
        res.setTransfer(Math.round(transfer) + "");
        res.setTax(Math.round(tax) + "");
        res.setWithdraw(Math.round(withdraw) + "");
        res.setRealProfit(Math.round(realProfit) + "");
        return res;
    }

    private double collectAllWithdraws() {
        double res = 0d;
        if (paymentDriverRecordMap == null) {
            makeMap();
        }
        for (PaymentDriverRecord paymentDriverRecord : paymentDriverRecordMap.values()) {
            res += paymentDriverRecord.getSummary().getSalaryWithTips();
        }

        if (paymentOwnerRecordMap == null) {
            makeOwnerMap();
        }
        for (PaymentOwnerRecord paymentOwnerRecord : paymentOwnerRecordMap.values()) {
            res += paymentOwnerRecord.getOwnerPaymentViews().getWithdraw();
            res += paymentOwnerRecord.getOwnerPaymentViews().getCash();
        }
        return res;
    }

    private Double makeGeneralPartnerSummaryCash() {
        Double amount = 0d;
        for (Map<Date, PaymentRecordRawRow> datedMap : primaryParsedData.values()) {
            for (PaymentRecordRawRow paymentRecordRawRow : datedMap.values()) {
                UberDriver currentDriver = driverByName(paymentRecordRawRow.driverName(), driverList);
                if (currentDriver != null && !currentDriver.getDriverType().equals("partner")) {
                    if (paymentRecordRawRow.getItemType().equals("cash_collected")) {
                        amount += paymentRecordRawRow.getAmount();
                    }
                }
            }
        }
        return -amount;
    }

    private Double makeGeneralPartnerSummaryNonCash() {
        Double amount = 0d;
        for (Map<Date, PaymentRecordRawRow> datedMap : primaryParsedData.values()) {
            for (PaymentRecordRawRow paymentRecordRawRow : datedMap.values()) {
                UberDriver currentDriver = driverByName(paymentRecordRawRow.driverName(), driverList);
                if (currentDriver != null && !currentDriver.getDriverType().equals("partner")) {
                    if (!paymentRecordRawRow.getItemType().equals("cash_collected")) {
                        amount += paymentRecordRawRow.getAmount();
                    }
                }
                if (currentDriver != null && currentDriver.getDriverType().equals("partner")) {
                    if (paymentRecordRawRow.getItemType().equals("promotion")) {
                        amount += paymentRecordRawRow.getAmount();
                    }
                }
            }
        }
        return amount;
    }

}
