package server.logan_park.view.weekly_report_uber;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;
import server.logan_park.helper.AutomaticallyWeeklyReportHelper;

@Controller
public class WeeklyReportUberController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET,value = "/logan_park/weekly_report_uber")
    public ModelAndView weeklyReport() {
        ModelAndView modelAndView=new ModelAndView("loganPark/week_report_uber");
        AutomaticallyWeeklyReportHelper automaticallyWeeklyReportHelper =new AutomaticallyWeeklyReportHelper();
        modelAndView.addObject("weekHashLabel", automaticallyWeeklyReportHelper.getCurrentWeekHash());
        modelAndView.addObject("paymentTable", automaticallyWeeklyReportHelper.makeMap());
        modelAndView.addObject("ownerTable", automaticallyWeeklyReportHelper.makeOwnerMap());
        modelAndView.addObject("generalPartnerSummary", automaticallyWeeklyReportHelper.makeGeneralPartnerSummary());
        return modelAndView;
    }
}