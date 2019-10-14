package orm.entity.logan_park.week_range;

import orm.entity.GenericAbstractDAO;

public class WeekRangeDAO extends GenericAbstractDAO<WeekRange> {

    public WeekRangeDAO() {
        super(WeekRange.class);
    }

    private static final WeekRangeDAO INSTANCE = new WeekRangeDAO();

    public static WeekRangeDAO getInstance() {
        return INSTANCE;
    }

}
