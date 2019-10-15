package orm.entity.bolt;

import orm.entity.GenericAbstractDAO;

import java.util.Date;
import java.util.List;

public class BoltPaymentRecordDayDAO extends GenericAbstractDAO<BoltPaymentRecordDay> {

    public BoltPaymentRecordDayDAO() {
        super(BoltPaymentRecordDay.class);
    }

    private static final BoltPaymentRecordDayDAO INSTANCE = new BoltPaymentRecordDayDAO();

    public static BoltPaymentRecordDayDAO getInstance() {
        return INSTANCE;
    }

    public Date latestDate() {
        if (findLatest("timestamp") == null) return null;
        return findLatest("timestamp").getTimestamp();
    }

    public List<BoltPaymentRecordDay> findAllByCurrentWeek(Date previousMonday) {
        return findAllInTimeRange("timestamp", previousMonday, new Date());
    }

    public List<BoltPaymentRecordDay> findAllByWeekRangeId(Integer week_id) {
        return findAllWhereEqual("week_id", week_id);
    }
}
