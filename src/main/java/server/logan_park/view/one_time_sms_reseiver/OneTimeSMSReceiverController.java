package server.logan_park.view.one_time_sms_reseiver;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import server.BaseController;
import server.logan_park.view.one_time_sms_reseiver.model.SMSReceiverFormModel;
import server.logan_park.view.one_time_sms_reseiver.service.SMSCodeService;

import javax.validation.Valid;

@Controller
public class OneTimeSMSReceiverController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    @RequestMapping(method = RequestMethod.GET,value = "/logan_park/one_time_sms_code")
    public ModelAndView initForm() {
        ModelAndView modelAndView=new ModelAndView("loganPark/one_time_sms_code");
        modelAndView.addObject("smsReceiver",new SMSReceiverFormModel());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/logan_park/one_time_sms_code")
    public ModelAndView submit(@Valid @ModelAttribute("smsReceiver") SMSReceiverFormModel smsReceiver, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.warn(result);
            ModelAndView modelAndView=new ModelAndView("loganPark/one_time_sms_code_error");
            modelAndView.addObject("msg",result);
            return modelAndView;
        }
        LOGGER.info(smsReceiver);
        SMSCodeService.geiInstance().saveCode(smsReceiver);
        return initForm();
    }
}