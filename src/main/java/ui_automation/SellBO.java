package ui_automation;

import static com.codeborne.selenide.Selenide.$;
import static orm.entity.currency.CurrencyDAO.USD_CURRENCY;

public class SellBO extends MyDealBO {
    private final static String url = "/currency/auction/usd/sell/lvov";

    public void sell(String amount, String course, String message) {
        gotoSellPage();
        deleteProposal();
        new NewBO().create("sell", amount, course, message);
        gotoSellPage();
        new MyDealBO().saveResults(url, USD_CURRENCY);
    }

    SellBO gotoSellPage() {
        goToPath(url);
        return this;
    }

    public String getAverageMinfinSell() {
        String avSell = $(".au-status--group---wrapper.au-status--group").findAll(".au-mid-buysell").get(1).text();
        return avSell.substring(avSell.indexOf(":") + 1, avSell.indexOf("грн")).trim();
    }

    public String getAverageSellByLatestDeal() {

        return "";
    }
}

