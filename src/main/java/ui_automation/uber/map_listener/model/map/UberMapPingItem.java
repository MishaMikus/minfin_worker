package ui_automation.uber.map_listener.model.map;

import client.rest.JSONModel;

public class UberMapPingItem extends JSONModel<UberMapPingItem> {
    private String status;
    private UberMapPingItemData data;

    @Override
    public String toString() {
        return "UberMapPingItem{" +
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

    public UberMapPingItemData getData() {
        return data;
    }

    public void setData(UberMapPingItemData data) {
        this.data = data;
    }
}
