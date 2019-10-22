package orm.entity.logan_park.fuel_account_leftover;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;

import java.util.HashMap;
import java.util.Map;

public class FuelAccountLeftoverDAO extends GenericAbstractDAO<FuelAccountLeftover> {
    private final static Logger LOGGER = Logger.getLogger(FuelAccountLeftoverDAO.class);

    public FuelAccountLeftoverDAO() {
        super(FuelAccountLeftover.class);
    }

    private static final FuelAccountLeftoverDAO INSTANCE = new FuelAccountLeftoverDAO();

    public static FuelAccountLeftoverDAO getInstance() {
        return INSTANCE;
    }

    public FuelAccountLeftover latest(String station) {
        Map<String, Object> whereEqualMap = new HashMap<>();
        whereEqualMap.put("station", station);
        FuelAccountLeftover fuelAccountLeftover = findLatestWhere("date", whereEqualMap);
        LOGGER.info("latest : " + fuelAccountLeftover);
        return fuelAccountLeftover;
    }
}
