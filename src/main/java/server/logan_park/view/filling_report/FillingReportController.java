package server.logan_park.view.filling_report;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import orm.entity.okko.uber_okko_filling.FillingRecordDAO;
import server.BaseController;

@Controller
public class FillingReportController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET,value = "/logan_park/filling_report")
    public ModelAndView weeklyReport() {
        ModelAndView modelAndView=new ModelAndView("loganPark/filling_report");
        modelAndView.addObject("fillingTable", FillingRecordDAO.getInstance().findAll());
        return modelAndView;
    }
}