package server.logan.park;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import server.BaseController;
import server.logan.park.view.PaymentView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LoganPark extends BaseController {
    private static final String ENDPOINT = "/logan_park";

    @RequestMapping(value = ENDPOINT)
    public String getLoganPark(HttpServletRequest request) {
        if (request.getSession(true).getAttribute("paymentView") == null) {
            request.getSession(true).setAttribute("paymentView", new PaymentView());
        }
        return "logan_park";
    }

    @RequestMapping(value = ENDPOINT + "/upload_payment_csv")
    public String uploadPaymentCSV(HttpServletRequest request, @RequestParam("payment_file") MultipartFile file) {
        String content = "NO_content";
        try {
            content = new String(file.getBytes(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PaymentView paymentView = new PaymentView(content);
        paymentView.setName(file.getOriginalFilename());
        paymentView.setReport(new String(makeReport(content).getBytes(), Charset.forName("UTF-8")));
        System.out.println(paymentView.getReport());
        paymentView.setContent(content);
        request.getSession(true).setAttribute("paymentView", paymentView);
        return "redirect:" + ENDPOINT;
    }


    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private String makeReport(String content) {
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
                for (List<Date> range : dateRange) {

                    Date start = range.get(0);
                    Date end = range.get(1);
                    System.out.println(normalizeUTF_UTF(driver) + " " +dateRange);
                    String workoutName = normalizeUTF_UTF(driver) + " " + dayLabel(start, end);

                    Map<Date, List<Object>> rangeMap = getRangeMap(start, end, driverMap.get(driver));

                    Long cash = getCash(rangeMap);
                    Long amount = getAmount(rangeMap);
                    Long sallary = Math.round(amount.doubleValue() * 0.35);
                    stringBuilder
                            .append("<tr>")
                            .append("<td>")
                            .append(workoutName)
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

    private String normalizeUTF_UTF(String driver) {
        try {
            return new String(driver.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Long getAmount(Map<Date, List<Object>> rangeMap) {
        return collectAmount(rangeMap.values(), "trip");
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
        currentRange.add(end);
        res.add(currentRange);
        return res;
    }
}