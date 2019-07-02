package server.views.dashboard;

import org.springframework.stereotype.Service;
import orm.entity.trade.TradeStatus;
import orm.entity.trade.TradeStatusDAO;

import java.util.List;

@Service
public class TradeStatusHelper {

    private TradeStatusDAO tradeStatusDAO = new TradeStatusDAO();

    private static final TradeStatusView STATUS_CLOSED = new TradeStatusView("lightgreen", "почати торги", "Наразі ми не торгуємо");
    private static final TradeStatusView STATUS_OPENED = new TradeStatusView("lightred", "закрити торги", "");

    public TradeStatusView actualTradeStatus() {
        List<TradeStatus> tradeStatusList = tradeStatusDAO.findAll();
        if (tradeStatusList.size() == 0) {
            return STATUS_CLOSED;
        } else {
            TradeStatus tradeStatus = tradeStatusDAO.getLatestOpened();
            if (tradeStatus == null) {
                return STATUS_CLOSED;
            } else {
                STATUS_OPENED.setMessage("Автоматичні тори #" + tradeStatus.getId() + " активовано " + tradeStatus.getStart());
                return STATUS_OPENED;
            }
        }
    }

    public boolean isActiveTrading() {
        TradeStatus tradeStatus = tradeStatusDAO.getLatestOpened();
        //return tradeStatus != null && tradeStatus.getEnd() == null;
        //TODO
        return true;
    }
}
