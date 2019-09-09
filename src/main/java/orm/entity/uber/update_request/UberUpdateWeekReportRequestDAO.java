package orm.entity.uber.update_request;

import org.apache.log4j.Logger;
import orm.entity.GenericAbstractDAO;

public class UberUpdateWeekReportRequestDAO extends GenericAbstractDAO<UberUpdateWeekReportRequest> {
    private final static Logger LOGGER = Logger.getLogger(UberUpdateWeekReportRequestDAO.class);
    public UberUpdateWeekReportRequestDAO() {
        super(UberUpdateWeekReportRequest.class);
    }

    private static final UberUpdateWeekReportRequestDAO INSTANCE = new UberUpdateWeekReportRequestDAO();

    public static UberUpdateWeekReportRequestDAO getInstance() {
        return INSTANCE;
    }

    public boolean inProgress() {
        UberUpdateWeekReportRequest uberUpdateWeekReportRequest=latest();
        if(uberUpdateWeekReportRequest==null) return false;
        return uberUpdateWeekReportRequest.getUpdated()==null;
    }

    public UberUpdateWeekReportRequest latest() {
        UberUpdateWeekReportRequest uberUpdateWeekReportRequest=findLatest("created");
        LOGGER.info("latest : "+uberUpdateWeekReportRequest);
        return uberUpdateWeekReportRequest;
    }

//    public static void main(String[] args) {
//        while(true){
//        getInstance().latest();}
//    }
}
