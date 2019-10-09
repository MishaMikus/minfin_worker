package ui_automation.bolt;

import ui_automation.BaseBO;

import static com.codeborne.selenide.Configuration.baseUrl;

public class BaseBoltBO extends BaseBO {

    private static final String HOST="fleets.bolt.eu";
    static {
        baseUrl = "https://"+HOST;
    }
}
