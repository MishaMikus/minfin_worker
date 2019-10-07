package orm.entity.uber.uber_okko_filling;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;

public class FillingRecordDAO extends GenericAbstractDAO<FillingRecord> {
    private final static Logger LOGGER = Logger.getLogger(FillingRecordDAO.class);
    public FillingRecordDAO() {
        super(FillingRecord.class);
    }

    private static final FillingRecordDAO INSTANCE = new FillingRecordDAO();

    public static FillingRecordDAO getInstance() {
        return INSTANCE;
    }


    public FillingRecord latest() {
        FillingRecord uberUpdateWeekReportRequest=findLatest("date");
        LOGGER.info("latest : "+uberUpdateWeekReportRequest);
        return uberUpdateWeekReportRequest;
    }
}
