package server.logan_park.view.captcha;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;
import server.logan_park.view.one_time_sms_reseiver.model.SMSReceiverFormModel;

@Controller
public class CaptchaController extends BaseController {
    public static final String PATH="/uber_captcha";
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    @RequestMapping(method = RequestMethod.GET,value = PATH+"/{fileId}")
    public ModelAndView initForm(@PathVariable String fileId) {
        ModelAndView modelAndView=new ModelAndView("loganPark"+PATH);
        modelAndView.addObject("smsReceiver",new SMSReceiverFormModel());
        LOGGER.info("fileId : "+fileId);
        return modelAndView;
    }
}