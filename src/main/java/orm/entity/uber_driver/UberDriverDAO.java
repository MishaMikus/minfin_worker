package orm.entity.uber_driver;

import orm.entity.GenericAbstractDAO;

public class UberDriverDAO extends GenericAbstractDAO<UberDriver> {

    public UberDriverDAO() {
        super(UberDriver.class);
    }

    private static final UberDriverDAO INSTANCE = new UberDriverDAO();

    public static UberDriverDAO getInstance() {
        return INSTANCE;
    }

}
