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
        Har har = null;

        // Instantiate a new proxy server.
        Bmp.proxyServer = new BrowserMobProxyServer();

        // Start the proxy server to capture network traffic.
        // The start() can receive a port number,
        // if no value is passed then it
        // defaults to "0".
        // The value of "0" indicates that the proxy
        // server will use a random port.
        Bmp.proxyServer.start(0);

        // Enables the capturing of all content types.
        // This can be fine-tuned to get specific content data.
        // Reference:
        // net.lightbody.bmp.proxy.CaptureType
        Bmp.proxyServer.setHarCaptureTypes(CaptureType
                .getAllContentCaptureTypes());

        // Create a new HTTP Archive to store the
        // requests and response details.
        Bmp.proxyServer.newHar("example.com");

        // Get the current HTTP requests and responses from when the proxy
        // server started up to now.

        new UberLoginBO()
                .loginIfNotAuthorized(ApplicationPropertyUtil.applicationPropertyGet("uber.login")
                        , ApplicationPropertyUtil.applicationPropertyGet("uber.password"))
                .setSMSCodeIfNeed();
        har = Bmp.proxyServer.getHar();
        new UberBO().gotoMapPage();
        try {
            Thread.sleep(3600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Write the results of the HAR to a local file.
        try {
            har.writeTo(new File("har.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stop capturing the network traffic.
        Bmp.proxyServer.stop();
    }
}
