package server.logan.park.service;

import org.apache.log4j.Logger;
import server.logan.park.view.PaymentView;
import server.logan.park.view.TripView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentTabHelper {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private String content;

    private static final int WORKOUT_HOUR_DIFF=6;

    public PaymentTabHelper(String content) {
        this.content = content;
    }

    private static final List<String> EXCLUDE_DRIVER_LIST = new ArrayList<>(Arrays.asList(
            "Мар'ян_Торган",
            "Богдан_Мікусь",
            "Михайло_Мікусь"));

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private Integer getTripCount(Map<Date, List<Object>> rangeMap) {
        Integer count = 0;
        for (List<Object> trip : rangeMap.values()) {
            if ("trip".equals(trip.get(1))) {
                count++;
            }
        }
        return count;
    }


    private Long getAmount(Map<Date, List<Object>> rangeMap) {
        return collectAmount(rangeMap.values(), "trip")
                // + collectAmount(rangeMap.values(), "tip")
                + collectAmount(rangeMap.values(), "promotion");
    }

    private Long collectAmount(Collection<List<Object>> values, String key) {
        Double amount = 0d;
        for (List<Object> trip : values) {
            if (key.equals(trip.get(1))) {
                amount += Double.parseDouble(trip.get(0).toString());
            }
        }
        return Math.round(amount);
    }

    private Long getCash(Map<Date, List<Object>> rangeMap) {
        return -collectAmount(rangeMap.values(), "cash_collected");
    }

    private Long getTips(Map<Date, List<Object>> rangeMap) {
        return collectAmount(rangeMap.values(), "tip");
    }

    private Long getPromotion(Map<Date, List<Object>> rangeMap) {
        return collectAmount(rangeMap.values(), "promotion");
    }

    private static final SimpleDateFormat SDF_DAY = new SimpleDateFormat("dd.MM");
    private static final SimpleDateFormat SDF_DAY_YEAR = new SimpleDateFormat("dd.MM.yyyy");

    private static final SimpleDateFormat SDF_DD_MM_YYYY_HH_MM = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private String dayLabel(Date start, Date end) {
        if (start.getTime() / 1000 / 60 / 60 / 24 != end.getTime() / 1000 / 60 / 60 / 24) {
            return "ніч з " + SDF_DAY.format(start) + " по " + SDF_DAY.format(end);
        } else return "день " + SDF_DAY_YEAR.format(start);
    }

    private Map<Date, List<Object>> getRangeMap(Date start, Date end, Map<Date, List<Object>> dateListMap) {
        Map<Date, List<Object>> res = new HashMap<>();
        for (Date date : dateListMap.keySet()) {
            if (date.getTime() >= start.getTime() && date.getTime() <= end.getTime()) {
                res.putIfAbsent(date, dateListMap.get(date));
            }
        }
        return res;
    }

    private List<List<Date>> calculateDateRange(Map<Date, List<Object>> dateListMap) {
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

        //"ac67df0b-abbb-4d15-876d-6cc76f95b7c3","","Юрий","Сосинский","18.0","2019-08-16T14:19:45+03:00","promotion","Промокод","Компенсація сервісного збору Убер"
        Map<String, PaymentDriverRecord> map = new HashMap<>();
        Map<String, Map<Date, List<Object>>> driverMap = parsePrimaryData();
        for (String driver : driverMap.keySet()) {
            if (!EXCLUDE_DRIVER_LIST.contains(driver)) {
                map.putIfAbsent(driver, new PaymentDriverRecord());
                List<List<Date>> dateRange = calculateDateRange(driverMap.get(driver));
                if (dateRange.size() > 0) {
                    for (List<Date> range : dateRange) {
                        Date start = range.get(0);
                        Date end = range.get(1);
                        String workoutName = dayLabel(start, end);
                        Map<Date, List<Object>> rangeMap = getRangeMap(start, end, driverMap.get(driver));
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
        map=makeRateAndSortByRate(map);
        return map;
    }

    private Map<String, PaymentDriverRecord> makeRateAndSortByRate(Map<String, PaymentDriverRecord> map) {
        TreeMap<Double,PaymentDriverRecord> sortMap=new TreeMap<>();
        for(PaymentDriverRecord paymentDriverRecord:map.values()){
            sortMap.put(-Double.parseDouble(paymentDriverRecord.getSummary().getUahPerHour()),paymentDriverRecord);
        }

        List<PaymentDriverRecord> list=new ArrayList<>(sortMap.values());
        for(PaymentDriverRecord paymentDriverRecord:new ArrayList<>(sortMap.values())){
            paymentDriverRecord.getSummary().setRate(list.indexOf(paymentDriverRecord)+"");
        }

        Map<String, PaymentDriverRecord> linkedHashMap=new LinkedHashMap<>();
        for(Map.Entry<Double,PaymentDriverRecord> entry : sortMap.entrySet()){
            linkedHashMap.put(entry.getValue().getDriverName(),entry.getValue());
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

        summaryPaymentDriverRecord.setCount(count + "");
        summaryPaymentDriverRecord.setAmount(amount + "");
        summaryPaymentDriverRecord.setCash(cash + "");
        summaryPaymentDriverRecord.setSalary(salary + "");
        summaryPaymentDriverRecord.setChange(change + "");
        summaryPaymentDriverRecord.setChangeWithoutTips((change-tips)+"");
        summaryPaymentDriverRecord.setTips(tips + "");
        summaryPaymentDriverRecord.setPromotion(promotion + "");
        summaryPaymentDriverRecord.setDuration(Math.round(duration * 100) / 100.0 + "");
        summaryPaymentDriverRecord.setSalaryWithTips((salary + tips) + "");
        summaryPaymentDriverRecord.setUahPerHour(Math.round(amount / duration * 100) / 100.0 + "");
        summaryPaymentDriverRecord.setUahPerTrip(Math.round(amount / (double) count * 100) / 100.0 + "");

        return summaryPaymentDriverRecord;
    }

    private List<TripView> makeTripList(Map<Date, List<Object>> rangeMap) {

        Map<String, List<String>> groupByTripMap = new HashMap<>();
        for (List<Object> parsedRow : rangeMap.values()) {
            String row = parsedRow.get(2) + "";
            String tripUUID = row.split(",")[1];
            if (tripUUID.trim().isEmpty()) {
                tripUUID = "NO_TRIP_ID";
            }

            groupByTripMap.putIfAbsent(tripUUID, new ArrayList<>());
            groupByTripMap.get(tripUUID).add(row);
        }

        List<TripView> res = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : groupByTripMap.entrySet()) {
            //[16.08.2019 11:38] [39.91] [-12.33] [trip]
            res.add(makeTripView(entry.getKey(), entry.getValue()));
        }
        res.sort(Comparator.comparing(TripView::getDate));

        return res;
    }

    private TripView makeTripView(String id, List<String> rowList) {
        TripView tripView = new TripView();
        tripView.setId(id);
        if (rowList.size() == 1) {
            tripView.setText(makeSingleUsualText(rowList.get(0)));
            tripView.setDate(parseRowDate(rowList.get(0).split(",")));
        }
        if (rowList.size() == 2) {
            if (tipAndTrip(rowList)) {
                tripView.setText(makeUsualTipTripText(rowList));
                tripView.setDate(parseOldestDate(rowList));

            } else {
                tripView.setText(makeCashUsualText(rowList));
                tripView.setDate(parseOldestDate(rowList));
            }
        }
        if (rowList.size() > 2) {
            tripView.setText(makeUnUsualText(rowList));
            tripView.setDate(parseOldestDate(rowList));
        }
        return tripView;
    }

    private String makeUsualTipTripText(List<String> rowList) {
        //[16.08.2019 18:04] [77.41] [tip : 10] [trip]
        String tipRow;
        String row;
        if (rowList.get(0).split(",")[6].equals("tip")) {
            tipRow = rowList.get(0);
            row = rowList.get(1);
        } else {
            tipRow = rowList.get(1);
            row = rowList.get(0);
        }
        String[] rowArray = row.split(",");
        String[] tipRowArray = tipRow.split(",");
        Date date = parseRowDate(rowArray);
        return "[" + SDF_DD_MM_YYYY_HH_MM.format(date) + "] [" + rowArray[4] + "] [tip : " + tipRowArray[4] + "]";
    }

    private boolean tipAndTrip(List<String> rowList) {
        String recordType0 = rowList.get(0).split(",")[6];
        String recordType1 = rowList.get(1).split(",")[6];
        return (recordType0.equals("tip") || recordType0.equals("trip")) && (recordType1.equals("tip") || recordType1.equals("trip"));
    }

    private Date parseOldestDate(List<String> rowList) {
        Date date = new Date();
        for (String row : rowList) {
            Date currentDate = parseRowDate(row.split(","));
            if (date.getTime() > currentDate.getTime()) {
                date = currentDate;
            }
        }
        return date;
    }

    private String makeCashUsualText(List<String> rowList) {
        //[16.08.2019 18:04] [77.41] [-103.22] [trip]
        String cashRow;
        String row;
        if (rowList.get(0).split(",")[6].equals("cash_collected")) {
            cashRow = rowList.get(0);
            row = rowList.get(1);
        } else {
            cashRow = rowList.get(1);
            row = rowList.get(0);
        }
        String[] rowArray = row.split(",");
        String[] cashRowArray = cashRow.split(",");
        Date date = parseRowDate(rowArray);
        return "[" + SDF_DD_MM_YYYY_HH_MM.format(date) + "] [" + rowArray[4] + "] [cash : " + cashRowArray[4] + "]";
    }

    private String makeSingleUsualText(String row) {
        //[16.08.2019 11:38] [39.91] [trip]
        String[] rowArray = row.split(",");
        Date date = parseRowDate(rowArray);
        String type = rowArray[6];
        if (rowArray[6].equals("trip")) {
            return "[" + SDF_DD_MM_YYYY_HH_MM.format(date) + "] [" + rowArray[4] + "]";
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

    private String makeUnUsualText(List<String> rowList) {
        StringBuilder text = new StringBuilder();
        for (String row : rowList) {
            String[] rowArray = row.split(",");
            Date date = parseRowDate(rowArray);
            try {
                String disclaimer = "";
                if (rowArray.length == 9) {
                    disclaimer = rowArray[8];
                }
                text.append("[")
                        .append(SDF_DD_MM_YYYY_HH_MM.format(date)).append("] ").append("[")
                        .append(rowArray[4]).append("] ").append("[")
                        .append(rowArray[6]).append("] ").append("[")
                        .append(rowArray[7]).append("] ").append("[")
                        .append(disclaimer).append("]");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(row);
            }

        }
        return text.toString();
    }

    private Map<String, Map<Date, List<Object>>> parsePrimaryData() {
        Map<String, Map<Date, List<Object>>> driverMap = new HashMap<>();
        boolean firstRow = true;
        int i = 0;
        for (String row : content.split("\n")) {
            if (firstRow) {
                firstRow = false;
            } else {
                LOGGER.info("row[" + i + "]:" + (i++) + " " + row);
                row = row.replaceAll("\"", "");
                String[] rowArray = row.split(",");
                String timestamp = rowArray[5];
                String driverName = rowArray[2] + "_" + rowArray[3];
                String amount = rowArray[4];
                String itemType = rowArray[6];

                //2019-07-24T11:08:21+03:00
                Date date = null;
                try {
                    date = SDF.parse(timestamp.split("\\+")[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                driverMap.putIfAbsent(driverName, new HashMap<>());
                assert date != null;
                driverMap.get(driverName).put(new Date(date.getTime() + i), Arrays.asList(amount, itemType, row));
            }
        }

        return driverMap;
    }
}

