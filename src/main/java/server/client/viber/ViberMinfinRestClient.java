package server.client.viber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import orm.entity.deal.Deal;
import orm.entity.deal.DealDAO;
import server.client.rest.client.ApacheRestClient;
import server.client.rest.model.RequestModel;
import server.client.viber.model.ViberMessage;
import ui_automation.Bank;

import static orm.entity.currency.CurrencyDAO.USD_CURRENCY;

import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class ViberMinfinRestClient {
    private static final ViberMinfinRestClient INSTANCE = new ViberMinfinRestClient();

    public static ViberMinfinRestClient getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Deal deal = DealDAO.getInstance().findAll().get((int) (DealDAO.getInstance().count() - 1));
        getInstance().sendDealMessage(deal, new Bank().balanceUSD(), new Bank().balanceUAH());
    }

    public void sendDealMessage(Deal deal, double usd, double uah) {
        ApacheRestClient apacheRestClient = new ApacheRestClient();
        String[] receiverArray = applicationPropertyGet("viber.receiver.list").split(",");
        for (String receiver : receiverArray) {
            RequestModel request = new RequestModel();
            request.setAllLog(true);
            request.setBody(body(deal, receiver, usd, uah));
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

    private String body(Deal deal, String receiver, double usd, double uah) {
        ViberMessage viberMessage = new ViberMessage();
        viberMessage.setReceiver(receiver);
        viberMessage.setText(text(deal, usd, uah));
        System.out.println(new JSONObject(viberMessage));
        // System.exit(0);
        return new JSONObject(viberMessage).toString();
    }

    private String text(Deal deal, double usd, double uah) {
        String type = deal.getCurrency().equals(USD_CURRENCY) ? "продаю" : "купую";
        String url = deal.getCurrency().equals(USD_CURRENCY) ? minfinAuctionURL("sell") : minfinAuctionURL("buy");
        return "(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)" + "\n"
                + type + " " + deal.getSum() + " по " + deal.getCurrencyRate() + " грн за доляр" + "\n"
                + "зараз у мене в касі :" + "\n"
                + "гривні : " + uah + "\n"
                + "доляра : " + usd + "\n"
                + "моє оголошення на цій сторінці :" + "\n"
                + url + "\n"
               // + "(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)" + "\n"
                ;
    }

    private String minfinAuctionURL(String type) {
        return "https://minfin.com.ua/currency/auction/usd/" + type + "/lvov";
    }
}
