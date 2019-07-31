package orm.entity.sell_price;

import orm.entity.GenericAbstractDAO;

public class SellPriceDAO extends GenericAbstractDAO<SellPrice> {

    public SellPriceDAO() {
        super(SellPrice.class);
    }

    private static final SellPriceDAO INSTANCE = new SellPriceDAO();

    public static SellPriceDAO getInstance() {
        return INSTANCE;
    }
}
