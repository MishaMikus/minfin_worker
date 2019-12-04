package ui_automation.uber.map_listener;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.log4j.Logger;
import ui_automation.uber.bo.UberBO;
import ui_automation.uber.bo.UberLoginBO;
import util.ApplicationPropertyUtil;

import java.io.File;
import java.io.IOException;

public class UberMapListener {

    private static final Logger LOGGER = Logger.getLogger(UberMapListener.class);

    public static void main(String[] args) {
        runListener();
    }

    private static void runListener() {
        System.setProperty("selenide.browser", BmpChrome.class.getName());
        Bmp.proxyServer = new BrowserMobProxyServer();
        Bmp.proxyServer.start(0);
        Bmp.proxyServer.setHarCaptureTypes(CaptureType.RESPONSE_CONTENT);


        new UberLoginBO()
                .loginIfNotAuthorized(ApplicationPropertyUtil.applicationPropertyGet("uber.login")
                        , ApplicationPropertyUtil.applicationPropertyGet("uber.password"))
                .setSMSCodeIfNeed();

        new UberBO().gotoMapPage();
        Bmp.proxyServer.newHar("uber.map");
        Bmp.proxyServer.newPage();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Bmp.proxyServer.getHar().writeTo(new File("har.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bmp.proxyServer.stop();
    }
}
