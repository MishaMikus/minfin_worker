package api_automation.tracker;

import api_automation.BaseClient;
import client.rest.model.RequestModel;
import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;
import orm.entity.logan_park.vehicle.VehicleDAO;

public class TrackerMapHttp extends BaseClient {
    private final static Logger LOGGER = Logger.getLogger(BaseClient.class);

    public static void ping() {
        VehicleDAO.findAllTrackerId();
        RequestModel requestModel = baseHttpPost();
        requestModel.setHost("www.ascendgps.com");
        requestModel.setPath("/Ajax/DevicesAjax.asmx/GetTracking");
        requestModel.setBody("{DeviceID:28811,TimeZone:'EET'}");
        requestModel.addHeader("Content-Type", "application/json");
        ResponseModel responseModel = CLIENT.call(requestModel);
        LOGGER.info(requestModel.getMethod() + " " + requestModel.getURL() + " " + responseModel.getStatusCode() + " " + responseModel.getResponseTime() + " ms");
        String pseudoJson = responseModel.getBodyAsJson().getString("d");

        System.out.println(new TrackerPingModel().makeMyFromJsonString(normalize(pseudoJson)));
    }

    private static String normalize(String pseudoJson) {
        for (String entry : pseudoJson.split(",")) {
            String key = entry.replaceAll("\\{", "").split(":")[0];
            pseudoJson = pseudoJson.replaceAll(key, "\"" + key + "\"");
        }
        return pseudoJson;
    }
}
