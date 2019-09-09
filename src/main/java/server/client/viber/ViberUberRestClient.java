package server.client.viber;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import server.client.viber.model.ViberMessage;
import server.logan_park.view.one_time_sms_reseiver.OneTimeSMSReceiver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static util.ApplicationPropertyUtil.applicationPropertyGet;

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
    private String sendNeedSMSCodeRequestBody(String receiver) {
        ViberMessage viberMessage = new ViberMessage();
        viberMessage.setReceiver(receiver);
        InetAddress IP= null;
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("IP of my system is := "+IP.getHostAddress());
        //http://192.168.7.147:8080/one_time_sms_code
        String url="http://"+IP.getHostAddress()+":8080"+ OneTimeSMSReceiver.PATH;
        viberMessage.setText("Need SMS Code\n"+url);
        return new JSONObject(viberMessage).toString();
    }

    public static void main(String[] args) {
        getInstance().sendNeedSMSCodeRequest();
    }
}
