package orm.entity.bolt.payment_record_day;

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

    public static void main(String[] args) {
        getInstance().findAll().forEach(p->{
        if (p.getTimestamp().getTime()>new Date().getTime()
        -7*24*60*60*1000L){
                System.out.println(p);
                getInstance().deleteById(new BoltPaymentRecordDayPK(p.getDriverName(),p.getTimestamp()));
        }}

        );
    }
}
