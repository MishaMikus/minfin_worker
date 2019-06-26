package ui_automation;

import static orm.entity.currency.CurrencyDAO.UAH_CURRENCY;

public class BuyBO extends MyDealBO {
    private final static String url = "/currency/auction/usd/buy/lvov";

    public void buy(String amount, String course, String message) {
        gotoBuyPage();
        deleteProposal();
        new NewBO().create("buy", amount, course, message);
        gotoBuyPage();
        new MyDealBO().saveResults(url, UAH_CURRENCY);
    }

    BuyBO gotoBuyPage() {
        goToPath(url);
        return this;
    }
}
