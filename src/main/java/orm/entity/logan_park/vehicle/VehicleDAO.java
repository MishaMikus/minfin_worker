package orm.entity.logan_park.vehicle;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;

public class VehicleDAO extends GenericAbstractDAO<Vehicle> {
    private final static Logger LOGGER = Logger.getLogger(Vehicle.class);

    public VehicleDAO() {
        super(Vehicle.class);
    }

    private static final VehicleDAO INSTANCE = new VehicleDAO();

    public static VehicleDAO getInstance() {
        return INSTANCE;
    }

    public Vehicle findByPlate(String plate) {
        return findWhereEqual("plate", plate);
    }
}
