package server.dashboard.service;

import org.springframework.stereotype.Service;
import orm.entity.deal.Deal;
import orm.entity.deal.DealDAO;
import server.dashboard.view.DealView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static orm.entity.currency.CurrencyDAO.USD_CURRENCY;

@Service
public
class DealTable {

    private static final int TABLE_ROW_LIMIT = 10;

    public List<DealView> dealTable() {
        List<DealView> dealTable = new ArrayList<>();
        List<Deal> dealList = new DealDAO().findAll();
        dealList.sort(Comparator.comparing(Deal::getDate));
        for (Deal deal : dealList) {
            DealView dealView = new DealView();
            dealView.setCourse(deal.getCurrencyRate());
            dealView.setDate(deal.getDate() + "");
            dealView.setType(deal.getCurrency().equals(USD_CURRENCY) ? "продаю долар" : "купую долар");
            dealView.setUsd(deal.getSum());
            dealTable.add(dealView);
        }
        Collections.reverse(dealTable);
        dealList.subList(0, dealList.size() > TABLE_ROW_LIMIT ? TABLE_ROW_LIMIT : dealList.size() - 1);
        return dealTable;
    }
}