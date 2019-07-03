package ui_automation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class Bank {
    public static final Long LOCAL_DELTA_TIME_MS=Integer.parseInt(applicationPropertyGet("time.delta.hours")) * 1000 * 60 * 60L;
    public int wantToSell() {
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

        return (currentTimeMS - dealTimeMS) > (16 * 60 * 1000);
    }


    public int wantToBuy() {
        String dealTime = new BuyBO().gotoBuyPage().getMyProposalTime();
        if (theTimeHasCome(dealTime)) {
            return buyAmount();
        } else return 0;
    }

    private int buyAmount() {
        //TODO depends on CASSA
        return 1000;
        //return 0;
    }

    private int sellAmount() {
        //TODO depends on CASSA
        return 0;
        //return 1000;
    }
}
