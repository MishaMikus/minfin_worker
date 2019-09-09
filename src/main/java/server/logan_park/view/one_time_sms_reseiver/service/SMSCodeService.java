package server.logan_park.view.one_time_sms_reseiver.service;

import orm.entity.uber.sms_code.UberSMSCode;
import orm.entity.uber.sms_code.UberSMSCodeDAO;
import server.logan_park.view.one_time_sms_reseiver.model.SMSReceiverFormModel;

import java.util.Date;

public class SMSCodeService {
    private final static SMSCodeService INSTANCE=new SMSCodeService();
    public static SMSCodeService geiInstance() {
        return INSTANCE;
    }

    public void saveCode(SMSReceiverFormModel smsReceiver) {
        UberSMSCode uberSMSCode=new UberSMSCode();
        uberSMSCode.setCode(smsReceiver.getCode());
        uberSMSCode.setUsed(false);
        uberSMSCode.setCreated(new Date());
        UberSMSCodeDAO.getInstance().save(uberSMSCode);
    }
}
