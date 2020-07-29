package ui_automation.bolt;

import com.codeborne.selenide.WebDriverRunner;
import orm.entity.bolt.payment_record_day.BoltPaymentRecordDayDAO;
import util.ApplicationPropertyUtil;

import java.io.File;
import java.util.Map;

public class BoltWorker {

    public static void main(String[] args) {
        try {
            runWorker();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void runWorker() throws InterruptedException {
            String login = ApplicationPropertyUtil.applicationPropertyGet("bolt.login");
            String pass = ApplicationPropertyUtil.applicationPropertyGet("bolt.pass");
            new BoltLogonBo().login(login, pass);
            DayReportBO dayReportBO = new DayReportBO();
            Map<String, File> dayMap = dayReportBO.downloadAllNewCSV(BoltPaymentRecordDayDAO.getInstance().latestDate());

            String pageSource = dayReportBO.monthTripPageSource();
            RecordHelper recordHelper = new RecordHelper();
            recordHelper.recordDayReportToDB(dayMap);

            //TODO
            //File monthTripCsv = dayReportBO.downloadMonthTripCsv();
            //recordHelper.recordMonthTripToDB(monthTripCsv);

            recordHelper.recordMonthTripPDFMapping(pageSource);
            WebDriverRunner.getWebDriver().close();
    }
}
