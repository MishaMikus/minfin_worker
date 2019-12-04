package orm.entity.bolt.map_pinger_item;

import orm.entity.GenericAbstractDAO;

public class MapPingerItemDAO extends GenericAbstractDAO<MapPingerItem> {

    public MapPingerItemDAO() {
        super(MapPingerItem.class);
    }

    private static final MapPingerItemDAO INSTANCE = new MapPingerItemDAO();

    public static MapPingerItemDAO getInstance() {
        return INSTANCE;
    }

}
