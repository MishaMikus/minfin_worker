package orm.entity.uber.description;

import orm.entity.GenericAbstractDAO;

import java.util.ArrayList;
import java.util.List;

public class UberDescriptionDAO extends GenericAbstractDAO<UberDescription> {

    public UberDescriptionDAO() {
        super(UberDescription.class);
    }

    private static final UberDescriptionDAO INSTANCE = new UberDescriptionDAO();

    public static UberDescriptionDAO getInstance() {
        return INSTANCE;
    }

    private static final List<UberDescription> DESCRIPTION_LIST = new ArrayList<>();
    public UberDescription descriptionById(Integer id) {
       return getDescriptionList()
                .stream()
                .filter(d->d.getId()
                .equals(id))
                .findAny()
                .orElse(null);

    }

    private List<UberDescription> getDescriptionList() {
        if(DESCRIPTION_LIST.size()==0)   {
            DESCRIPTION_LIST.addAll(findAll());
        }
        return DESCRIPTION_LIST;
    }

    public UberDescription getDescriptionByName(String description) {
        if(description==null) return null;
        return getDescriptionList().stream()
                .filter(d->d.getName().equals(description))
                .findAny().orElse(null);
    }

    public Integer addNewDescription(UberDescription uberDescription) {
        uberDescription.setId((Integer) save(uberDescription));
        DESCRIPTION_LIST.add(uberDescription);
        return uberDescription.getId();
    }
}
