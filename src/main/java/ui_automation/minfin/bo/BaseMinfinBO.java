package ui_automation.minfin.bo;

import ui_automation.bo.BaseBO;

import static com.codeborne.selenide.Configuration.*;

public class BaseMinfinBO extends BaseBO {

    static {
        baseUrl = "https://minfin.com.ua";
    }

    public void deleteProposal() {
        try {
            executeJavaScriptAction("DELETE OLD", "document.querySelectorAll(\".au-delete-deal.js-au-delete-deal\")[0].click();");
        } catch (Exception e) {
            System.out.println("CAN'T DELETE OLD");
        }
        acceptAlert();
    }
}
