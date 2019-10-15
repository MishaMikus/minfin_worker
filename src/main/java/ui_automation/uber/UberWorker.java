package ui_automation.uber;

import org.apache.log4j.Logger;
import ui_automation.uber.bo.UberBO;
import ui_automation.uber.bo.UberLoginBO;
import util.ApplicationPropertyUtil;

import static com.codeborne.selenide.Selenide.close;

public class UberWorker {
    private static final Logger LOGGER = Logger.getLogger(UberWorker.class);

    private static void runWorker() {
        new UberLoginBO()
                .loginIfNotAuthorized(ApplicationPropertyUtil.applicationPropertyGet("uber.login")
                        , ApplicationPropertyUtil.applicationPropertyGet("uber.password"))
                .setSMSCodeIfNeed();
        new UberBO().recordPayment();
        close();
        System.exit(0);
    }


}
