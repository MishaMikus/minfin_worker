package ui_automation;

import org.json.JSONObject;
import orm.entity.deal.Deal;
import server.rest.rest.client.ApacheRestClient;
import server.rest.rest.model.RequestModel;

import static orm.entity.currency.CurrencyDAO.USD_CURRENCY;

import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class ViberMinfinRestClient {
    private static final ViberMinfinRestClient INSTANCE = new ViberMinfinRestClient();

    public static ViberMinfinRestClient getInstance() {
        return INSTANCE;
    }

//    public static void main(String[] args) {
//        Deal deal = DealDAO.getInstance().findAll().get(0);
//        getInstance().sendDealMessage(deal);
//    }

    public void sendDealMessage(Deal deal) {
        ApacheRestClient apacheRestClient = new ApacheRestClient();
        String[] receiverArray = applicationPropertyGet("viber.receiver.list").split(",");
        for (String receiver : receiverArray) {
            RequestModel request = new RequestModel();
            request.setAllLog(true);
            request.setBody(body(deal, receiver));
            request.setHost("chatapi.viber.com");
            request.setBodyEncoding("UTF-8");
            request.setProtocol("https://");
            request.setPath("/pa/send_message");
            request.setMethod("POST");
            String type = "application/json";
            request.addHeader("Content-Type", type);
            request.addHeader("X-Viber-Auth-Token", "49ede17d8067d51b-d7158ec52351c82d-b6c36f3fef30b321");
            System.out.println(apacheRestClient.call(request).getBody());
        }

    }

    private Object body(Deal deal, String receiver) {
        JSONObject body = new JSONObject();
        JSONObject sender = new JSONObject();
        sender.put("name", "MinfinBot");
        sender.put("avatar", "https://dl-media.viber.com/1/share/2/long/vibes/icon/image/0x0/40e4/33c1ccfc1991b38eddcfa93841e75a8bc85a2e56e0e79c3cd9c866cb60b840e4.jpg");
        body.put("receiver", receiver);
        body.put("min_api_version", 1);
        body.put("tracking_data", "tracking data");
        body.put("type", "text");
        String type = deal.getCurrency().equals(USD_CURRENCY) ? "SELL" : "BUY";
        String url = deal.getCurrency().equals(USD_CURRENCY) ? "https://minfin.com.ua/currency/auction/usd/sell/lvov" : "https://minfin.com.ua/currency/auction/usd/buy/lvov";
        body.put("text", type + " " + deal.getSum() + " " + deal.getCurrencyRate() + "\n" + url);
        body.put("sender", sender);
        return body.toString();
    }
}
