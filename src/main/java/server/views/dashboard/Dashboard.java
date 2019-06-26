package server.views.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import orm.entity.deal.Deal;
import orm.entity.deal.DealDAO;
import orm.entity.transaction.Transaction;
import orm.entity.transaction.TransactionDAO;
import server.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static orm.entity.currency.CurrencyDAO.USD_CURRENCY;

@Controller
public class Dashboard extends BaseController {
    @RequestMapping(value = "/dashboard")
    public String getDashboard(HttpServletRequest request, HttpServletResponse response) {
        addTransactionTable(request);
        addDealTable(request);
        return "dashboard";
    }

    private void addDealTable(HttpServletRequest request) {
        request.getSession(true).setAttribute("dealTable", dealTable());
    }

    private List<DealView> dealTable() {
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
        return dealTable;
    }

    private void addTransactionTable(HttpServletRequest request) {
        List<TransactionView> transactionTable = transactionTable();
        request.getSession(true).setAttribute("lastTransactionView", transactionTable.get(transactionTable.size() - 1));
        transactionTable.remove(transactionTable.size() - 1);
        request.getSession(true).setAttribute("transactionTable", transactionTable);
    }

    private List<TransactionView> transactionTable() {
        List<TransactionView> transactionTable = new ArrayList<>();
        List<Transaction> transactionList = new TransactionDAO().findAll();
        transactionList.sort(Comparator.comparing(Transaction::getDate));
        for (Transaction transaction : transactionList) {
            TransactionView transactionView = new TransactionView();
            transactionView.setDate(transaction.getDate() + "");
            transactionView.setCourse(transaction.getCurrency_rate() + "");

            String transactionString = transaction.getType().getFrom().getName() + "->" + transaction.getType().getTo().getName();
            if (transaction.getType().getFrom().getId() == transaction.getType().getTo().getId()) {
                transactionString = "invest_" + transaction.getType().getFrom().getName();
            }
            transactionView.setTransaction(transactionString);

            transactionView.setUah(transaction.getUah_after() + "");
            transactionView.setUsd(transaction.getUsd_after() + "");

            transactionView.setUsd_change(transaction.getChange_usd() + "");
            transactionView.setUah_change(transaction.getChange_uah() + "");

            transactionView.setId(transaction.getId() + "");

            transactionTable.add(transactionView);

        }

        return transactionTable;
    }
}