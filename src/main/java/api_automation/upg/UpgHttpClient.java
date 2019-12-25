package api_automation.upg;

import api_automation.BaseClient;
import client.rest.model.RequestModel;
import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;
import util.ApplicationPropertyUtil;
import util.StringUtil;

import static client.rest.client.RestClient.GET;
import static client.rest.client.RestClient.POST;

public class UpgHttpClient extends BaseClient {
    private final static Logger LOGGER = Logger.getLogger(UpgHttpClient.class);

    public static void main(String[] args) {
        ResponseModel responseModel=getHost();
        String tkn = responseModel.getBody().split("value=\"")[1].split("\"")[0];
        System.out.println(responseModel.getCookiesMap());
        postLogin(tkn);
    }

    public static ResponseModel getHost() {
        RequestModel requestModel = baseHttpsGet();
        requestModel.setHost("upgcard.com.ua");
        requestModel.setPath("/");
        requestModel.setMethod(GET);
        return CLIENT.call(requestModel);
    }

    private static ResponseModel postLogin(String tkn) {
        RequestModel requestModel = baseHttpsGet();
        requestModel.setHost("upgcard.com.ua");
        requestModel.setPath("/");
        String login = ApplicationPropertyUtil.applicationPropertyGet("upg.login");
        String pass = ApplicationPropertyUtil.applicationPropertyGet("upg.pass");
        StringUtil.urlEncode(tkn);
        requestModel.setBody("tkn=" + tkn
                + "&MFormLogin[login]=" + login
                + "&MFormLogin[password]=" + pass);
        requestModel.setAllLog(true);
        requestModel.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestModel.setBodyEncoding("UTF-8");
        requestModel.setMethod(POST);
        ResponseModel responseModel=CLIENT.call(requestModel);
        System.out.println(responseModel.getCookiesMap());
        return responseModel;
    }
}
