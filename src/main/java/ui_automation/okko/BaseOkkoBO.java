package ui_automation.okko;

import ui_automation.BaseBO;

import static com.codeborne.selenide.Configuration.baseUrl;

public class BaseOkkoBO extends BaseBO {

    private static final String HOST="online.okko.ua";
    static {
        baseUrl = "http://"+HOST;
    }
}
