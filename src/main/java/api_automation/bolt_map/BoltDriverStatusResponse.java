package api_automation.bolt_map;

import client.rest.JSONModel;

public class BoltDriverStatusResponse extends JSONModel<BoltDriverStatusResponse> {
    private Integer code;
    private String message;
    private BoltDriverStatusData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BoltDriverStatusData getData() {
        return data;
    }

    public void setData(BoltDriverStatusData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BoltDriverStatusResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                "} " + super.toString();
    }
}
