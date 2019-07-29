package server.logan.park.service;

import server.logan.park.view.PaymentView;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentTabHelper {

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
            }
        }
        return count;
    }

//    private String normalizeUTF_UTF(String string) {
//        return new String(string.getBytes(StandardCharsets.UTF_8));
//    }


    private Long getAmount(Map<Date, List<Object>> rangeMap) {
        //1835
        return collectAmount(rangeMap.values(), "trip")
                + collectAmount(rangeMap.values(), "tip")
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

    private static final SimpleDateFormat SDF_DAY = new SimpleDateFormat("dd.MM");
    private static final SimpleDateFormat SDF_DAY_YEAR = new SimpleDateFormat("dd.MM.yyyy");

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
                if (dateRange != null && dateRange.size() >0) {
                    for (List<Date> range : dateRange) {
                        Date start = range.get(0);
                        Date end = range.get(1);
                        String workoutName = dayLabel(start, end);
                        Map<Date, List<Object>> rangeMap = getRangeMap(start, end, driverMap.get(driver));
                        Integer count = getTripCount(rangeMap);
                        Long cash = getCash(rangeMap);
                        Long amount = getAmount(rangeMap);
                        Long salary = Math.round(amount.doubleValue() * 0.35);
                        map.get(driver).add(new PaymentView(workoutName, count + "", amount + "", cash + "", salary + "", (cash - salary) + ""));
                    }
                }
            }
        }
        return map;
    }

    private Map<String, Map<Date, List<Object>>> parsePrimaryData() {
        Map<String, Map<Date, List<Object>>> driverMap = new HashMap<>();
        boolean firstRow = true;
        for (String row : content.split("\n")) {
            if (firstRow) {
                firstRow = false;
            } else {
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
                driverMap.get(driverName).put(date, Arrays.asList(amount, itemType));
            }
        }
        return driverMap;
    }
}
