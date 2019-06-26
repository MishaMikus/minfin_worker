package ui_automation;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

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
        options.addArguments("--headless");
        Capabilities capabilities = new Capabilities() {
            @Override
            public Map<String, ?> asMap() {
                System.out.println("asMap " + options.asMap());
                return options.asMap();
            }

            @Override
            public Object getCapability(String s) {
                System.out.println("getCapability " + s);
                return options.asMap().get(s);
            }
        };
        Configuration.browserCapabilities.merge(capabilities);
        //System.getProperties().list(System.out);
        if (!System.getProperties().containsValue("windows")) {
            System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        }
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
