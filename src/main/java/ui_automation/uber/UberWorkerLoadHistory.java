package ui_automation.uber;

import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Logger;
import ui_automation.uber.bo.UberBO;

import static ui_automation.uber.bo.UberLoginBO.login;

public class UberWorkerLoadHistory {
    private static final Logger LOGGER = Logger.getLogger(UberWorkerLoadHistory.class);

    public static void main(String[] args) {
        runWorker();
        System.exit(0);
    }

    public static void runWorker() {
        login();
        new UberBO().recordPaymentWithOneWeekHistory();
        WebDriverRunner.getWebDriver().close();
    }
}
