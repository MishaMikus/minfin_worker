package ui_automation.minfin;

import static com.codeborne.selenide.Selenide.$;

public class CalculateBO extends BaseBO {

    public String getAverageSell() {
        return new SellBO().gotoSellPage().getAverageMinfinSell();
        //return new SellBO().gotoSellPage().getAverageSellByLatestDeal();
    }

    public String getAverageDecreasedSell(int decreaseCoin) {
        String av = getAverageSell();
        return change(av, decreaseCoin, -1);
    }

    private String change(String av, int decreaseCoin, int side) {
        String uah = av.split(",")[0];
        String coin = av.split(",")[1];
        Integer coinInt = Integer.parseInt(coin) + Integer.parseInt(uah) * 100;
        coinInt += decreaseCoin * side;
        uah = (coinInt / 100) + "";
        coin = String.format("%02d", coinInt % 100);
        return uah + "." + coin;
    }

    public String getAverageIncreasedBuy(int increaseCoin) {
        String av = getAverageBuy();
        return change(av, increaseCoin, +1);
    }

    private String getAverageBuy() {
        new BuyBO().gotoBuyPage();
        String avBuy = $(".au-status--group---wrapper.au-status--group").findAll(".au-mid-buysell").get(0).text();
        System.out.println("avBuy : "+avBuy);
        return avBuy.substring(avBuy.indexOf(":") + 1, avBuy.indexOf("грн")).trim();
    }
}
