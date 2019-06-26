package ui_automation;

import org.openqa.selenium.Alert;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BaseBO {

    void deleteProposal() {
        try {
            executeJavaScriptAction("DELETE OLD", "document.querySelectorAll(\".au-delete-deal.js-au-delete-deal\")[0].click();");
        } catch (Exception e) {
            System.out.println("CAN'T DELETE OLD");
        }
        acceptAlert();
    }

    public BaseBO() {
        timeout = 10000;
        baseUrl = "https://minfin.com.ua";
//        startMaximized = false;
        browser = "chrome";
//        browserPosition = "890x10";
//        browserSize = "780x950";
        savePageSource = true;
        headless = true;
        //System.getProperties().list(System.out);
        if (!System.getProperties().containsValue("windows")) {
            System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        }
    }

    void goToPath(String path) {
        System.out.println("GOTO PAGE : " + baseUrl + path);
        open(path);
    }

    String executeJavaScriptAction(String actionName, String script) {
        System.out.println(actionName + " : " + script);
        return executeJavaScript(script);
    }

    void acceptAlert() {
        try {
            Alert alert = getWebDriver().switchTo().alert();
            System.out.println("ACCEPT DIALOG : accept");
            alert.accept();
        } catch (Exception e) {
            System.out.println("NO ANY ACCEPT DIALOG");
        }
    }

}
