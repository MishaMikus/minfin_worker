package orm.entity.logan_park.driver;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;
import ui_automation.uber.map_listener.UberMapListener;

import java.util.List;

public class UberDriverDAO extends GenericAbstractDAO<UberDriver> {
    private static final Logger LOGGER = Logger.getLogger(UberDriverDAO.class);

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

    public UberDriver findDriverByUUID(String driverUUID) {
        LOGGER.info("try to find driver by uuid "+driverUUID);
        return findWhereEqual("driverUUID", driverUUID);
    }

    public UberDriver findDriverByDriverName(String name) {
        return findWhereEqual("name", name.replaceAll(" ", "_"));
    }

    public UberDriver findDriverByBoltDriverName(String name) {
        return findWhereEqual("bolt_name", name.replaceAll(" ", "_"));
    }
}
