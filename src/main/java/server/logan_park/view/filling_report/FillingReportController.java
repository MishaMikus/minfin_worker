package server.logan_park.view.filling_report;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import server.BaseController;
import server.logan_park.view.filling_report.model.KmRequest;
import server.logan_park.view.filling_report.model.ManualReceiptRequest;
import server.logan_park.view.weekly_report_general.DateValidator;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class FillingReportController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/filling_report")
    public ModelAndView weeklyReport() {
        return new ModelAndView("loganPark/filling_report")
                .addObject("fillingTable", new FillingHelper().makeReport(new Date()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/filling_report/{date}")
    public ModelAndView weeklyReportByDate(@PathVariable String date) {
        if (!new DateValidator().isValidDate(date)) {
            return new ModelAndView("loganPark/filling_report")
                    .addObject("fillingTable", new FillingHelper().makeReport(new Date()));
        }
        return new ModelAndView("loganPark/filling_report")
                .addObject("fillingTable", new FillingHelper().makeReport(new DateValidator().parseDate(date)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logan_park/save_km")
    public ModelAndView postKm(@Valid KmRequest kmRequest) {
        LOGGER.info("TRY to add "+kmRequest);
        new FillingHelper().addKm(kmRequest);
        return new ModelAndView(new RedirectView("/logan_park/filling_report", true));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logan_park/manual_receipt")
    public ModelAndView postKm(@Valid ManualReceiptRequest manualReceiptRequest) {
        LOGGER.info("TRY to make new receipt "+manualReceiptRequest);
        new FillingHelper().makeManualReceipt(manualReceiptRequest);
        return new ModelAndView(new RedirectView("/logan_park/filling_report", true));
    }

}