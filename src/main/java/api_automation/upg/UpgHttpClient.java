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

    static ResponseModel getHomePage() {
        return upgGet("ua/owner");
    }

    public static ResponseModel getHost() {
        return upgGet("");
    }

    static ResponseModel getListPage() {
        return upgGet("ua/owner/account/list");
    }

    static ResponseModel upgGet(String path) {
        RequestModel requestModel = baseHttpsGet();
        requestModel.setHost("upgcard.com.ua");
        requestModel.setPath("/"+path);
        requestModel.setMethod(GET);
        return CLIENT.call(requestModel);
    }

    static ResponseModel postLogin(String tkn) {
        RequestModel requestModel = baseHttpsGet();
        requestModel.setHost("upgcard.com.ua");
        requestModel.setPath("/");
        String login = ApplicationPropertyUtil.applicationPropertyGet("upg.login");
        String pass = ApplicationPropertyUtil.applicationPropertyGet("upg.pass");
        requestModel.setBody("tkn=" + StringUtil.urlEncode(tkn)
                + "&"+StringUtil.urlEncode("MFormLogin[login]")+"=" + login
                + "&"+StringUtil.urlEncode("MFormLogin[password]")+"=" + StringUtil.urlEncode(pass));
        requestModel.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestModel.setBodyEncoding("UTF-8");
        requestModel.setMethod(POST);
        return CLIENT.call(requestModel);
    }
}
