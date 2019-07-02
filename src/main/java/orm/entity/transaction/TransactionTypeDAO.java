package orm.entity.transaction;

import orm.entity.GenericAbstractDAO;

public class TransactionTypeDAO extends GenericAbstractDAO<TransactionType> {

    public TransactionTypeDAO() {
        super(TransactionType.class);
    }

    private static final TransactionTypeDAO INSTANCE = new TransactionTypeDAO();

    public static TransactionTypeDAO getInstance() {
        return INSTANCE;
    }

    public final static TransactionType INVEST_USD = new TransactionTypeDAO().findWhereEqual("name", "invest_usd");
    public final static TransactionType INVEST_UAH = new TransactionTypeDAO().findWhereEqual("name", "invest_uah");
    public final static TransactionType USD_UAH = new TransactionTypeDAO().findWhereEqual("name", "usd-uah");
    public final static TransactionType UAH_USD = new TransactionTypeDAO().findWhereEqual("name", "uah-usd");
}
