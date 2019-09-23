package orm.entity.uber.driver;

import orm.entity.GenericAbstractDAO;

import java.util.ArrayList;
import java.util.List;

public class UberDriverDAO extends GenericAbstractDAO<UberDriver> {
    public UberDriverDAO() {
        super(UberDriver.class);
    }
    private static final UberDriverDAO INSTANCE = new UberDriverDAO();
    public static UberDriverDAO getInstance() {
        return INSTANCE;
    }

    public  UberDriver driverById(Integer driverId) {
     return getDriverList()
             .stream()
             .filter(d->d.getId().equals(driverId))
             .findAny()
             .orElse(null);
    }

    public List<UberDriver> getDriverList() {
        return findAll();
    }

    public UberDriver driverByUUID(String driverUUID) {
        return getDriverList()
                .stream()
                .filter(d->d.getDriverUUID().equals(driverUUID))
                .findAny()
                .orElse(null);
    }
}
