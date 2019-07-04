package server.client.viber.model;

import static server.client.viber.model.ViberSender.MINFIN_BOT;

public class ViberMessage {
    //https://developers.viber.com/docs/api/rest-bot-api
    private String receiver;
    private Integer min_api_version=1;
    private ViberSender sender=MINFIN_BOT;
    private String tracking_data="tracking data";
    private String type="text";
    private String text;



    public Integer getMin_api_version() {
        return min_api_version;
    }

    public void setMin_api_version(Integer min_api_version) {
        this.min_api_version = min_api_version;
    }

    public ViberSender getSender() {
        return sender;
    }

    public void setSender(ViberSender sender) {
        this.sender = sender;
    }

    public String getTracking_data() {
        return tracking_data;
    }

    public void setTracking_data(String tracking_data) {
        this.tracking_data = tracking_data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
