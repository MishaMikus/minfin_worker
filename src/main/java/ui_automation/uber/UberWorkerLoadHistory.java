package ui_automation.uber;

import org.apache.log4j.Logger;
import ui_automation.uber.bo.UberBO;
import ui_automation.uber.bo.UberLoginBO;
import util.ApplicationPropertyUtil;

import static com.codeborne.selenide.Selenide.close;

public class UberWorkerLoadHistory {
    private static final Logger LOGGER = Logger.getLogger(UberWorkerLoadHistory.class);
    public static void main(String[] args) {
        runWorker();
    }

    private static void runWorker() {
        new UberLoginBO()
                .loginIfNotAuthorized(ApplicationPropertyUtil.applicationPropertyGet("uber.login")
                        , ApplicationPropertyUtil.applicationPropertyGet("uber.password"))
                .setSMSCodeIfNeed();
        new UberBO().recordPaymentWithOneWeekHistory();
        close();
        System.exit(0);
    }


}
