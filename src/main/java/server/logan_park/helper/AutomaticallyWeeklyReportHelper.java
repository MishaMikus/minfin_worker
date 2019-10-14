package server.logan_park.helper;

import org.apache.log4j.Logger;
import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import orm.entity.uber.payment_record_row.UberPaymentRecordRow;
import orm.entity.uber.payment_record_row.UberPaymentRecordRowDAO;
import server.logan_park.service.PaymentRecordRawRow;

import java.util.*;

public class AutomaticallyWeeklyReportHelper extends CommonWeeklyReportHelper {
    private final static Logger LOGGER = Logger.getLogger(AutomaticallyWeeklyReportHelper.class);

    public AutomaticallyWeeklyReportHelper(Date weekFlag) {
        super(weekFlag);
    }

    public AutomaticallyWeeklyReportHelper() {
        super(new Date());
    }

    Map<String, Map<Date, PaymentRecordRawRow>> parsePrimaryData(Date weekFlag) {
        WeekRange weekRange = WeekRangeDAO.getInstance().findOrCreateWeek(weekFlag);
        LOGGER.info("find all records for week : " + weekRange);
        List<UberPaymentRecordRow> dbRowList = UberPaymentRecordRowDAO.getInstance()
                .findAllWhereEqual("week_id", weekRange.getId());
        LOGGER.info("find all records for weekHash dbRowList.size : " + dbRowList.size());
        Map<String, Map<Date, PaymentRecordRawRow>> driverMap = new HashMap<>();
        int i = 0;
        for (UberPaymentRecordRow row : dbRowList) {
            PaymentRecordRawRow paymentRecordRawRow = PaymentRecordRawRow.makeMeFromDBRow(row, driverList);
            driverMap.putIfAbsent(paymentRecordRawRow.driverName(), new HashMap<>());
            if (paymentRecordRawRow.getTimestamp() != null) {
                driverMap.get(paymentRecordRawRow.driverName()).put(
                        new Date(paymentRecordRawRow.getTimestamp().getTime() + i++),
                        paymentRecordRawRow);
            }
        }
        primaryParsedData = driverMap;
        LOGGER.info("primaryParsedData.size : " + primaryParsedData.size());
        return getPrimaryParsedData();
    }

}
