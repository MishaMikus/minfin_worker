package orm.entity.transaction;

import orm.entity.GenericAbstractDAO;
import orm.entity.deal.Deal;

public class TransactionDAO extends GenericAbstractDAO<Transaction> {

    public TransactionDAO() {
        super(Transaction.class);
    }

    private static final TransactionDAO INSTANCE = new TransactionDAO();

    public static TransactionDAO getInstance() {
        return INSTANCE;
    }
}
