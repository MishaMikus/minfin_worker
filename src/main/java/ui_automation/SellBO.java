package ui_automation;

public class SellBO extends BaseBO {
    public void sell(String amount, String course, String message) {
        goToPath("/currency/auction/usd/sell/lvov");
        try{
        executeJavaScriptAction("DELETE OLD", "document.querySelectorAll(\".au-delete-deal.js-au-delete-deal\")[0].click();");}catch (Exception e)
        {
            System.out.println("CAN'T DELETE OLD");
        }
        acceptAlert();
        goToPath("/currency/auction/new");
        executeJavaScriptAction("SET TYPE SELL", "document.getElementsByName(\"Type\")[0].value='sell';");
        executeJavaScriptAction("SET AMOUNT", "document.getElementsByName(\"Amount\")[0].value='" + amount + "';");
        executeJavaScriptAction("CLICK PARTS", "document.getElementsByName(\"Parts\")[1].click();");
        executeJavaScriptAction("SET COURSE", "document.getElementsByName(\"Course\")[0].value='" + course + "';");
        executeJavaScriptAction("SET CITY", "document.getElementsByName(\"RegionID\")[0].value='32';");
        executeJavaScriptAction("SET DISTRICT", "document.getElementsByName(\"District\")[0].value='" + message + "';");
        executeJavaScriptAction("CLICK SUBMIT", "document.getElementsByClassName(\"au-submit-btn js-submit\")[0].click();");
        executeJavaScriptAction("CLICK SELL", "document.getElementsByClassName(\"au-submit-btn\")[0].click();");}
    }


