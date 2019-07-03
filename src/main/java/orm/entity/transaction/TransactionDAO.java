package orm.entity.transaction;

import orm.entity.GenericAbstractDAO;

import java.util.Comparator;

public class TransactionDAO extends GenericAbstractDAO<Transaction> {

    public TransactionDAO() {
        super(Transaction.class);
    }

    private static final TransactionDAO INSTANCE = new TransactionDAO();

    public static TransactionDAO getInstance() {
        return INSTANCE;
    }

    public Transaction getLatest() {
        return findAll().stream().max(Comparator.comparing(Transaction::getDate)).orElse(null);
    }
}
