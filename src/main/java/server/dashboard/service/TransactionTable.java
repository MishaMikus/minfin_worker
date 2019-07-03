package server.dashboard.service;

import org.springframework.stereotype.Service;
import orm.entity.transaction.Transaction;
import orm.entity.transaction.TransactionDAO;
import orm.entity.transaction.TransactionType;
import orm.entity.transaction.TransactionTypeDAO;
import server.dashboard.view.TransactionView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public
class TransactionTable {


    public List<TransactionView> transactionTable() {
        List<TransactionView> transactionTable = new ArrayList<>();
        List<Transaction> transactionList = TransactionDAO.getInstance().findAll();
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

    public void add(String type, String course, String uah_action, String usd_action) {

        TransactionType transactionType = findTransactionType(type);
        System.out.println("type : " + type + " -> " + transactionType);

        Double courseDouble = parseCourse(course);
        System.out.println("course : " + course + " -> " + courseDouble);

        Integer uah_actionInteger = parseInteger(uah_action);
        System.out.println("uah_action : " + uah_action + " -> " + uah_actionInteger);

        Integer usd_actionInteger = parseInteger(usd_action);
        System.out.println("usd_action : " + usd_action + " -> " + usd_actionInteger);

        try {
            Transaction transaction = new Transaction();
            transaction.setType(transactionType);
            transaction.setCurrency_rate(courseDouble);
            transaction.setChange_uah(uah_actionInteger);
            transaction.setChange_usd(usd_actionInteger);
            transaction.setDate(new Date());

            if (TransactionDAO.getInstance().count() == 0) {
                transaction.setUah_before(0);
                transaction.setUsd_before(0);
                transaction.setUsd_after(usd_actionInteger);
                transaction.setUah_after(uah_actionInteger);
            } else {
                Integer sumUah = TransactionDAO.getInstance().sum("change_uah").intValue();
                Integer sumUsd = TransactionDAO.getInstance().sum("change_usd").intValue();

                transaction.setUah_before(sumUah);
                transaction.setUah_after(sumUah + uah_actionInteger);

                transaction.setUsd_before(sumUsd);
                transaction.setUsd_after(sumUsd + usd_actionInteger);
            }

            TransactionDAO.getInstance().save(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer parseInteger(String integerString) {
        try {
            return Integer.parseInt(integerString);
        } catch (Exception e) {
            return 0;
        }
    }

    private Double parseCourse(String course) {
        try {
            return Double.parseDouble(course);
        } catch (Exception e) {
            return 0d;
        }
    }

    private TransactionType findTransactionType(String type) {
        return TransactionTypeDAO.getInstance().findWhereEqual("name", type);
    }

    public void delete(Integer id) {
        TransactionDAO.getInstance().deleteById(id);
    }
}
