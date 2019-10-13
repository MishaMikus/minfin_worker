package ui_automation.bolt;

import com.codeborne.selenide.Configuration;
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
        Map<String, File> map = new DayReportBO().downloadAllCSV();
        close();
        new RecordHelper().recordDayReportToDB(map);
        System.exit(0);
    }
}
