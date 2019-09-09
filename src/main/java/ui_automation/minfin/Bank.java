package ui_automation.minfin;

import orm.entity.minfin.buy_price.BuyPriceDAO;
import orm.entity.minfin.transaction.TransactionDAO;
import ui_automation.minfin.bo.BuyBO;
import ui_automation.minfin.bo.SellBO;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class Bank {
    public static final Long LOCAL_DELTA_TIME_MS = Integer.parseInt(applicationPropertyGet("time.delta.hours")) * 1000 * 60 * 60L;
    private static final Long DEAL_LIFE_TIME_MS = Integer.parseInt(applicationPropertyGet("deal.life.lime.minutes")) * 1000 * 60L;

    int wantToSell() {
        String dealTime = new SellBO().gotoSellPage().getMyProposalTime();
        if (theTimeHasCome(dealTime)) {
            return sellAmount();
        } else return 0;
    }

    private boolean theTimeHasCome(String dealTime) {

        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        System.out.println("dealTime : " + dealTime + " ===>>> currentTime : " + currentTime);
        long dealTimeMS = 0;
        if (dealTime != null) {
            dealTimeMS = Integer.parseInt(dealTime.split(":")[0]) * 60 * 60 * 1000L + Integer.parseInt(dealTime.split(":")[1]) * 60 * 1000L;
        }

        long currentTimeMS = LOCAL_DELTA_TIME_MS + Integer.parseInt(currentTime.split(":")[0]) * 60 * 60 * 1000L + Integer.parseInt(currentTime.split(":")[1]) * 60 * 1000L;
        long timeElapsed = (currentTimeMS - dealTimeMS);
        System.out.println("timeElapsed MS : " + timeElapsed);
        boolean res = timeElapsed > DEAL_LIFE_TIME_MS;
        System.out.println("theTimeHasCome : " + res);
        return res;
    }


    public int wantToBuy() {
        String dealTime = new BuyBO().gotoBuyPage().getMyProposalTime();
        if (theTimeHasCome(dealTime)) {
            return buyAmount();
        } else return 0;
    }

    private int buyAmount() {
        int balanceUAH=balanceUAH();

        double rate= BuyPriceDAO.getInstance().getLatest().getPrice();

        double usd=((double) balanceUAH) / rate;

        int res=(int) (Math.round(usd / 1000d) * 1000d);

        return res;
    }

    private int sellAmount() {
        return Math.round(balanceUSD() / 1000) * 1000;
    }


    public int balanceUAH() {
        return balance("change_uah");
    }

    public int balanceUSD() {
        return balance("change_usd");
    }

    private int balance(String wallet) {
        return TransactionDAO.getInstance().sum(wallet).intValue();
    }
}
