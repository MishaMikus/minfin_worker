package ui_automation.minfin;

import com.codeborne.selenide.Selenide;
import orm.entity.buy_price.BuyPrice;
import orm.entity.buy_price.BuyPriceDAO;
import orm.entity.deal.Deal;
import orm.entity.sell_price.SellPrice;
import orm.entity.sell_price.SellPriceDAO;
import server.dashboard.service.TradeStatusHelper;
import ui_automation.minfin.bo.BuyBO;
import ui_automation.minfin.bo.LoginBO;
import ui_automation.minfin.bo.MyDealBO;
import ui_automation.minfin.bo.SellBO;
import util.HTMLUtil;
import util.RegExpUtil;

import java.util.Date;
import java.util.List;

import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class Trader {
    private static final String ADDRESS = applicationPropertyGet("minfin.address");
    private static final String PRICE_REGEX = "<span class=\"au-deal-currency\">([\\d]+,[\\d]+)";


    public static void check() {
        if (login()) {
            try {
                List<Deal> myDealList = new MyDealBO().updateMyDealList();
                System.out.println(myDealList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void trade() {
        Selenide.close();
        tradeAction();
        refreshPrice();
    }

    private static void refreshPrice() {
        System.out.println("START refreshPrice");
        try {
            String sellContent = HTMLUtil.getContentByURL("https://minfin.com.ua/currency/auction/usd/sell/lvov");
            Double price_sell = getAverageFirst10Prices(RegExpUtil.findsByRegex(sellContent, PRICE_REGEX, 1));
            new SellPriceDAO().save(new SellPrice(price_sell));

            String buyContent = HTMLUtil.getContentByURL("https://minfin.com.ua/currency/auction/usd/buy/lvov");
            Double price_buy = getAverageFirst10Prices(RegExpUtil.findsByRegex(buyContent, PRICE_REGEX, 1));
            new BuyPriceDAO().save(new BuyPrice(price_buy));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CAN'T REFRESH PRICE");
        }
    }

    private static Double getAverageFirst10Prices(List<String> priceList) {
        Double average = 0d;
        priceList = priceList.subList(0, 10);
        Integer count = 0;
        for (String price : priceList) {
            average += Double.parseDouble(price.replaceAll(",", "."));
            count++;
        }
        return Math.round(average / count * 100) / 100d;
    }

    private static void tradeAction() {
        if (new TradeStatusHelper().isActiveTrading()) {
            if (startTrading()) {
                sellIfNeed();
                buyIfNeed();
            }}
    }

    private static boolean startTrading() {
        System.out.println(new Date() + "==>>TRY to TRADE");
        return login();
    }

    private static void buyIfNeed() {
        try {
            int wantToBuyAmount = new Bank().wantToBuy();
            if (wantToBuyAmount > 0) {
                String course = (new TradeStatusHelper().priceBuy() + 0.01) + "";
                new BuyBO().buy(wantToBuyAmount + "", course, ADDRESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CAN'T BUY");
        }
    }

    private static void sellIfNeed() {
        try {
            int wantToSellAmount = new Bank().wantToSell();
            if (wantToSellAmount > 0) {
                String course = (new TradeStatusHelper().priceSell() - 0.01) + "";
                new SellBO().sell(wantToSellAmount + "", course, ADDRESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CAN'T SELL");
        }
    }

    public static void deleteDeal() {
        if (login()) {
            new SellBO().gotoSellPage().deleteProposal();
            new BuyBO().gotoBuyPage().deleteProposal();
        }
    }

    private static boolean login() {
        return new LoginBO().login(applicationPropertyGet("minfin.user"), applicationPropertyGet("minfin.pass"));
    }
}
