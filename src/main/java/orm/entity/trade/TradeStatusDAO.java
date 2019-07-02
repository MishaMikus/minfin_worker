package orm.entity.trade;

import orm.entity.GenericAbstractDAO;

import java.util.Comparator;

public class TradeStatusDAO extends GenericAbstractDAO<TradeStatus> {

    public TradeStatusDAO() {
        super(TradeStatus.class);
    }

    private static final TradeStatusDAO INSTANCE = new TradeStatusDAO();

    public static TradeStatusDAO getInstance() {
        return INSTANCE;
    }

    public TradeStatus getLatestOpened() {
        TradeStatus res = findAll().stream().max(Comparator.comparing(TradeStatus::getStart_date)).orElse(null);
        System.out.println("getLatestOpened : " + res);
        return res;
    }
}
