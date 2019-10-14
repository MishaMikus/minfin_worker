package server.logan_park.view.weekly_report_bolt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;

import java.util.Date;

@Controller
public class WeeklyReportBoltController extends BaseController {
    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/weekly_report_bolt")
    public ModelAndView weeklyReportBolt() {
        return new ModelAndView("loganPark/weekly_report_bolt")
                .addObject("weeklyReportBolt", WeeklyReportBoltHelper.makeReport(new Date()));
    }
}