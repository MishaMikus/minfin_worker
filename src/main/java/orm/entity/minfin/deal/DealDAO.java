package orm.entity.minfin.deal;

import orm.entity.GenericAbstractDAO;

public class DealDAO extends GenericAbstractDAO<Deal> {

    public DealDAO() {
        super(Deal.class);
    }

    private static final DealDAO INSTANCE = new DealDAO();

    public static DealDAO getInstance() {
        return INSTANCE;
    }
}
