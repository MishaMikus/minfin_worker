package ui_automation.uber;

import org.apache.log4j.Logger;
import ui_automation.uber.bo.UberBO;
import ui_automation.uber.bo.UberLoginBO;
import util.ApplicationPropertyUtil;

import static com.codeborne.selenide.Configuration.headless;
import static com.codeborne.selenide.Selenide.close;
import static util.ApplicationPropertyUtil.getBoolean;

public class UberWorker {
    private static final Logger LOGGER = Logger.getLogger(UberWorker.class);

    public static void main(String[] args) {
        stat();
        close();
        System.exit(0);
    }

    public static void stat() {
        if (ApplicationPropertyUtil.getBoolean("selenide.proxy", false)) {
            System.out.println("SET PROXY");
            System.setProperty("selenide.proxyEnabled", "true");
            System.setProperty("selenide.proxyHost", "127.0.0.1");
            System.setProperty("selenide.proxyPort", "6666");
        }
        headless = getBoolean("headless", true);
        new UberLoginBO()
                .loginIfNotAuthorized(ApplicationPropertyUtil.applicationPropertyGet("uber.login")
                        , ApplicationPropertyUtil.applicationPropertyGet("uber.password"))
                .setSMSCodeIfNeed();
        new UberBO().recordPayment();
    }
}
