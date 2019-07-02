package server.views.dashboard;

import orm.entity.transaction.Transaction;
import orm.entity.transaction.TransactionDAO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class TransactionTable {
    List<TransactionView> transactionTable() {
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
