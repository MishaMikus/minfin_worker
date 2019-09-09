package server.logan_park.helper;

import server.logan_park.service.*;
import java.util.*;

public class ManuallyWeeklyReportHelper extends CommonWeeklyReportHelper{
    public ManuallyWeeklyReportHelper(String content) {
        super(content);
    }

    Map<String, Map<Date, PaymentRecordRawRow>> parsePrimaryData() {
        Map<String, Map<Date, PaymentRecordRawRow>> driverMap = new HashMap<>();
        boolean firstRow = true;
        int i = 0;
        for (String row : content.split("\n")) {
            if (firstRow) {
                firstRow = false;
            } else {
                PaymentRecordRawRow paymentRecordRawRow = PaymentRecordRawRow.makeMeFromStringRow(row, i++);
                driverMap.putIfAbsent(paymentRecordRawRow.driverName(), new HashMap<>());
                driverMap.get(paymentRecordRawRow.driverName()).put(
                        new Date(paymentRecordRawRow.getTimestamp().getTime() + i),
                        paymentRecordRawRow);
            }
        }
        primaryParsedData = driverMap;
        return getPrimaryParsedData();
    }

}

