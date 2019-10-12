package server.logan_park.helper;

import org.apache.log4j.Logger;
import orm.entity.uber.payment_record_row.UberPaymentRecordRow;
import orm.entity.uber.payment_record_row.UberPaymentRecordRowDAO;
import server.logan_park.service.PaymentRecordRawRow;

import java.util.*;

public class AutomaticallyWeeklyReportHelper extends CommonWeeklyReportHelper {
    private final static Logger LOGGER = Logger.getLogger(AutomaticallyWeeklyReportHelper.class);

    public AutomaticallyWeeklyReportHelper() {
        super();
    }

//    public static void main(String[] args) {
//        AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper=new AutomaticallyWeeklyReportHelper();
//        System.out.println(automaticallyWeeklyReportHelper.makeMap());
//        System.out.println(automaticallyWeeklyReportHelper.makeOwnerMap());
//        System.out.println(automaticallyWeeklyReportHelper.makeGeneralPartnerSummary());
//    }

    Map<String, Map<Date, PaymentRecordRawRow>> parsePrimaryData() {
        Integer weekHash = getLatestHash();
        LOGGER.info("find all records for weekHash : " + weekHash);
        List<UberPaymentRecordRow> dbRowList = weekHash == null ? new ArrayList<>() : UberPaymentRecordRowDAO.getInstance()
                .findAllWhereEqual("weekHash", weekHash);
        LOGGER.info("find all records for weekHash dbRowList.size : " + dbRowList.size());
        Map<String, Map<Date, PaymentRecordRawRow>> driverMap = new HashMap<>();
        int i = 0;
        for (UberPaymentRecordRow row : dbRowList) {
            PaymentRecordRawRow paymentRecordRawRow = PaymentRecordRawRow.makeMeFromDBRow(row,driverList);
            driverMap.putIfAbsent(paymentRecordRawRow.driverName(), new HashMap<>());
            driverMap.get(paymentRecordRawRow.driverName()).put(
                    new Date(paymentRecordRawRow.getTimestamp().getTime() + i),
                    paymentRecordRawRow);
        }
        primaryParsedData = driverMap;
        LOGGER.info("primaryParsedData.size : " + primaryParsedData.size());
        return getPrimaryParsedData();
    }

}
