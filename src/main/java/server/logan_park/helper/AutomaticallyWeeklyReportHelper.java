package server.logan_park.helper;

import org.apache.log4j.Logger;
import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import orm.entity.uber.payment_record_row.UberPaymentRecordRow;
import orm.entity.uber.payment_record_row.UberPaymentRecordRowDAO;
import server.logan_park.service.PaymentRecordRawRow;
import server.logan_park.view.weekly_report_general.WeekLinksHelper;
import server.logan_park.view.weekly_report_uber.model.AutomaticallyWeeklyUberReport;

import java.util.*;

public class AutomaticallyWeeklyReportHelper extends CommonWeeklyReportHelper {
    private final static Logger LOGGER = Logger.getLogger(AutomaticallyWeeklyReportHelper.class);

    public AutomaticallyWeeklyReportHelper(String content, Date weekFlag) {
        super(content, weekFlag);
    }
    public AutomaticallyWeeklyReportHelper(Date weekFlag) {
        super(weekFlag);
    }

    Map<String, Map<Date, PaymentRecordRawRow>> parsePrimaryData(Date weekFlag) {
        WeekRange weekRange = WeekRangeDAO.getInstance().findOrCreateWeek(weekFlag, "ubet_worker");
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

    public AutomaticallyWeeklyUberReport makeReport() {
        AutomaticallyWeeklyUberReport automaticallyWeeklyUberReport = new AutomaticallyWeeklyUberReport();
        automaticallyWeeklyUberReport.setWeekLinksList(new WeekLinksHelper().linkList());
        automaticallyWeeklyUberReport.setPaymentTable(makeMap());
        automaticallyWeeklyUberReport.setOwnerTable(makeOwnerMap());
        automaticallyWeeklyUberReport.setGeneralPartnerSummary(makeGeneralPartnerSummary());
        return automaticallyWeeklyUberReport;
    }
}
