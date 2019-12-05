package ui_automation.uber.map_listener.model.map;

public class UberDriverEvent {
    private String driverUUID;
    private String eventTimestamp;
    private String driverStatus;
    private UberDriverLocation driverLocation;
    private Integer firstVVID;
    private String driverStatusState;

    @Override
    public String toString() {
        return "UberDriverEvent{" +
                "driverUUID='" + driverUUID + '\'' +
                ", eventTimestamp='" + eventTimestamp + '\'' +
                ", driverStatus='" + driverStatus + '\'' +
                ", driverLocation=" + driverLocation +
                ", firstVVID=" + firstVVID +
                ", driverStatusState='" + driverStatusState + '\'' +
                '}';
    }

    public String getDriverUUID() {
        return driverUUID;
    }

    public void setDriverUUID(String driverUUID) {
        this.driverUUID = driverUUID;
    }

    public String getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(String eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public UberDriverLocation getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(UberDriverLocation driverLocation) {
        this.driverLocation = driverLocation;
    }

    public Integer getFirstVVID() {
        return firstVVID;
    }

    public void setFirstVVID(Integer firstVVID) {
        this.firstVVID = firstVVID;
    }

    public String getDriverStatusState() {
        return driverStatusState;
    }

    public void setDriverStatusState(String driverStatusState) {
        this.driverStatusState = driverStatusState;
    }
}
