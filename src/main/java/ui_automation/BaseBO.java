package ui_automation;

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

        if (getBoolean("remote", false)) {
            remote = " http://localhost:4444/wd/hub";
            LOGGER.info("remote : " + remote);
        }
        headless = getBoolean("headless", true);
    }


    protected void goToPath(String path) {
        LOGGER.info("GOTO PAGE : " + baseUrl + path);
        open(path);
    }

    protected String executeJavaScriptAction(String actionName, String script) {
        try {
            LOGGER.info(actionName + " : " + script);
            return executeJavaScript(script);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("FAIL executeJavaScriptAction : " + actionName + " : " + script);
            return null;
        }
    }

    protected void acceptAlert() {
        try {
            Alert alert = getWebDriver().switchTo().alert();
            LOGGER.info("ACCEPT DIALOG : accept");
            alert.accept();
        } catch (Exception e) {
            LOGGER.info("NO ANY ACCEPT DIALOG");
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

    protected void clickById(String id) {
        $(By.id(id)).click();
        LOGGER.info("clickById : " + id);
    }

    protected void inputById(String id, String text) {
        $(By.id(id)).setValue(text);
        LOGGER.info("inputById : " + id + " text : " + text);
    }

    protected void clickByClassName(String className) {
        $(By.className(className)).click();
        LOGGER.info("clickByClassName : " + className);
    }

    protected void clickByType(String type) {
        $(By.xpath("//*[@type='" + type + "']")).click();
        LOGGER.info("clickByType : " + type);
    }

    protected void clickByPartialText(String text) {
        $(By.partialLinkText(text)).click();
        LOGGER.info("clickByPartialText : " + text);
    }

    private void setValueByClassName(String className, String value) {
        $(By.className(className)).setValue(value);
        LOGGER.info("setValueByClassName : " + className + " value : " + value);
    }

    protected void clickByXpath(String xpath) {
        $(By.xpath(xpath)).click();
        LOGGER.info("clickByXpath " + xpath);
    }
}
