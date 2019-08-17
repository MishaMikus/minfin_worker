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
            } else {
                LOGGER.info("It is not a trip : " + trip);
            }
        }
        return count;
    }


    private Long getAmount(Map<Date, List<Object>> rangeMap) {
        //1835
        return collectAmount(rangeMap.values(), "trip")
                + collectAmount(rangeMap.values(), "tip")
                + collectAmount(rangeMap.values(), "promotion")
                ;
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
        if (dateListMap.size() < 2) {
            return null;
        }

        List<Date> daleList = new ArrayList<>(dateListMap.keySet());
        Collections.sort(daleList);
        Date start = daleList.get(0);
        Date end = daleList.get(0);
        List<List<Date>> res = new ArrayList<>();
        for (Date date : daleList) {
            int diff = end == null ? 0 : (int) (((date.getTime() - end.getTime()) / 1000) / 60) / 60;
            if (diff > 2) {
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

    public Map<String, List<PaymentView>> makeMap() {
        Map<String, List<PaymentView>> map = new HashMap<>();
        Map<String, Map<Date, List<Object>>> driverMap = parsePrimaryData();
        for (String driver : driverMap.keySet()) {
            if (!EXCLUDE_DRIVER_LIST.contains(driver)) {
                map.putIfAbsent(driver, new ArrayList<>());
                List<List<Date>> dateRange = calculateDateRange(driverMap.get(driver));
                if (dateRange != null && dateRange.size() > 0) {
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
                        map.get(driver).add(paymentView);
                    }
                }
            }
        }
        return map;
    }

    private List<TripView> makeTripList(Map<Date, List<Object>> rangeMap) {
        //"driverUUID","tripUUID","firstName","lastName","amount","timestamp","itemType","description","disclaimer"
        //"6dfce16e-9334-4091-96a8-77d3508eaab5","7c27595e-e7b3-4960-8510-86546ee3cc8e","Ростислав","Петрик","45.30","2019-08-15T06:23:29+03:00","trip","UberX",""
        //"ecc36425-0762-49e7-a6e7-f1a3c56eab60","","Тарас","Семеряк","-25.48","2019-08-15T15:05:45+03:00","promotion","Промокод","Support Adjustment: 4ac9381d-d68f-4766-a5ce-77d8f6a3bf8e"
        //[tripUUID url] {[date] [amount] [type] [description] [disclaimer] .. }

        //https://partners.uber.com/p3/payments/trips/dba393d4-4991-46fc-bae7-81acca1a84c6
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
            tripView.setText(makeCashUsualText(rowList));
            tripView.setDate(parseOldestDate(rowList));
        }
        if (rowList.size() > 2) {
            tripView.setText(makeUnUsualText(rowList));
            tripView.setDate(parseOldestDate(rowList));
        }
        return tripView;
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
        return "[" + SDF_DD_MM_YYYY_HH_MM.format(date) + "] [" + rowArray[4] + "] [" + cashRowArray[4] + "] [" + rowArray[6] + "]";
    }

    private String makeSingleUsualText(String row) {
        //[16.08.2019 11:38] [39.91] [trip]
        String[] rowArray = row.split(",");
        Date date = parseRowDate(rowArray);
        return "[" + SDF_DD_MM_YYYY_HH_MM.format(date) + "] [" + rowArray[4] + "] [" + rowArray[6] + "]";
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
        for (String row : content.split("\n")) {
            if (firstRow) {
                firstRow = false;
            } else {
                LOGGER.info(row);
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
                driverMap.get(driverName).put(date, Arrays.asList(amount, itemType, row));
            }
        }
        return driverMap;
    }
}

