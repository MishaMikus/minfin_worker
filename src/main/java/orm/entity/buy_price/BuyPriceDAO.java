package orm.entity.buy_price;

import orm.entity.GenericAbstractDAO;
import orm.entity.transaction.Transaction;

import java.util.Comparator;

public class BuyPriceDAO extends GenericAbstractDAO<BuyPrice> {

    public BuyPriceDAO() {
        super(BuyPrice.class);
    }

    private static final BuyPriceDAO INSTANCE = new BuyPriceDAO();

    public static BuyPriceDAO getInstance() {
        return INSTANCE;
    }

    public BuyPrice getLatest() {
        return findAll().stream().max(Comparator.comparing(BuyPrice::getDate)).orElse(null);
    }
}
