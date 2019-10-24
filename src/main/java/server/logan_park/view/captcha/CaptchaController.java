package server.logan_park.view.captcha;

import org.apache.log4j.Logger;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public static final String PATH = "/logan_park/uber_captcha";
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/logan_park/uber_captcha/{id}")
    public String initForm(@PathVariable Integer id, HttpServletRequest request) {
        request.getSession(true).setAttribute("id", id);
        return "loganPark/uber_captcha";
    }

    //IMAGE link
    @RequestMapping(method = RequestMethod.GET, value = PATH + "/img/{id}")
    public ResponseEntity<byte[]> initFile(@PathVariable Integer id, HttpServletRequest request) {
        UberCaptcha uberCaptcha = UberCaptchaDAO.getInstance().findById(id);
        LOGGER.info("uberCaptcha : " + uberCaptcha);
        LOGGER.info("id : " + id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(uberCaptcha.getImage(), headers, HttpStatus.OK);
    }

}