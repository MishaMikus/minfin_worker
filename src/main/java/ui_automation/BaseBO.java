package ui_automation;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.source;

public class BaseBO {
    public BaseBO() {
        timeout = 10000;
        baseUrl = "https://minfin.com.ua";
        startMaximized = false;
        browser = "chrome";
        browserPosition = "890x10";
        browserSize = "780x950";
        savePageSource = true;
        headless = true;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        Capabilities capabilities = new Capabilities() {
            @Override
            public Map<String, ?> asMap() {
                return options.asMap();
            }

            @Override
            public Object getCapability(String s) {
                return options.asMap().get(s);
            }
        };
        browserCapabilities.merge(capabilities);

    }

    void goToPath(String path) {
        System.out.println("GOTO SELL : " + baseUrl + path);
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
