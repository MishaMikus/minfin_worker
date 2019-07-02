package ui_automation;

import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static util.ApplicationPropertyUtil.applicationPropertyGet;

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
        browser = "chrome";
        savePageSource = true;
        //headless = true;
        if (applicationPropertyGet("remote").equals("true")) {
            remote = " http://localhost:4444/wd/hub";
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
