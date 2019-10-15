package server.logan_park.view.filling_report.model;

import java.util.HashMap;
import java.util.Map;

public class FillingInfo {
    private FillingValue weekFilling = new FillingValue();
    private Map<String, FillingValue> carDistributedMap = new HashMap<>();

    public FillingValue getWeekFilling() {
        return weekFilling;
    }

    public void setWeekFilling(FillingValue weekFilling) {
        this.weekFilling = weekFilling;
    }

    public Map<String, FillingValue> getCarDistributedMap() {
        return carDistributedMap;
    }

    public void setCarDistributedMap(Map<String, FillingValue> carDistributedMap) {
        this.carDistributedMap = carDistributedMap;
    }
}
