package ui_automation.uber;
import ui_automation.uber.bo.UberLoginBO;

public class Uber {


    public static void stat() {
        System.setProperty("selenide.proxyEnabled", "true");
        System.setProperty("selenide.proxyHost", "127.0.0.1");
        System.setProperty("selenide.proxyPort", "6666");
        new UberLoginBO().login("logan.park.lviv@gmail.com", "qwsdf@#123era");
    }
}
