package orm.entity.uber.driver;

import orm.entity.GenericAbstractDAO;

import java.util.List;

public class UberDriverDAO extends GenericAbstractDAO<UberDriver> {
    public UberDriverDAO() {
        super(UberDriver.class);
    }

    private static final UberDriverDAO INSTANCE = new UberDriverDAO();

    public static UberDriverDAO getInstance() {
        return INSTANCE;
    }

    public List<UberDriver> getDriverList() {
        return findAll();
    }
}
