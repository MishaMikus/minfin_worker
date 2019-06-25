package ui_automation;

import com.codeborne.selenide.Selenide;

import java.util.Date;

public class Trader {
//    public static void main(String[] args) {
//        sell();
//    }

    public static void sell() {
        System.out.println(new Date() + "==>>TRY to SELL");
        new LoginBO().login("tradingLviv", "dbb906");
        boolean wantToSell = new Cassa().wantToSell();
        if (wantToSell) {
            String sellCourse = new CalculateBO().getAverageDecreasedSell(1);
            new SellBO().sell("1000", sellCourse, "Центр, вул. Франка 40, початок Франка і Зеленої");
        }
        Selenide.close();
    }
}
