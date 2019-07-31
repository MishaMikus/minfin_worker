package ui_automation.uber.bo;

import org.openqa.selenium.By;
import ui_automation.bo.BaseBO;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class UberLoginBO extends BaseBO {
    static {
        baseUrl = "https://partners.uber.com";
    }

    public void login(String login, String password) {
        goToPath("/login");
        try {
            $(By.id("useridInput")).setValue(login);
            $(By.tagName("button")).click();
            $(By.id("password")).setValue(password);
            $(By.tagName("button")).click();
            $(By.className("icon_menu")).click();
            $(By.linkText("Fleet")).click();
            $$(By.className("tabs__link")).get(4).click();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("could'nt login");
        }
        while (true) {
        }
    }
}
