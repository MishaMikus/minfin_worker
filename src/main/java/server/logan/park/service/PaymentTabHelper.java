package server.logan.park.service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentTabHelper {

    public String makeAllDataTable(String content) {
        return null;
    }


    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public String makeReport(String content) {
        boolean firstRow = true;
        Map<String, Map<Date, List<Object>>> driverMap = new HashMap<>();
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

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("<table border=1>")
                .append("<tr>" +

                        "<th>")
                .append(normalizeUTF_UTF("зміна"))
                .append("</th>" +

                        "<th>")
                .append(normalizeUTF_UTF("поїздок"))
                .append("</th>" +

                        "<th>")
                .append(normalizeUTF_UTF("загалом"))
                .append("</th>" +

                        "<th>")
                .append(normalizeUTF_UTF("готівка"))
                .append("</th>" +

                        "<th>")
                .append(normalizeUTF_UTF("зарплата"))
                .append("</th>" +

                        "<th>")
                .append(normalizeUTF_UTF("решту від готівки"))
                .append("</th>" +

                        "</tr>");
        for (String driver : driverMap.keySet()) {

            List<List<Date>> dateRange = calculateDateRange(driverMap.get(driver));
            if (dateRange != null && dateRange.size() > 1) {
                stringBuilder
                        .append("<tr>")
                        .append("<td colspan=6>")
                        .append(normalizeUTF_UTF(driver))
                        .append("</td>")
                        .append("</tr>");
                for (List<Date> range : dateRange) {

                    Date start = range.get(0);
                    Date end = range.get(1);
                    String workoutName = dayLabel(start, end);

                    Map<Date, List<Object>> rangeMap = getRangeMap(start, end, driverMap.get(driver));
                    Integer count = getTripCount(rangeMap);
                    Long cash = getCash(rangeMap);
                    Long amount = getAmount(rangeMap);
                    Long sallary = Math.round(amount.doubleValue() * 0.35);
                    stringBuilder
                            .append("<tr>")
                            .append("<td>")
                            .append(workoutName)
                            .append("</td><td>")

                            .append(" ")
                            .append(count)
                            .append("</td><td>")

                            .append(" ")
                            .append(amount)
                            .append("</td><td>")

                            .append(" ")
                            .append(cash)
                            .append("</td><td>")

                            .append(" ")
                            .append(sallary)
                            .append("</td><td>")

                            .append(" ")
                            .append(cash - sallary)
                            .append("</td>")

                            .append("</tr>");
                }
            }
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

    private Integer getTripCount(Map<Date, List<Object>> rangeMap) {
        Integer count = 0;
        for (List<Object> trip : rangeMap.values()) {

            if ("trip".equals(trip.get(1))) {
                count++;
            }
        }
        return count;
    }

    private String normalizeUTF_UTF(String driver) {
        return new String(driver.getBytes(StandardCharsets.UTF_8));
    }


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
            return normalizeUTF_UTF("ніч з") + " " + SDF_DAY.format(start) + " " + normalizeUTF_UTF("по") + " " + SDF_DAY.format(end);
        } else return normalizeUTF_UTF("день") + " " + SDF_DAY_YEAR.format(start);
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
        if (dateListMap.size() < 2) return null;
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
}
