package orm.entity.logan_park.map_pinger.state;

import orm.entity.GenericAbstractDAO;

public class MapPingerStateDAO extends GenericAbstractDAO<MapPingerState> {

    public MapPingerStateDAO() {
        super(MapPingerState.class);
    }

    private static final MapPingerStateDAO INSTANCE = new MapPingerStateDAO();

    public static MapPingerStateDAO getInstance() {
        return INSTANCE;
    }

    public MapPingerState findByStatus(String state) {
        return findWhereContains("state", state);
    }

    public MapPingerState findByStatusAndTaxi(String state, Integer taxiBrandId) {
        return findAllWhereEqual("state", state).stream().filter(s -> s.getTaxi_brand_id().equals(taxiBrandId)).findAny().orElse(null);
    }
}
