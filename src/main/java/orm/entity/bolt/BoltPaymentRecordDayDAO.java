package orm.entity.bolt;

import orm.entity.GenericAbstractDAO;

public class BoltPaymentRecordDayDAO extends GenericAbstractDAO<BoltPaymentRecordDay> {

    public BoltPaymentRecordDayDAO() {
        super(BoltPaymentRecordDay.class);
    }

    private static final BoltPaymentRecordDayDAO INSTANCE = new BoltPaymentRecordDayDAO();

    public static BoltPaymentRecordDayDAO getInstance() {
        return INSTANCE;
    }

}
