package ui_automation;

import com.codeborne.selenide.SelenideElement;
import orm.entity.currency.Currency;
import orm.entity.deal.Deal;
import orm.entity.deal.DealDAO;
import server.client.viber.ViberMinfinRestClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static orm.entity.currency.CurrencyDAO.UAH_CURRENCY;
import static orm.entity.currency.CurrencyDAO.USD_CURRENCY;
import static ui_automation.Bank.LOCAL_DELTA_TIME_MS;

public class MyDealBO extends BaseBO {
    public void saveResults(String url, Currency currency) {
        String time = getMyProposalTime();
        Deal deal = new Deal();
        deal.setTime(time);
        deal.setUrl(url);
        deal.setDate(new Date(new Date().getTime() + LOCAL_DELTA_TIME_MS));
        deal.setCurrencyRate(getMyCurrencyRate());
        deal.setCurrency(currency);
        deal.setSum(getMySum());
        deal.setMinfin_id(getMyMinfinId());
        deal.setMsg(getMyMessage());
        deal.setPhone(getMyPhone());
        new DealDAO().save(deal);
        ViberMinfinRestClient.getInstance().sendDealMessage(deal, new Bank().balanceUSD(), new Bank().balanceUAH());
    }

    String getMyProposalTime() {
        if ($(".au-delete-deal.js-au-delete-deal").isDisplayed()) {
            return $(".au-delete-deal.js-au-delete-deal").parent().findAll(".au-deal-time").get(0).text();
        } else return null;
    }

    private String getMySum() {
        return $(".au-delete-deal.js-au-delete-deal").parent().findAll(".au-deal-sum").get(0).text();
    }

    private String getMyPhone() {
        return $(".au-delete-deal.js-au-delete-deal").parent().findAll(".au-dealer-phone").get(0).text();
    }

    private String getMyMessage() {
        return $(".au-delete-deal.js-au-delete-deal").parent().findAll(".au-deal-msg").get(0).text();
    }

    private String getMyMinfinId() {
        return $(".au-delete-deal.js-au-delete-deal").getAttribute("data-id");
    }

    private String getMyCurrencyRate() {
        if ($(".au-delete-deal.js-au-delete-deal").isDisplayed()) {
            return $(".au-delete-deal.js-au-delete-deal").parent().findAll(".au-deal-currency").get(0).text();
        } else return null;
    }

    public List<Deal> updateMyDealList() {
        goToPath("/currency/auction/list");
        List<Deal> res = new ArrayList<>();
        for (SelenideElement dealTad : $(".au-my-deals-wrap.js-myDealsWrap").findAll(".au-my-deal.js-au-deal")) {
            String id = dealTad.findAll(".au-refresh-btn.js-au-refresh-deal").get(0).attr("data-bid");
            Deal deal = DealDAO.getInstance().findWhereEqual("minfin_id", id);
            if (deal != null) {
                deal.setWatchCount(
                        Integer.parseInt(dealTad.findAll(".au-deal-views.icon-eye-1").get(0).text().replaceAll("\\D", "")));
                deal.setActive(true);
                res.add(deal);
                DealDAO.getInstance().update(deal);
            } else {
                deal = new Deal();
                deal.setMinfin_id(id);
                deal.setWatchCount(
                        Integer.parseInt(dealTad.findAll(".au-deal-views.icon-eye-1").get(0).text().replaceAll("\\D", "")));
                deal.setActive(true);
                deal.setTime(dealTad.findAll(".au-deal-time").get(0).text().split("\n")[0].trim());
                String sum = dealTad.findAll(".au-deal-sum").get(0).text();
                deal.setSum(sum.split("\n")[1]);
                deal.setCurrencyRate(sum.split("\n")[2]);
                deal.setMsg(dealTad.findAll(".au-deal-msg").get(0).text());
                deal.setDate(new Date());
                deal.setPhone("MyPhone");
                System.out.println("deal.setCurrency : " + sum.split("\n")[0]);
                deal.setCurrency(sum.split("\n")[0].equals("Продам") ? USD_CURRENCY : UAH_CURRENCY);
                DealDAO.getInstance().save(deal);
                res.add(deal);
            }
        }
        return res;
    }
}
