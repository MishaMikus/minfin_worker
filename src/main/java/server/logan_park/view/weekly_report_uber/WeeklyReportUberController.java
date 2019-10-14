package server.logan_park.view.weekly_report_uber;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;
import server.logan_park.helper.AutomaticallyWeeklyReportHelper;
import server.logan_park.view.weekly_report_general.DateValidator;

import java.util.Date;

@Controller
public class WeeklyReportUberController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/weekly_report_uber")
    public ModelAndView weeklyReport() {
        ModelAndView modelAndView = new ModelAndView("loganPark/week_report_uber");
        modelAndView.addObject("automaticallyWeeklyUberReport", new AutomaticallyWeeklyReportHelper(new Date()).makeReport());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/weekly_report_uber/{date}")
    public ModelAndView weeklyReportByDate(@PathVariable String date) {
        if (!new DateValidator().validateDate(date)) {
            new ModelAndView("loganPark/week_report_uber")
                    .addObject("automaticallyWeeklyUberReport", new AutomaticallyWeeklyReportHelper(new Date()).makeReport());
        }
        return new ModelAndView("loganPark/week_report_uber")
                .addObject("automaticallyWeeklyUberReport", new AutomaticallyWeeklyReportHelper(new DateValidator().parseDate(date))
                        .makeReport());

    }
}