package ui_automation;

import com.codeborne.selenide.Selenide;

import java.util.Date;

public class Trader {

    public static void trade() {
        System.out.println(new Date() + "==>>TRY to TRADE");
        new LoginBO().login("tradingLviv", "dbb906");

        //sell
        int wantToSellAmount = new Bank().wantToSell();
        if (wantToSellAmount > 0) {
            String course = new CalculateBO().getAverageDecreasedSell(1);
            new SellBO().sell(wantToSellAmount + "", course, "Центр, вул. Франка 40, початок Франка і Зеленої");
        }

        //buy
        int wantToBuyAmount = new Bank().wantToBuy();
        if (wantToBuyAmount > 0) {
            String course = new CalculateBO().getAverageIncreasedBuy(0);
            new BuyBO().buy(wantToBuyAmount + "", course, "Центр, вул. Франка 40, початок Франка і Зеленої");
        }
        Selenide.close();
    }
}
