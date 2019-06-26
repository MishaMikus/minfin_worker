package ui_automation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Bank {
    public boolean wantToSell() {

        String dealTime = new CalculateBO().getMyProposalTime();
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println("dealTime : " + dealTime + " ===>>> currentTime : " + currentTime);

        long dealTimeMS = Integer.parseInt(dealTime.split(":")[0]) * 60 * 60 * 1000L + Integer.parseInt(dealTime.split(":")[1]) * 60 * 1000L;
        long currentTimeMS = Integer.parseInt(currentTime.split(":")[0]) * 60 * 60 * 1000L + Integer.parseInt(currentTime.split(":")[1]) * 60 * 1000L;

        return (currentTimeMS - dealTimeMS) > (16 * 60 * 1000);
    }
}
