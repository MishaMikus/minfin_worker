package server.logan_park.view.driver;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import orm.entity.logan_park.driver.UberDriverDAO;
import server.BaseController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DriverController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/driver")
    public String initForm(HttpServletRequest request) {
        request.getSession(true).setAttribute("driverTable", UberDriverDAO.getInstance().findAll());
        return "loganPark/driver";
    }
}