package server.logan_park.view.weekly_report_bolt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;
import server.logan_park.view.weekly_report_general.DateValidator;

import java.util.Date;

@Controller
public class WeeklyReportBoltController extends BaseController {
    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/weekly_report_bolt")
    public ModelAndView weeklyReportBolt() {
        return new ModelAndView("loganPark/weekly_report_bolt")
                .addObject("weeklyReportBolt", WeeklyReportBoltHelper.makeReport(new Date()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/weekly_report_bolt/{date}")
    public ModelAndView weeklyReportByDate(@PathVariable String date) {
        if (!new DateValidator().validateDate(date)) {
            return new ModelAndView("loganPark/weekly_report_bolt")
                    .addObject("weeklyReportBolt", WeeklyReportBoltHelper.makeReport(new Date()));
        }
        return new ModelAndView("loganPark/weekly_report_bolt")
                .addObject("weeklyReportBolt", WeeklyReportBoltHelper.makeReport(new DateValidator().parseDate(date)));
    }
}