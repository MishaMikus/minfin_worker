package server.logan_park.view.one_time_sms_reseiver.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SMSReceiverFormModel {
    @NotNull
    @Pattern(regexp = "\\d\\d\\d\\d")
    private String code;

    public SMSReceiverFormModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SMSReceiverFormModel{" +
                "code='" + code + '\'' +
                '}';
    }
}
