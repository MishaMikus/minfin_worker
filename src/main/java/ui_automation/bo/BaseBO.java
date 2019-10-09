package ui_automation.bo;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import java.util.Date;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static util.ApplicationPropertyUtil.getBoolean;

public class BaseBO {
    private final static Logger LOGGER = Logger.getLogger(BaseBO.class);
    private static final Long TIMEOUT_MS = 10 * 60 * 1000L;
    private static final Long DOWNLOAD_BUTTON_APPEAR_ITERATION_TIME_MS = 5 * 1000L;
    static {
        timeout = 10000;
        browser = "chrome";
        savePageSource = true;
        if (getBoolean("remote",false)) {
            remote = " http://localhost:4444/wd/hub";
            headless = getBoolean("headless",true);
            System.out.println("remote : " + remote);
            System.out.println("headless : " + headless);
        }
    }


    protected void goToPath(String path) {
        //GOTO PAGE : https://minfin.com.ua/login
        System.out.println("GOTO PAGE : " + baseUrl + path);
        open(path);
    }

    protected String executeJavaScriptAction(String actionName, String script) {
        try {


            System.out.println(actionName + " : " + script);
            return executeJavaScript(script);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FAIL executeJavaScriptAction : " + actionName + " : " + script);
            return null;
        }
    }

    protected void acceptAlert() {
        try {
            Alert alert = getWebDriver().switchTo().alert();
            System.out.println("ACCEPT DIALOG : accept");
            alert.accept();
        } catch (Exception e) {
            System.out.println("NO ANY ACCEPT DIALOG");
        }
    }
    protected void waitingForDownloadButtonAppear() {
        Long startMs = new Date().getTime();
        while (!$(By.linkText("Download CSV")).isDisplayed() && timeout(startMs, TIMEOUT_MS)) {
            LOGGER.info("WAITING FOR DOWNLOAD BUTTON APPEAR");
            threadSleep(DOWNLOAD_BUTTON_APPEAR_ITERATION_TIME_MS);
        }
        LOGGER.info("DOWNLOAD BUTTON APPEAR");
    }

    protected boolean timeout(Long startMs, Long timeoutMs) {
        return ((new Date().getTime() - startMs) < timeoutMs);
    }

    protected void threadSleep(Long timeMs) {
        LOGGER.info("SLEEP " + timeMs + " MS");
        try {
            Thread.sleep(timeMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
