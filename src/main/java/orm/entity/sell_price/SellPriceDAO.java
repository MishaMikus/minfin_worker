package orm.entity.sell_price;

import orm.entity.GenericAbstractDAO;
import orm.entity.buy_price.BuyPrice;

import java.util.Comparator;

public class SellPriceDAO extends GenericAbstractDAO<SellPrice> {

    public SellPriceDAO() {
        super(SellPrice.class);
    }

    private static final SellPriceDAO INSTANCE = new SellPriceDAO();

    public static SellPriceDAO getInstance() {
        return INSTANCE;
    }

    public SellPrice getLatest() {
        return findAll().stream().max(Comparator.comparing(SellPrice::getDate)).orElse(null);
    }
}
