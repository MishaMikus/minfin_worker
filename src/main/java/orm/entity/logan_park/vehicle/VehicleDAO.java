package orm.entity.logan_park.vehicle;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;
import util.StringUtil;

import java.util.Set;

public class VehicleDAO extends GenericAbstractDAO<Vehicle> {
    private final static Logger LOGGER = Logger.getLogger(Vehicle.class);

    public VehicleDAO() {
        super(Vehicle.class);
    }

    private static final VehicleDAO INSTANCE = new VehicleDAO();

    public static VehicleDAO getInstance() {
        return INSTANCE;
    }

    public static Set<Integer> findAllTrackerId() {
        //return getInstance().findDistinctSet("tracker_id");
        //TODO
        return null;
    }

    public Vehicle findByPlate(String plate) {
        return findWhereEqual("plate", StringUtil.turnCyrillicLettersToEnglish(plate));
    }
}
