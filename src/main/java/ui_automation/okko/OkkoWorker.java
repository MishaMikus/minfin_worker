package ui_automation.okko;

import orm.entity.logan_park.filling.FillingRecord;
import orm.entity.logan_park.filling.FillingRecordDAO;
import util.ApplicationPropertyUtil;

import java.util.Date;
import java.util.List;

import static com.codeborne.selenide.Selenide.close;

public class OkkoWorker {

    public static void main(String[] args) {
        runWorker();
    }

    public static void runWorker() {
        String login = ApplicationPropertyUtil.applicationPropertyGet("okko.login");
        String pass = ApplicationPropertyUtil.applicationPropertyGet("okko.pass");
        new OkkoLoginBo().login(login, pass);
        FillingRecord latestDBRecord = getLatestFillingRecord();
        List<FillingRecord> allRecords = new OkkoBo().getAllLatestFillings(latestDBRecord);
        FillingRecordDAO.getInstance().saveBatch(allRecords);
        close();
        System.exit(0);
    }

    private static FillingRecord getLatestFillingRecord() {
        FillingRecord fillingRecordLatest = FillingRecordDAO.getInstance().latest("okko");
        return fillingRecordLatest == null ? new FillingRecord(new Date(0)) : fillingRecordLatest;
    }
}
