package api_automation;

import client.rest.client.ApacheRestClient;
import client.rest.client.RestClient;
import client.rest.model.RequestModel;

import static client.rest.client.RestClient.*;

public class BaseClient {
    protected static final RestClient CLIENT = new ApacheRestClient();

    protected static RequestModel baseHttpsGet() {
        RequestModel requestModel = new RequestModel();
        requestModel.setProtocol(HTTPS);
        requestModel.setMethod(GET);
        return requestModel;
    }

    protected static RequestModel baseHttpPost() {
        RequestModel requestModel = new RequestModel();
        requestModel.setProtocol(HTTP);
        requestModel.setMethod(POST);
        return requestModel;
    }
}
