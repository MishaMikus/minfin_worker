package orm.entity.minfin.currency;

import orm.entity.GenericAbstractDAO;

public class CurrencyDAO extends GenericAbstractDAO<Currency> {

    public CurrencyDAO() {
        super(Currency.class);
    }

    private static final CurrencyDAO INSTANCE = new CurrencyDAO();

    public static CurrencyDAO getInstance() {
        return INSTANCE;
    }

    public final static Currency USD_CURRENCY = new CurrencyDAO().findWhereEqual("name", "usd");
    public final static Currency UAH_CURRENCY = new CurrencyDAO().findWhereEqual("name", "uah");

}
