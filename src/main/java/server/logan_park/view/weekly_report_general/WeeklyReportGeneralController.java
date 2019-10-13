package server.logan_park.view.weekly_report_general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;
import server.logan_park.view.weekly_report_bolt.WeeklyReportBoltHelper;

@Controller
public class WeeklyReportGeneralController extends BaseController {
    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/weekly_report_general")
    public ModelAndView weeklyReportBolt() {
        return new ModelAndView("loganPark/weekly_report_general")
                .addObject("weeklyReport", WeeklyReportGeneralHelper.makeReport());
    }
}