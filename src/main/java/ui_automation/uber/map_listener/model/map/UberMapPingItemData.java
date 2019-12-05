package ui_automation.uber.map_listener.model.map;

import java.util.List;

public class UberMapPingItemData {
    private List<UberDriverEvent> driverEvents;

    public List<UberDriverEvent> getDriverEvents() {
        return driverEvents;
    }

    public void setDriverEvents(List<UberDriverEvent> driverEvents) {
        this.driverEvents = driverEvents;
    }

    @Override
    public String toString() {
        return "UberMapPingItemData{" +
                "driverEvents=" + driverEvents +
                '}';
    }
}
