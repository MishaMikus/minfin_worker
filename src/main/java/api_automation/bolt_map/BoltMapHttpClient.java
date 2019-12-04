package api_automation.bolt_map;

import client.rest.client.ApacheRestClient;
import client.rest.client.RestClient;
import client.rest.model.RequestModel;
import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;
import util.ApplicationPropertyUtil;

import static client.rest.client.RestClient.GET;
import static client.rest.client.RestClient.HTTPS;

public class BoltMapHttpClient {
    private static final RestClient CLIENT = new ApacheRestClient();
    private final static Logger LOGGER = Logger.getLogger(BoltMapHttpClient.class);
    public static BoltDriverStatusResponse ping() {
        RequestModel requestModel = new RequestModel();
        requestModel.setProtocol(HTTPS);
        requestModel.setHost("node.taxify.eu");
        requestModel.setPath("/dispatcher/dispatcher/getDriverLocations/?version=ZW.1.19&deviceId=8ce1456e-91df-4c90-bdbc-313e9932327e&device_type=web");
        requestModel.addHeader("Authorization",
                ApplicationPropertyUtil.applicationPropertyGet("bolt.map.auth"));
        requestModel.setMethod(GET);
        ResponseModel responseModel=CLIENT.call(requestModel);
        LOGGER.info(requestModel.getMethod()+" "+requestModel.getURL()+" "+responseModel.getStatusCode()+" "+responseModel.getResponseTime()+" ms");
        return new BoltDriverStatusResponse().makeMyFromJsonString(responseModel.getBody());
    }
}
