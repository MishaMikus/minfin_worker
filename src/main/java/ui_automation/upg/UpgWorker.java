package ui_automation.upg;

import orm.entity.logan_park.filling.FillingRecord;
import orm.entity.logan_park.filling.FillingRecordDAO;
import util.ApplicationPropertyUtil;

import java.util.Date;
import java.util.List;

import static com.codeborne.selenide.Selenide.close;

public class UpgWorker {

    public static void main(String[] args) {
        runWorker();
    }

    public static void runWorker() {
        String login = ApplicationPropertyUtil.applicationPropertyGet("upg.login");
        String pass = ApplicationPropertyUtil.applicationPropertyGet("upg.pass");
        new UpgLoginBo().login(login, pass);
        List<FillingRecord> allRecords = new UpgBo().getAllLatestFillings(getLatestFillingRecord());
        FillingRecordDAO.getInstance().saveBatch(allRecords);
        close();
        System.exit(0);
    }
    private static FillingRecord getLatestFillingRecord() {
        FillingRecord fillingRecordLatest = FillingRecordDAO.getInstance().latest("upg");
        return fillingRecordLatest == null ? new FillingRecord(new Date(0)) : fillingRecordLatest;
    }
}
