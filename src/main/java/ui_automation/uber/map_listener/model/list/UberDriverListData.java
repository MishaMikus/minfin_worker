package ui_automation.uber.map_listener.model.list;

import java.util.List;

public class UberDriverListData {
    private List<UberDriverListDriver> drivers;
    private String timezone;
    private String useKilometers;

    @Override
    public String toString() {
        return "UberDriverListData{" +
                "drivers=" + drivers +
                ", timezone='" + timezone + '\'' +
                ", useKilometers='" + useKilometers + '\'' +
                '}';
    }

    public List<UberDriverListDriver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<UberDriverListDriver> drivers) {
        this.drivers = drivers;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getUseKilometers() {
        return useKilometers;
    }

    public void setUseKilometers(String useKilometers) {
        this.useKilometers = useKilometers;
    }
}
