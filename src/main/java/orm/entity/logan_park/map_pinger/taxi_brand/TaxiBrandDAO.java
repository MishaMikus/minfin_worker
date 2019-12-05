package orm.entity.logan_park.map_pinger.taxi_brand;

import orm.entity.GenericAbstractDAO;

public class TaxiBrandDAO extends GenericAbstractDAO<TaxiBrand> {

    public TaxiBrandDAO() {
        super(TaxiBrand.class);
    }

    private static final TaxiBrandDAO INSTANCE = new TaxiBrandDAO();

    public static TaxiBrandDAO getInstance() {
        return INSTANCE;
    }

    public static final TaxiBrand UBER = getInstance().findWhereEqual("name", "uber");
    public static final TaxiBrand BOLT = getInstance().findWhereEqual("name", "bolt");
}
