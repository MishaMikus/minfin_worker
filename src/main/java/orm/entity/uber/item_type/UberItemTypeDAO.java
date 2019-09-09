package orm.entity.uber.item_type;

import orm.entity.GenericAbstractDAO;

import java.util.ArrayList;
import java.util.List;

public class UberItemTypeDAO extends GenericAbstractDAO<UberItemType> {

    public UberItemTypeDAO() {
        super(UberItemType.class);
    }

    private static final UberItemTypeDAO INSTANCE = new UberItemTypeDAO();

    public static UberItemTypeDAO getInstance() {
        return INSTANCE;
    }

    private static final List<UberItemType> ITEM_LIST = new ArrayList<>();

    public UberItemType itemById(Integer id) {
        return getItemTypeList()
                .stream()
                .filter(d -> d.getId()
                        .equals(id))
                .findAny()
                .orElse(null);
    }

    private List<UberItemType> getItemTypeList() {
        if (ITEM_LIST.size() == 0) {
            ITEM_LIST.addAll(findAll());
        }
        return ITEM_LIST;
    }

    public UberItemType getItemTypeByName(String itemType) {
        return getItemTypeList()
                .stream()
                .filter(i -> i.getName().equals(itemType))
                .findAny()
                .orElse(null);
    }
}
