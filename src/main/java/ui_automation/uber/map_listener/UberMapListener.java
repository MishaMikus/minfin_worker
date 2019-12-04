package ui_automation.uber.map_listener;

import net.lightbody.bmp.BrowserMobProxyServer;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ui_automation.uber.bo.UberBO;
import ui_automation.uber.bo.UberLoginBO;
import util.ApplicationPropertyUtil;
import util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.lightbody.bmp.proxy.CaptureType.*;

public class UberMapListener {

    private static final Logger LOGGER = Logger.getLogger(UberMapListener.class);

    public static void main(String[] args) {
        runListener();
    }

    private static void runListener() {
        setupProxy();
        login();

        // while (true) {
        new UberBO().gotoMapPage();
        sleep(10000);
        collectNewHar("uber.map", 10000);
        processHarMapDataFileFile();
        new UberBO().gotoDriverPage();
        collectNewHar("uber.driver", 10000);
        processHarDriverDataFileFile();
        // }

        finish();
    }

    private static void collectNewHar(String name, int timeout) {
        Bmp.proxyServer.newHar(name);
        Bmp.proxyServer.newPage();
        sleep(timeout);
    }

    private static void processHarDriverDataFileFile() {
        saveMapDriverInfoToDB(saveHarFile("driver.har.json"));
    }

    private static void saveMapDriverInfoToDB(File harFile) {
        List<String> jsonList = getMapEventJsonCollection(harFile, "https://partners.uber.com/p3/fleet-manager/_rpc?rpc=getFleetDrivers");
        for (String json : jsonList) {
            saveDriverPingToDB(json);
        }
    }

    private static void processHarMapDataFileFile() {
        saveMapPingInfoToDB(saveHarFile("map.har.json"));
    }

    private static void saveMapPingInfoToDB(File harFile) {
        List<String> jsonList = getMapEventJsonCollection(harFile, "https://partners.uber.com/p3/fleet-manager/_rpc?rpc=getDriverEvents");
        for (String json : jsonList) {
            saveMapPingToDB(json);
        }
    }

    private static List<String> getMapEventJsonCollection(File harFile, String url) {
        List<String> jsonList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(IOUtils.readTextFromFile(harFile));
        JSONArray jsonArray = jsonObject.getJSONObject("log").getJSONArray("entries");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject page = jsonArray.getJSONObject(i);
            if (page.getJSONObject("request").getString("url").equals(url)) {
                jsonList.add(page.getJSONObject("response").getJSONObject("content").getString("text"));
            }
        }
        return jsonList;
    }

    private static void saveMapPingToDB(String json) {
        System.out.println(json);
        //TODO
    }

    private static void saveDriverPingToDB(String json) {
        System.out.println(json);
        //TODO
    }

    private static File saveHarFile(String fileName) {
        File file = new File(fileName);
        try {
            Bmp.proxyServer.getHar().writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static void finish() {
        Bmp.proxyServer.stop();
    }

    private static void login() {
        new UberLoginBO()
                .loginIfNotAuthorized(ApplicationPropertyUtil.applicationPropertyGet("uber.login")
                        , ApplicationPropertyUtil.applicationPropertyGet("uber.password"))
                .setSMSCodeIfNeed();
    }

    private static void setupProxy() {
        System.setProperty("selenide.browser", BmpChrome.class.getName());
        Bmp.proxyServer = new BrowserMobProxyServer();
        Bmp.proxyServer.start(0);
        Bmp.proxyServer.setHarCaptureTypes(REQUEST_HEADERS,
                REQUEST_COOKIES,
                REQUEST_CONTENT,
                REQUEST_BINARY_CONTENT,
                RESPONSE_HEADERS,
                RESPONSE_COOKIES,
                RESPONSE_CONTENT,
                RESPONSE_BINARY_CONTENT);
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
