package orm.entity.uber.driver_realtime_table.state;

import orm.entity.GenericAbstractDAO;
import orm.entity.uber.driver_realtime_table.UberDriverRealTime;

public class UberDriverRealTimeStateDAO extends GenericAbstractDAO<UberDriverRealTimeState> {
    public UberDriverRealTimeStateDAO() {
        super(UberDriverRealTimeState.class);
    }

    private static final UberDriverRealTimeStateDAO INSTANCE = new UberDriverRealTimeStateDAO();

    public static UberDriverRealTimeStateDAO getInstance() {
        return INSTANCE;
    }

    public UberDriverRealTimeState findByStatus(String state) {
        return findWhereContains("state", state);
    }
}
