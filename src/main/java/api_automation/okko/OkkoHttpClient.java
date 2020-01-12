package api_automation.okko;

import api_automation.BaseClient;
import client.rest.model.RequestModel;
import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;
import orm.entity.logan_park.filling.FillingRecord;
import util.ApplicationPropertyUtil;
import util.StringUtil;

import java.util.List;

import static client.rest.client.RestClient.GET;
import static client.rest.client.RestClient.POST;

public class OkkoHttpClient extends BaseClient {

    private final static Logger LOGGER = Logger.getLogger(OkkoHttpClient.class);
    private final static String HOST = "online.okko.ua";

    static ResponseModel getHomePage() {
        return okkoGet("pages/main.jspx");
    }

    public static ResponseModel getHost() {
        return okkoGet("");
    }

    static ResponseModel okkoGet(String path) {
        RequestModel requestModel = baseHttpGet();
        requestModel.setHost(HOST);
        requestModel.setPath("/" + path);
        requestModel.setMethod(GET);
        return CLIENT.call(requestModel);
    }

    static ResponseModel postLogin(String urlToken) {
        RequestModel requestModel = baseHttpPost();
        requestModel.setHost(HOST);
        requestModel.setPath("/pages/login.jspx;" + urlToken);
        String login = ApplicationPropertyUtil.applicationPropertyGet("okko.login");
        String pass = ApplicationPropertyUtil.applicationPropertyGet("okko.pass");
        requestModel.setBody("userForm%3ArealUsername=" + login +
                "&userForm%3ArealUserpass=" + StringUtil.urlEncode(pass) +
                "&userForm%3Aj_id18=Enter" +
                "&userForm=userForm" +
                "&autoScroll=" +
                "&javax.faces.ViewState=j_id1");
        requestModel.addHeader("Content-Type", "application/x-www-form-urlencoded");
        requestModel.setBodyEncoding("UTF-8");
        return CLIENT.call(requestModel);
    }

    private static List<FillingRecord> getAllLatestFillings(FillingRecord latestFillingRecord) {
        //TODO
        return null;
    }

}
