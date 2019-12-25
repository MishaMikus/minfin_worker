package ui_automation.uber;

import org.apache.log4j.Logger;
import ui_automation.uber.bo.UberBO;

import static com.codeborne.selenide.Selenide.close;
import static ui_automation.uber.bo.UberLoginBO.login;

public class UberWorkerLoadHistory {
    private static final Logger LOGGER = Logger.getLogger(UberWorkerLoadHistory.class);

    public static void main(String[] args) {
        runWorker();
    }

    private static void runWorker() {
        login();
        new UberBO().recordPaymentWithOneWeekHistory();
        close();
        System.exit(0);
    }
}
