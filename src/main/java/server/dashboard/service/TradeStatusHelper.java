package server.dashboard.service;

import org.springframework.stereotype.Service;
import orm.entity.trade.TradeStatus;
import orm.entity.trade.TradeStatusDAO;
import server.dashboard.view.TradeStatusView;

import java.util.Date;
import java.util.List;

import static ui_automation.Trader.deleteDeal;

@Service
public class TradeStatusHelper {

    private TradeStatusDAO tradeStatusDAO = new TradeStatusDAO();

    private static final TradeStatusView STATUS_CLOSED = new TradeStatusView("lightgreen", "почати торги", "");
    private static final TradeStatusView STATUS_OPENED = new TradeStatusView("lightcoral", "закрити торги", "");

    public TradeStatusView actualTradeStatus() {
        List<TradeStatus> tradeStatusList = tradeStatusDAO.findAll();
        if (tradeStatusList.size() == 0) {
            return STATUS_CLOSED;
        } else {
            TradeStatus latest = tradeStatusDAO.getLatestOpened();
            if (latest == null || latest.getEnd_date() != null) {
                String latestComment = (latest == null ? "Останні торги відсутні." : "Останні торги #" + latest.getId() + " відкрито " + latest.getStart_date() + " ,закрито : " + latest.getEnd_date());
                STATUS_CLOSED.setMessage("Наразі ми не торгуємо. " + latestComment);
                return STATUS_CLOSED;
            } else {
                STATUS_OPENED.setMessage("Автоматичні тори #" + latest.getId() + " активовано " + latest.getStart_date());
                return STATUS_OPENED;
            }
        }
    }

    public boolean isActiveTrading() {
        TradeStatus latest = tradeStatusDAO.getLatestOpened();
        boolean res = !(latest == null || latest.getEnd_date() != null);
        System.out.println("isActiveTrading : " + res);
        return res;
    }

    public void pushTradeButton() {
        System.out.println("pushTradeButton");
        TradeStatus latest = tradeStatusDAO.getLatestOpened();
        if (tradeStatusDAO.getLatestOpened() == null || latest.getEnd_date() != null) {
            //Start new Trading
            tradeStatusDAO.save(new TradeStatus(new Date()));
        } else {
            //Close trading
            latest.setEnd_date(new Date());
            tradeStatusDAO.update(latest);
            deleteDeal();
        }
    }


}
