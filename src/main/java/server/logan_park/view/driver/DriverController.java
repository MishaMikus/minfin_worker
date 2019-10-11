package server.logan_park.view.driver;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import orm.entity.uber.driver.UberDriverDAO;
import orm.entity.uber.uber_captcha.UberCaptcha;
import orm.entity.uber.uber_captcha.UberCaptchaDAO;
import server.BaseController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DriverController extends BaseController {
    public static final String PATH = "/driver";
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = PATH)
    public String initForm(HttpServletRequest request) {
        request.getSession(true).setAttribute("driverTable", UberDriverDAO.getInstance().findAll());
        return "loganPark" + PATH;
    }
}