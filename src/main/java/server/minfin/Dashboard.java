package server.minfin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import orm.entity.minfin.buy_price.BuyPrice;
import orm.entity.minfin.buy_price.BuyPriceDAO;
import orm.entity.minfin.sell_price.SellPrice;
import orm.entity.minfin.sell_price.SellPriceDAO;
import server.BaseController;
import server.minfin.service.DealTable;
import server.minfin.service.TradeStatusHelper;
import server.minfin.service.TransactionTable;
import server.minfin.view.TransactionView;

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
        String price_sell = request.getParameter("price_sell");
        String price_buy = request.getParameter("price_buy");
        try {
            new SellPriceDAO().save(new SellPrice(Double.parseDouble(price_sell)));
            new BuyPriceDAO().save(new BuyPrice(Double.parseDouble(price_buy)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(price_sell);
        System.out.println(price_buy);

        return "redirect:/minfin/dashboard";
    }

    @RequestMapping(value = "/add")
    public String postAdd(HttpServletRequest request) {
        transactionTable.add(request.getParameter("type"),
                request.getParameter("course"),
                request.getParameter("uah_action"),
                request.getParameter("usd_action"));
        return "redirect:/minfin/dashboard";
    }

    @RequestMapping(value = "/trade")
    public String getTrade() {
        tradeStatusHelper.pushTradeButton();
        return "redirect:/minfin/dashboard";
    }

    @RequestMapping(value = {"/minfin/dashboard"})
    public String getDashboard(HttpServletRequest request) {
        makeTransactionTableContent(request);
        request.getSession(true).setAttribute("dealTable", dealTable.dealTable());
        request.getSession(true).setAttribute("tradeStatus", tradeStatusHelper.actualTradeStatus());
        request.getSession(true).setAttribute("price_sell", tradeStatusHelper.priceSell() + "");
        request.getSession(true).setAttribute("price_buy", tradeStatusHelper.priceBuy() + "");

        request.getSession(true).setAttribute("price_sell_date", tradeStatusHelper.priceSellDate() + "");
        request.getSession(true).setAttribute("price_buy_date", tradeStatusHelper.priceBuyDate() + "");
        return "minfin/dashboard";
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