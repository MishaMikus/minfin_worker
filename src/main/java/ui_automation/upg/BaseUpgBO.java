package ui_automation.upg;

import ui_automation.BaseBO;

import static com.codeborne.selenide.Configuration.baseUrl;

public class BaseUpgBO extends BaseBO {

    private static final String HOST="upgcard.com.ua";
    static {
        baseUrl = "http://"+HOST;
    }
}
