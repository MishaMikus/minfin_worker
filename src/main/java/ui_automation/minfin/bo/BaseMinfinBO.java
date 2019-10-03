package ui_automation.minfin.bo;

import ui_automation.BaseBO;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.$$;

public class BaseMinfinBO extends BaseBO {

    static {
        baseUrl = "https://minfin.com.ua";
    }

    public void deleteProposal() {
        if ($$(".au-delete-deal.js-au-delete-deal").size() > 0) {
            executeJavaScriptAction("DELETE OLD", "document.querySelectorAll(\".au-delete-deal.js-au-delete-deal\")[0].click();");
            acceptAlert();
        } else {
            System.out.println("CAN'T DELETE OLD");
        }
    }
}
