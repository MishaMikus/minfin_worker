package ui_automation.uber.map_listener;

import net.lightbody.bmp.BrowserMobProxyServer;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import orm.entity.logan_park.driver.UberDriver;
import orm.entity.logan_park.driver.UberDriverDAO;
import orm.entity.logan_park.map_pinger.MapPingerItem;
import orm.entity.logan_park.map_pinger.MapPingerItemDAO;
import orm.entity.logan_park.map_pinger.state.MapPingerState;
import orm.entity.logan_park.map_pinger.state.MapPingerStateDAO;
import orm.entity.uber.driver_realtime_table.UberDriverRealTime;
import orm.entity.uber.driver_realtime_table.UberDriverRealTimeDAO;
import orm.entity.uber.driver_realtime_table.state.UberDriverRealTimeState;
import orm.entity.uber.driver_realtime_table.state.UberDriverRealTimeStateDAO;
import ui_automation.uber.bo.UberBO;
import ui_automation.uber.bo.UberLoginBO;
import ui_automation.uber.map_listener.model.list.UberDriverList;
import ui_automation.uber.map_listener.model.list.UberDriverListDriver;
import ui_automation.uber.map_listener.model.map.UberDriverEvent;
import ui_automation.uber.map_listener.model.map.UberMapPingItem;
import util.ApplicationPropertyUtil;
import util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.lightbody.bmp.proxy.CaptureType.*;
import static orm.entity.logan_park.map_pinger.taxi_brand.TaxiBrandDAO.UBER;

public class UberMapListener {

    private static final Logger LOGGER = Logger.getLogger(UberMapListener.class);

    public static void main(String[] args) {
        runListener();
    }

    private static void runListener() {
        setupProxy();
        login();
        while (true) {
            processingDriverList();//should be first for new drivers creation
            processingMap();
        }
    }

    private static void processingMap() {
        new UberBO().gotoMapPage();
        sleep(10000);
        collectNewHar("uber.map", 10000);
        processHarMapDataFileFile();
    }

    private static void processingDriverList() {
        new UberBO().gotoDriverPage();
        collectNewHar("uber.driver", 10000);
        processHarDriverDataFileFile();
    }

    private static void collectNewHar(String name, int timeout) {
        Bmp.proxyServer.newHar(name);
        Bmp.proxyServer.newPage();
        sleep(timeout);
    }

    private static void processHarDriverDataFileFile() {
        try {
            saveMapDriverInfoToDB(saveHarFile("driver.har.json"));
        } catch (Exception e) {
        }
    }

    private static void saveMapDriverInfoToDB(File harFile) {
        List<String> jsonList = getMapEventJsonCollection(harFile, "https://partners.uber.com/p3/fleet-manager/_rpc?rpc=getFleetDrivers");
        for (String json : jsonList) {
            saveDriverPingToDB(json);
        }
    }

