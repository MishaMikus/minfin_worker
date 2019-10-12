package ui_automation.bolt;

import util.ApplicationPropertyUtil;

import static com.codeborne.selenide.Selenide.close;

public class BoltWorker {

    public static void main(String[] args) {
        runWorker();
    }

    public static void runWorker() {
        String login = ApplicationPropertyUtil.applicationPropertyGet("bolt.login");
        String pass = ApplicationPropertyUtil.applicationPropertyGet("bolt.pass");
        new BoltLogonBo().login(login, pass);
        close();
        System.exit(0);
    }
}
