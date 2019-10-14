package server.logan_park.view.weekly_report_general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;

@Controller
public class WeeklyReportGeneralController extends BaseController {
    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/weekly_report_general")
    public ModelAndView weeklyReport() {
        return new ModelAndView("loganPark/weekly_report_general")
                .addObject("weeklyReport", WeeklyReportGeneralHelper.makeReport());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/weekly_report_general/{date}")
    public ModelAndView weeklyReportByDate(@PathVariable String date) {
        if (!new DateValidator().validateDate(date)) {
            new ModelAndView("loganPark/weekly_report_general")
                    .addObject("weeklyReport", WeeklyReportGeneralHelper.makeReport());
        }
        return new ModelAndView("loganPark/weekly_report_general")
                .addObject("weeklyReport", WeeklyReportGeneralHelper.makeReport(new DateValidator().parseDate(date)));
    }
}