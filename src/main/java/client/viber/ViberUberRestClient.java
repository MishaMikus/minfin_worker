package client.viber;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import client.viber.model.ViberMessage;
import server.logan_park.view.one_time_sms_reseiver.OneTimeSMSReceiverController;

import static util.ApplicationPropertyUtil.applicationPropertyGet;
import static util.SystemUtil.getMyIP;

public class ViberUberRestClient extends BaseViberRestClient{
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private static final ViberUberRestClient INSTANCE = new ViberUberRestClient();
    private String receiver = applicationPropertyGet("uber.viber.receiver.list");

    public static ViberUberRestClient getInstance() {
        return INSTANCE;
    }

    public void sendNeedSMSCodeRequest(){
        sendViberMessage(sendNeedSMSCodeRequestBody(receiver));
    }

    private String sendCaptchaBody(String receiver,String url) {
        ViberMessage viberMessage = new ViberMessage();
        viberMessage.setReceiver(receiver);
        viberMessage.setText("Need solve captcha\n"+url);
        return new JSONObject(viberMessage).toString();
    }

    private String sendNeedSMSCodeRequestBody(String receiver) {
        ViberMessage viberMessage = new ViberMessage();
        viberMessage.setReceiver(receiver);
        String url="http://"+getMyIP()+":8080/logan_park/one_time_sms_code";
        viberMessage.setText("Need SMS Code\n"+url);
        return new JSONObject(viberMessage).toString();
    }

    public void sendCaptcha(String url) {
        LOGGER.info("send viber : "+url);
        sendViberMessage(sendCaptchaBody(receiver,url));
    }
}
