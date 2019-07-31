package ui_automation.minfin;

import com.codeborne.selenide.Selenide;
import orm.entity.deal.Deal;
import server.dashboard.service.TradeStatusHelper;

import java.util.Date;
import java.util.List;

import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class Trader {

//    public static void main(String[] args) {
//        check();
//    }

    public static void check() {
        try {
            new LoginBO().login(applicationPropertyGet("minfin.user"), applicationPropertyGet("minfin.pass"));
            List<Deal> myDealList = new MyDealBO().updateMyDealList();
            System.out.println(myDealList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void trade() {
        check();
        tradeAction();
        Selenide.close();
    }

    private static void tradeAction() {
        try {
            if (new TradeStatusHelper().isActiveTrading()) {
                System.out.println(new Date() + "==>>TRY to TRADE");
                new LoginBO().login(applicationPropertyGet("minfin.user"), applicationPropertyGet("minfin.pass"));

                String address = applicationPropertyGet("minfin.address");
                //sell
                int wantToSellAmount = new Bank().wantToSell();
                if (wantToSellAmount > 0) {
                    String course = new TradeStatusHelper().priceSell()+"";
                    new SellBO().sell(wantToSellAmount + "", course, address);
                }

                //buy
                int wantToBuyAmount = new Bank().wantToBuy();
                if (wantToBuyAmount > 0) {
                    String course = new CalculateBO().getAverageIncreasedBuy(1);
                    new BuyBO().buy(wantToBuyAmount + "", course, address);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteDeal() {
        new LoginBO().login(applicationPropertyGet("minfin.user"), applicationPropertyGet("minfin.pass"));
        new SellBO().gotoSellPage().deleteProposal();
        new BuyBO().gotoBuyPage().deleteProposal();
    }
}
