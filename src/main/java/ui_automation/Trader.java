package ui_automation;

import com.codeborne.selenide.Selenide;
import server.dashboard.service.TradeStatusHelper;

import java.util.Date;

public class Trader {

    public static void trade() {
        if (new TradeStatusHelper().isActiveTrading()) {
            System.out.println(new Date() + "==>>TRY to TRADE");
            new LoginBO().login("tradingLviv", "dbb906");

            String address = "Центр, вул. Франка, початок Франка і Зеленої";
            //sell
            int wantToSellAmount = new Bank().wantToSell();
            if (wantToSellAmount > 0) {
                String course = new CalculateBO().getAverageDecreasedSell(1);
                new SellBO().sell(wantToSellAmount + "", course, address);
            }

            //buy
            int wantToBuyAmount = new Bank().wantToBuy();
            if (wantToBuyAmount > 0) {
                String course = new CalculateBO().getAverageIncreasedBuy(1);
                new BuyBO().buy(wantToBuyAmount + "", course, address);
            }
            Selenide.close();
        }
    }
}
