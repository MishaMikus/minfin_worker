package orm.entity.buy_price;

import orm.entity.GenericAbstractDAO;

public class BuyPriceDAO extends GenericAbstractDAO<BuyPrice> {

    public BuyPriceDAO() {
        super(BuyPrice.class);
    }

    private static final BuyPriceDAO INSTANCE = new BuyPriceDAO();

    public static BuyPriceDAO getInstance() {
        return INSTANCE;
    }
}
