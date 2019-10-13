package ui_automation.bolt;

import orm.entity.bolt.BoltPaymentRecordDayDAO;
import util.ApplicationPropertyUtil;

import java.io.File;
import java.util.Map;

import static com.codeborne.selenide.Selenide.close;

public class BoltWorker {

    public static void main(String[] args) {
        runWorker();
    }

    public static void runWorker() {
        String login = ApplicationPropertyUtil.applicationPropertyGet("bolt.login");
        String pass = ApplicationPropertyUtil.applicationPropertyGet("bolt.pass");
        new BoltLogonBo().login(login, pass);
        Map<String, File> map = new DayReportBO().downloadAllNewCSV(BoltPaymentRecordDayDAO.getInstance().latestDate());
        close();
        new RecordHelper().recordDayReportToDB(map);
        System.exit(0);
    }
}
