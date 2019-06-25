package ui_automation;

import static com.codeborne.selenide.Selenide.$;

public class CalculateBO extends BaseBO {

    public String getAverageSell() {
        goToPath("/currency/auction/usd/sell/lvov/");
        String avSell = $(".au-status--group---wrapper.au-status--group").findAll(".au-mid-buysell").get(1).text();
        return avSell.substring(avSell.indexOf(":") + 1, avSell.indexOf("грн")).trim();
    }

    public String getAverageDecreasedSell(int decreaseCoin) {
        String av = getAverageSell();
        String uah = av.split(",")[0];
        String coin = av.split(",")[1];
        Integer coinInt = Integer.parseInt(coin) + Integer.parseInt(uah) * 100;
        coinInt -= decreaseCoin;
        uah = (coinInt / 100) + "";
        coin = String.format("%02d", coinInt % 100);
        return uah + "." + coin;
    }
}
