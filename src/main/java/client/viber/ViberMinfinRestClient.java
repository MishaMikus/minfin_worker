package client.viber;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import orm.entity.minfin.deal.Deal;
import client.viber.model.ViberMessage;

import static orm.entity.minfin.currency.CurrencyDAO.USD_CURRENCY;
import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class ViberMinfinRestClient extends BaseViberRestClient{
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private static final ViberMinfinRestClient INSTANCE = new ViberMinfinRestClient();
    private String[] receiverArray = applicationPropertyGet("viber.receiver.list").split(",");

    public static ViberMinfinRestClient getInstance() {
        return INSTANCE;
    }

//    public static void main(String[] args) {
//        Deal deal = DealDAO.getInstance().findAll().get((int) (DealDAO.getInstance().count() - 1));
//        getInstance().sendStartMessage(new Bank().balanceUSD(), new Bank().balanceUAH());
//        getInstance().sendDealMessage(deal, new Bank().balanceUSD(), new Bank().balanceUAH());
//        getInstance().sendEndMessage(new Bank().balanceUSD(), new Bank().balanceUAH());
//    }

    public void sendEndMessage(int balanceUSD, int balanceUAH) {
        for (String receiver : receiverArray) {
            sendViberMessage(endBody(receiver, balanceUSD, balanceUAH));
        }
    }

    private String endBody(String receiver, int balanceUSD, int balanceUAH) {
        ViberMessage viberMessage = new ViberMessage();
        viberMessage.setReceiver(receiver);
        viberMessage.setText(endText(balanceUSD, balanceUAH));
        return new JSONObject(viberMessage).toString();
    }

    private String endText(int balanceUSD, int balanceUAH) {
        return "(do_not_enter)(do_not_enter)(do_not_enter)(do_not_enter)" +
                "(do_not_enter)(do_not_enter)(do_not_enter)(do_not_enter)" +
                "(do_not_enter)(do_not_enter)(do_not_enter)(do_not_enter)" + "\n"
                + "Закінчив торгувати!" + "\n"
                + "Зараз у мене в касі :" + "\n"
                + "Гривні : " + balanceUAH + "\n"
                + "Доляра : " + balanceUSD + "\n"
                + randomSmiles();
    }

    public void sendStartMessage(double balanceUSD, double balanceUAH) {
        for (String receiver : receiverArray) {
            sendViberMessage(startBody(receiver, balanceUSD, balanceUAH));
        }
    }

    private String startBody(String receiver, double balanceUSD, double balanceUAH) {
        ViberMessage viberMessage = new ViberMessage();
        viberMessage.setReceiver(receiver);
        viberMessage.setText(startText(balanceUSD, balanceUAH));
        return new JSONObject(viberMessage).toString();
    }

    private String startText(double balanceUSD, double balanceUAH) {
        return "(rocket)(rocket)(rocket)(rocket)(rocket)(rocket)(rocket)(rocket)(rocket)(rocket)(rocket)(rocket)" + "\n"
                + "Починаю торгувати!" + "\n"
                + "Зараз у мене в касі :" + "\n"
                + "Гривні : " + balanceUAH + "\n"
                + "Доляра : " + balanceUSD + "\n"
                + randomSmiles();
    }

    public void sendDealMessage(Deal deal, double usd, double uah) {
        String[] receiverArray = applicationPropertyGet("viber.receiver.list").split(",");
        for (String receiver : receiverArray) {
            sendViberMessage(dealBody(deal, receiver, usd, uah));
        }
    }

    private String dealBody(Deal deal, String receiver, double usd, double uah) {
        ViberMessage viberMessage = new ViberMessage();
        viberMessage.setReceiver(receiver);
        viberMessage.setText(text(deal, usd, uah));
        return new JSONObject(viberMessage).toString();
    }

    private String text(Deal deal, double usd, double uah) {
        String type = deal.getCurrency().equals(USD_CURRENCY) ? "Продаю" : "Купую";
        String url = deal.getCurrency().equals(USD_CURRENCY) ? minfinAuctionURL("sell") : minfinAuctionURL("buy");
        return "(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)(money)(moneybag)" + "\n"
                + type + " " + deal.getSum() + " по " + deal.getCurrencyRate() + " грн за доляр" + "\n"
                + "Зараз у мене в касі :" + "\n"
                + "Гривні : " + uah + "\n"
                + "Доляра : " + usd + "\n"
                + "Моє оголошення на цій сторінці :" + "\n"
                + url + "\n" +
                randomSmiles()
                ;
    }

    private String minfinAuctionURL(String type) {
        return "https://minfin.com.ua/currency/auction/usd/" + type + "/lvov";
    }
}
