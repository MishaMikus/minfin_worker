package server.logan_park.view.weekly_report_general.version_2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;
import server.logan_park.view.weekly_report_general.DateValidator;
import server.logan_park.view.weekly_report_general.WeeklyReportGeneralHelper;

import java.util.Date;

@Controller
public class WeeklyReportGeneralController2 extends BaseController {
    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/new_report_general")
    public ModelAndView weeklyReport() {
        return new ModelAndView("loganPark/new_report_general")
                .addObject("weeklyReport", WeeklyReportGeneralHelper.makeReport());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/new_report_general/{date}")
    public ModelAndView weeklyReportByDate(@PathVariable String date) {
        Date validDate = new DateValidator().isValidDate(date) ? new DateValidator().parseDate(date) : new Date();
        return new ModelAndView("loganPark/new_report_general")
                .addObject("weeklyReport",
                        WeeklyReportGeneralHelper.makeReport(validDate));
    }
}