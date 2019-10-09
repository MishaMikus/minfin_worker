package server.logan_park.view.captcha;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import orm.entity.uber.uber_captcha.UberCaptcha;
import orm.entity.uber.uber_captcha.UberCaptchaDAO;
import server.BaseController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CaptchaController extends BaseController {
    public static final String PATH="/uber_captcha";
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    @RequestMapping(method = RequestMethod.GET,value = PATH+"/{fileId}")
    public String initForm(@PathVariable String fileId, HttpServletRequest request) {
        UberCaptcha uberCaptcha=UberCaptchaDAO.getInstance().findWhereEqual("fileId",fileId);
        LOGGER.info("uberCaptcha : "+uberCaptcha);
        request.getSession(true).setAttribute("uberCaptcha", uberCaptcha.getRealPath());
        LOGGER.info("fileId : "+fileId);
        return "loganPark"+PATH;
    }
}