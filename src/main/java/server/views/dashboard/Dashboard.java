package server.views.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import server.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class Dashboard extends BaseController {
    @RequestMapping(value = "/dashboard")
    public String getDashboard(HttpServletRequest request) {
        makeTransactionTableContent(request);

        request.getSession(true).setAttribute("dealTable", new DealTable().dealTable());

        request.getSession(true).setAttribute("tradeStatus", new TradeStatusHelper().actualTradeStatus());

        return "dashboard";
    }

    private void makeTransactionTableContent(HttpServletRequest request) {
        List<TransactionView> transactionTable = new TransactionTable().transactionTable();
        if (transactionTable.size() > 0) {
            request.getSession(true).setAttribute("lastTransactionView", transactionTable.get(transactionTable.size() - 1));

            transactionTable.remove(transactionTable.size() - 1);
            request.getSession(true).setAttribute("transactionTable", transactionTable);
        }
    }

}