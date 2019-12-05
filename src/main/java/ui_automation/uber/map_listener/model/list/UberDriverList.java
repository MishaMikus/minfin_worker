package ui_automation.uber.map_listener.model.list;

import client.rest.JSONModel;

public class UberDriverList extends JSONModel<UberDriverList> {
    private String status;
    private UberDriverListData data;

    @Override
    public String toString() {
        return "UberDriverList{" +
                "status='" + status + '\'' +
                ", data=" + data +
                "} " + super.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UberDriverListData getData() {
        return data;
    }

    public void setData(UberDriverListData data) {
        this.data = data;
    }
}