    private static void processHarMapDataFileFile() {
        try {
            saveMapPingInfoToDB(saveHarFile("map.har.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                JSONObject content = page.getJSONObject("response").getJSONObject("content");
                if (content.keySet().contains("text")) {
                    jsonList.add(content.getString("text"));
                }
            }
        }
        return jsonList;
    }

    private static void saveMapPingToDB(String json) {
        UberMapPingItem uberMapPingItem = new UberMapPingItem().makeMyFromJsonString(json);
        uberMapPingItem.getData().getDriverEvents().forEach(d -> {
            LOGGER.info("try to transform "+uberMapPingItem);
            MapPingerItem mapPingerItem = new MapPingerItem();
            mapPingerItem.setDriver_id(findDriverId(d));
            mapPingerItem.setLat(d.getDriverLocation().getLatitude());
            mapPingerItem.setLng(d.getDriverLocation().getLongitude());
            mapPingerItem.setState_id(findStateId(d.getDriverStatus(), UBER.getBase_id()));
            mapPingerItem.setTimestamp(parseTimestamp(d.getEventTimestamp()));
            mapPingerItem.setVehicle_id(findVehicleId(d));
            MapPingerItemDAO.getInstance().save(mapPingerItem);
        });
    }

    private static Integer findVehicleId(UberDriverEvent d) {
        //TODO
        return 0;
    }

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static Long parseTimestamp(String eventTimestamp) {
        //2019-12-05T06:31:01.485Z
        if (eventTimestamp != null) {
            try {
                LOGGER.info("TRY parse " + eventTimestamp);
                return SDF.parse(eventTimestamp).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.warn("Date parsing error : " + eventTimestamp);
            }
        }
        return new Date().getTime();
    }

    public static Integer findStateId(String state, Integer taxiBrandId) {
        MapPingerState mapPingerState = MapPingerStateDAO.getInstance().findByStatusAndTaxi(state, taxiBrandId);
        if (mapPingerState == null) {
            mapPingerState = new MapPingerState();
            mapPingerState.setState(state);
            mapPingerState.setTaxi_brand_id(taxiBrandId);
            Integer id = (Integer) MapPingerStateDAO.getInstance().save(mapPingerState);
            mapPingerState.setBase_id(id);
            LOGGER.info("cant find state, create new : " + mapPingerState);
        }
        return mapPingerState.getBase_id();
    }

    private static Integer findDriverId(UberDriverEvent driver) {
        LOGGER.info("TRY to find driver_id for "+driver);
        UberDriver uberDriver = UberDriverDAO.getInstance().findDriverByUUID(driver.getDriverUUID());
        if (uberDriver == null) {
            uberDriver = saveNewDriver(driver.getDriverUUID(), "UNKNOWN DRIVER");
        }
        return uberDriver.getId();
    }

    private static void saveDriverPingToDB(String json) {
        UberDriverList uberDriverList = new UberDriverList().makeMyFromJsonString(json);
        LOGGER.info("DRIVERS_LIST: " + uberDriverList);
        uberDriverList.getData().getDrivers().forEach(d -> {
            UberDriverRealTime uberDriverRealTime = new UberDriverRealTime();
            uberDriverRealTime.setDriver_id(findDriverIdByRealtimeDriver(d));
            uberDriverRealTime.setLastTimeOnline(parseTimestamp(d.getLastOnlineTime()));
            uberDriverRealTime.setRealtime_state_id(findRealTimeStateId(d.getRealtimeStatus()));
            uberDriverRealTime.setTimestamp(new Date().getTime());
            UberDriverRealTimeDAO.getInstance().save(uberDriverRealTime);
        });
    }

    private static Integer findRealTimeStateId(String state) {
        UberDriverRealTimeState uberDriverRealTimeState = UberDriverRealTimeStateDAO.getInstance().findByStatus(state);
        if (uberDriverRealTimeState == null) {
            uberDriverRealTimeState = new UberDriverRealTimeState();
            uberDriverRealTimeState.setState(state);
            Integer id = (Integer) UberDriverRealTimeStateDAO.getInstance().save(uberDriverRealTimeState);
            uberDriverRealTimeState.setBase_id(id);
            LOGGER.info("cant find realtime driver state, create new : " + uberDriverRealTimeState);
        }
        return uberDriverRealTimeState.getBase_id();
    }

    private static Integer findDriverIdByRealtimeDriver(UberDriverListDriver driver) {
        LOGGER.info("TRY to find driver_id for "+driver);
        UberDriver uberDriver = UberDriverDAO.getInstance().findDriverByUUID(driver.getUuid());
        if (uberDriver == null) {
            uberDriver = saveNewDriver(driver.getUuid(), driver.getName());
        }
        return uberDriver.getId();
    }

    private static UberDriver saveNewDriver(String uuid, String name) {
        LOGGER.info("try to create driver:"+uuid+" name: "+name);
        UberDriver uberDriver = new UberDriver();
        uberDriver.setDriverType("usual40");
        uberDriver.setDriverUUID(uuid);
        uberDriver.setName(name.replaceAll(" ","_"));
        Integer id = (Integer) UberDriverDAO.getInstance().save(uberDriver);
        uberDriver.setId(id);
        LOGGER.info("create default driver: " + uberDriver);
        return uberDriver;
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
