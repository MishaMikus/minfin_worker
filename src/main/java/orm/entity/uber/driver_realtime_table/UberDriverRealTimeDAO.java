package orm.entity.uber.driver_realtime_table;

import orm.entity.GenericAbstractDAO;

public class UberDriverRealTimeDAO extends GenericAbstractDAO<UberDriverRealTime> {

    public UberDriverRealTimeDAO() {
        super(UberDriverRealTime.class);
    }

    private static final UberDriverRealTimeDAO INSTANCE = new UberDriverRealTimeDAO();

    public static UberDriverRealTimeDAO getInstance() {
        return INSTANCE;
    }

}
