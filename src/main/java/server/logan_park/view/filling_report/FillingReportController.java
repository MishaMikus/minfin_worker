package server.logan_park.view.filling_report;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;
import server.logan_park.view.weekly_report_bolt.WeeklyReportBoltHelper;
import server.logan_park.view.weekly_report_general.DateValidator;

import java.util.Date;

@Controller
public class FillingReportController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/filling_report")
    public ModelAndView weeklyReport() {
        ModelAndView modelAndView = new ModelAndView("loganPark/filling_report");
        modelAndView.addObject("fillingTable", new FillingHelper().makeReport(new Date()));
        return modelAndView;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/filling_report/{date}")
    public ModelAndView weeklyReportByDate(@PathVariable String date) {
        if (!new DateValidator().validateDate(date)) {
            return new ModelAndView("loganPark/filling_report")
                    .addObject("fillingTable", new FillingHelper().makeReport(new Date()));
        }
        return new ModelAndView("loganPark/filling_report")
                .addObject("fillingTable", new FillingHelper().makeReport(new DateValidator().parseDate(date)));
    }

}