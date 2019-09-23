package orm.entity.uber.uber_captcha;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;

public class UberCaptchaDAO extends GenericAbstractDAO<UberCaptcha> {
    private final static Logger LOGGER = Logger.getLogger(UberCaptchaDAO.class);
    public UberCaptchaDAO() {
        super(UberCaptcha.class);
    }

    private static final UberCaptchaDAO INSTANCE = new UberCaptchaDAO();

    public static UberCaptchaDAO getInstance() {
        return INSTANCE;
    }
}
