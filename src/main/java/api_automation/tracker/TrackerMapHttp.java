package api_automation.tracker;

import api_automation.BaseClient;
import client.rest.model.RequestModel;
import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;
import orm.entity.logan_park.map_pinger.MapPingerItem;
import orm.entity.logan_park.map_pinger.MapPingerItemDAO;
import orm.entity.logan_park.vehicle.Vehicle;
import orm.entity.logan_park.vehicle.VehicleDAO;

import java.util.Date;
import java.util.List;

import static orm.entity.logan_park.map_pinger.taxi_brand.TaxiBrandDAO.TRACKER;
import static orm.entity.logan_park.map_pinger.taxi_brand.TaxiBrandDAO.UBER;
import static ui_automation.uber.map_listener.UberMapListener.findStateId;

public class TrackerMapHttp extends BaseClient {
    private final static Logger LOGGER = Logger.getLogger(BaseClient.class);

    public static void ping() {
        List<Vehicle> vehicleCollection = VehicleDAO.getInstance().findAll();
        vehicleCollection.stream().filter(v -> v.getTracker_id() != null).forEach(v -> {
            TrackerPingModel track = getTrack(v.getTracker_id());
            MapPingerItem mapPingerItem = new MapPingerItem();
            mapPingerItem.setLat(Double.parseDouble(track.getoLat()));
            mapPingerItem.setLng(Double.parseDouble(track.getoLng()));
            mapPingerItem.setVehicle_id(v.getId());
            mapPingerItem.setTimestamp(new Date().getTime());
            mapPingerItem.setDriver_id(0);//TODO get driver
            mapPingerItem.setState_id(findStateId(track.getStatus(), TRACKER.getBase_id()));
            MapPingerItemDAO.getInstance().save(mapPingerItem);
        });
    }

    private static TrackerPingModel getTrack(Integer tracker_id) {
        RequestModel requestModel = baseHttpPost();
        requestModel.setHost("www.ascendgps.com");
        requestModel.setPath("/Ajax/DevicesAjax.asmx/GetTracking");
        requestModel.setBody("{DeviceID:" + tracker_id + ",TimeZone:'EET'}");
        requestModel.addHeader("Content-Type", "application/json");
        ResponseModel responseModel = CLIENT.call(requestModel);
        LOGGER.info(requestModel.getMethod() + " " + requestModel.getURL() + " " + responseModel.getStatusCode() + " " + responseModel.getResponseTime() + " ms");
        String pseudoJson = responseModel.getBodyAsJson().getString("d");
        return new TrackerPingModel().makeMyFromJsonString(normalize(pseudoJson));
    }

    private static String normalize(String pseudoJson) {
        for (String entry : pseudoJson.split(",")) {
            String key = entry.replaceAll("\\{", "").split(":")[0];
            pseudoJson = pseudoJson.replaceAll(key, "\"" + key + "\"");
        }
        return pseudoJson;
    }
}
