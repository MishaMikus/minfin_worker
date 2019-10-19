package orm.entity.logan_park.card;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;

public class FillingCardDAO extends GenericAbstractDAO<FillingCard> {
    private final static Logger LOGGER = Logger.getLogger(FillingCard.class);

    public FillingCardDAO() {
        super(FillingCard.class);
    }

    private static final FillingCardDAO INSTANCE = new FillingCardDAO();

    public static FillingCardDAO getInstance() {
        return INSTANCE;
    }
}
