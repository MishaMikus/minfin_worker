package ui_automation;

public class NewBO extends BaseBO {
    private final static String url = "/currency/auction/new";

    public void create(String type, String amount, String course, String message) {
        goToPath(url);
        executeJavaScriptAction("SET TYPE "+type.toUpperCase(), "document.getElementsByName(\"Type\")[0].value='"+type+"';");
        executeJavaScriptAction("SET AMOUNT", "document.getElementsByName(\"Amount\")[0].value='" + amount + "';");
        executeJavaScriptAction("CLICK PARTS", "document.getElementsByName(\"Parts\")[1].click();");
        executeJavaScriptAction("SET COURSE", "document.getElementsByName(\"Course\")[0].value='" + course + "';");
        executeJavaScriptAction("SET CITY", "document.getElementsByName(\"RegionID\")[0].value='32';");
        executeJavaScriptAction("SET DISTRICT", "document.getElementsByName(\"District\")[0].value='" + message + "';");
        executeJavaScriptAction("CLICK SUBMIT", "document.getElementsByClassName(\"au-submit-btn js-submit\")[0].click();");
        executeJavaScriptAction("CLICK DONE", "document.getElementsByClassName(\"au-submit-btn\")[0].click();");
    }
}
