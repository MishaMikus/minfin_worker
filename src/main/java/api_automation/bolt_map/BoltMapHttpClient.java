package api_automation.bolt_map;

import api_automation.BaseClient;
import client.rest.model.RequestModel;
import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;
import util.ApplicationPropertyUtil;

import static client.rest.client.RestClient.GET;

public class BoltMapHttpClient extends BaseClient {
    private final static Logger LOGGER = Logger.getLogger(BoltMapHttpClient.class);
    public static BoltDriverStatusResponse ping() {
        RequestModel requestModel = baseHttpsGet();
        requestModel.setHost("node.taxify.eu");
        requestModel.setPath("/dispatcher/dispatcher/getDriverLocations/?version=ZW.1.19&deviceId=8ce1456e-91df-4c90-bdbc-313e9932327e&device_type=web");
        requestModel.addHeader("Authorization",
                ApplicationPropertyUtil.applicationPropertyGet("bolt.map.auth"));
        requestModel.setMethod(GET);
        ResponseModel responseModel=CLIENT.call(requestModel);
        return new BoltDriverStatusResponse().makeMyFromJsonString(responseModel.getBody());
    }

    public static void main(String[] args) {
        System.out.println(ping());
    }
}
