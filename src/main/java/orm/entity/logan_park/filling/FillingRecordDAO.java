package orm.entity.logan_park.filling;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;

import java.util.HashMap;
import java.util.Map;

public class FillingRecordDAO extends GenericAbstractDAO<FillingRecord> {
    private final static Logger LOGGER = Logger.getLogger(FillingRecordDAO.class);

    public FillingRecordDAO() {
        super(FillingRecord.class);
    }

    private static final FillingRecordDAO INSTANCE = new FillingRecordDAO();

    public static FillingRecordDAO getInstance() {
        return INSTANCE;
    }


    public FillingRecord latest(String station) {
        Map<String, Object> whereEqualMap = new HashMap<>();
        whereEqualMap.put("station", station);
        FillingRecord fillingRecord = findLatestWhere("date", whereEqualMap);
        LOGGER.info("latest : " + fillingRecord);
        return fillingRecord;
    }
}
