package ui_automation.bo;

import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Configuration.headless;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class BaseBO {
    static {
        timeout = 10000;
        browser = "chrome";
        savePageSource = true;
        if (applicationPropertyGet("remote").equals("true")) {
            remote = " http://localhost:4444/wd/hub";
            headless = true;
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
        System.out.println(actionName + " : " + script);
        return executeJavaScript(script);
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
}
