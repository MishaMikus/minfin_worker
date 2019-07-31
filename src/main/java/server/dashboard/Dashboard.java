package server.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import server.BaseController;
import server.dashboard.service.DealTable;
import server.dashboard.service.TradeStatusHelper;
import server.dashboard.service.TransactionTable;
import server.dashboard.view.TransactionView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Dashboard extends BaseController {
    private final TradeStatusHelper tradeStatusHelper;
    private final DealTable dealTable;
    private final TransactionTable transactionTable;

    public Dashboard(TradeStatusHelper tradeStatusHelper, DealTable dealTable, TransactionTable transactionTable) {
        this.tradeStatusHelper = tradeStatusHelper;
        this.dealTable = dealTable;
        this.transactionTable = transactionTable;
    }

    @RequestMapping(value = "/delete/{id}")
    public String getDelete(@PathVariable Integer id) {
        transactionTable.delete(id);
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/price")
    public String postPrice(HttpServletRequest request) {
        String price_sell=request.getParameter("price_sell");
        String price_buy=request.getParameter("price_buy");

        System.out.println(price_sell);
        System.out.println(price_buy);

        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/add")
    public String postAdd(HttpServletRequest request) {
        transactionTable.add(request.getParameter("type"),
                request.getParameter("course"),
                request.getParameter("uah_action"),
                request.getParameter("usd_action"));
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/trade")
    public String getTrade() {
        tradeStatusHelper.pushTradeButton();
        return "redirect:/dashboard";
    }

    @RequestMapping(value = {"/dashboard","/"})
    public String getDashboard(HttpServletRequest request) {
        makeTransactionTableContent(request);
        request.getSession(true).setAttribute("dealTable", dealTable.dealTable());
        request.getSession(true).setAttribute("tradeStatus", tradeStatusHelper.actualTradeStatus());
        request.getSession(true).setAttribute("price_sell", tradeStatusHelper.priceSell());
        request.getSession(true).setAttribute("price_buy", tradeStatusHelper.priceBuy());
        return "dashboard";
    }

    private void makeTransactionTableContent(HttpServletRequest request) {
        List<TransactionView> transactionTableList = transactionTable.transactionTable();
        HttpSession session = request.getSession(true);
        if (transactionTableList.size() > 0) {
            session.setAttribute("lastTransactionView", transactionTableList.get(transactionTableList.size() - 1));

            transactionTableList.remove(transactionTableList.size() - 1);
            session.setAttribute("transactionTable", transactionTableList);
        } else {
            session.setAttribute("lastTransactionView", new TransactionView());
            session.setAttribute("transactionTable", new ArrayList<>());
        }
    }

}