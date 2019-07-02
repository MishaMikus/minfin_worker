package ui_automation;

import orm.entity.currency.Currency;
import orm.entity.deal.Deal;
import orm.entity.deal.DealDAO;

import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class MyDealBO extends BaseBO {
    public void saveResults(String url, Currency currency) {
        String time = getMyProposalTime();
        Deal deal = new Deal();
        deal.setTime(time);
        deal.setUrl(url);
        deal.setDate(new Date());
        deal.setCurrencyRate(getMyCurrencyRate());
        deal.setCurrency(currency);
        deal.setSum(getMySum());
        deal.setMinfin_id(getMyMinfinId());
        deal.setMsg(getMyMessage());
        deal.setPhone(getMyPhone());
        new DealDAO().save(deal);
    }

    String getMyProposalTime() {
        if($(".au-delete-deal.js-au-delete-deal").isDisplayed()){
            return $(".au-delete-deal.js-au-delete-deal").parent().findAll(".au-deal-time").get(0).text();}
        else return null;
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
        return $(".au-delete-deal.js-au-delete-deal").parent().findAll(".au-deal-currency").get(0).text();
    }

}
