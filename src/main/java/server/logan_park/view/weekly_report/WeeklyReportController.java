package server.logan_park.view.weekly_report;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import orm.entity.uber.update_request.UberUpdateWeekReportRequest;
import orm.entity.uber.update_request.UberUpdateWeekReportRequestDAO;
import server.BaseController;
import server.logan_park.helper.AutomaticallyWeeklyReportHelper;

import java.util.Date;

@Controller
public class WeeklyReportController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.POST,value = "/updateWeekReportRequest")
    public ModelAndView submit() {
        UberUpdateWeekReportRequest uberUpdateWeekReportRequest=new UberUpdateWeekReportRequest();
        uberUpdateWeekReportRequest.setCreated(new Date());
        uberUpdateWeekReportRequest.setStarted(false);
        if(!updateInProgress()){
            uberUpdateWeekReportRequest.setId((Integer) UberUpdateWeekReportRequestDAO.getInstance().save(uberUpdateWeekReportRequest));
        }
        waitForReportDone();
        return new ModelAndView("redirect:/logan_park/weekly_report");
    }

    private void waitForReportDone() {
        long timeout=10*60*1000L;
        long start=new Date().getTime();
        long pingTime=10000L;
        while (updateInProgress()&&((new Date().getTime()-start)<timeout)){
            LOGGER.info("WAIT FOR REPORT PROCESSING");
            try {
                Thread.sleep(pingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean updateInProgress() {
        boolean res=UberUpdateWeekReportRequestDAO.getInstance().inProgress();
        LOGGER.info("updateInProgress : "+res);
        return res;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/logan_park/weekly_report")
    public ModelAndView weeklyReport() {
        ModelAndView modelAndView=new ModelAndView("loganPark/week_report");
        AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper =new AutomaticallyWeeklyReportHelper();

        UberUpdateWeekReportRequest uberUpdateWeekReportRequest=UberUpdateWeekReportRequestDAO.getInstance().latest();
        modelAndView.addObject("latestUpdateDate", uberUpdateWeekReportRequest.getUpdated());
        Long duration=uberUpdateWeekReportRequest.getUpdated()==null
                ?new Date().getTime()-uberUpdateWeekReportRequest.getCreated().getTime()
                :uberUpdateWeekReportRequest.getUpdated().getTime()-uberUpdateWeekReportRequest.getCreated().getTime();
        modelAndView.addObject("latestUpdateDuration", duration);
        modelAndView.addObject("weekHashLabel", automaticallyWeeklyReportHelper.getCurrentWeekHash());
        modelAndView.addObject("paymentTable", automaticallyWeeklyReportHelper.makeMap());
        modelAndView.addObject("ownerTable", automaticallyWeeklyReportHelper.makeOwnerMap());
        modelAndView.addObject("generalPartnerSummary", automaticallyWeeklyReportHelper.makeGeneralPartnerSummary());
        return modelAndView;
    }
}