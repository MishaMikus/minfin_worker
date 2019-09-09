package orm.entity.uber.payment_record_row;

import orm.entity.GenericAbstractDAO;

public class UberPaymentRecordRowDAO extends GenericAbstractDAO<UberPaymentRecordRow> {

    public UberPaymentRecordRowDAO() {
        super(UberPaymentRecordRow.class);
    }

    private static final UberPaymentRecordRowDAO INSTANCE = new UberPaymentRecordRowDAO();

    public static UberPaymentRecordRowDAO getInstance() {
        return INSTANCE;
    }

    public UberPaymentRecordRow findLatest() {
        return findLatest("timestamp");
    }
}
