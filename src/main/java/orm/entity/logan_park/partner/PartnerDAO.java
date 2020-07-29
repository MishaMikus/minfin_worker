package orm.entity.logan_park.partner;

import orm.entity.GenericAbstractDAO;

public class PartnerDAO extends GenericAbstractDAO<Partner> {

    public PartnerDAO() {
        super(Partner.class);
    }

    private static final PartnerDAO INSTANCE = new PartnerDAO();

    public static PartnerDAO getInstance() {
        return INSTANCE;
    }

}
