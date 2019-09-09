package orm.entity.uber.sms_code;

import orm.entity.GenericAbstractDAO;

public class UberSMSCodeDAO extends GenericAbstractDAO<UberSMSCode> {

    public UberSMSCodeDAO() {
        super(UberSMSCode.class);
    }

    private static final UberSMSCodeDAO INSTANCE = new UberSMSCodeDAO();

    public static UberSMSCodeDAO getInstance() {
        return INSTANCE;
    }


}
