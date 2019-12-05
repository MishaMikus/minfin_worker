package ui_automation.uber.map_listener.model.list;

public class UberDriverListDriver {
    private String uuid;
    private String pictureUrl;
    private String name;
    private String mobile;
    private String licensePlate;
    private String realtimeStatus;
    private String onboardingStatus;
    private String lastOnlineTime;
    private String vehicleUUID;
    private Boolean waitlistedByFleet;
    private String email;
    private String orgDriverInfo;

    @Override
    public String toString() {
        return "UberDriverListDriver{" +
                "uuid='" + uuid + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", realtimeStatus='" + realtimeStatus + '\'' +
                ", onboardingStatus='" + onboardingStatus + '\'' +
                ", lastOnlineTime='" + lastOnlineTime + '\'' +
                ", vehicleUUID='" + vehicleUUID + '\'' +
                ", waitlistedByFleet=" + waitlistedByFleet +
                ", email='" + email + '\'' +
                ", orgDriverInfo='" + orgDriverInfo + '\'' +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getRealtimeStatus() {
        return realtimeStatus;
    }

    public void setRealtimeStatus(String realtimeStatus) {
        this.realtimeStatus = realtimeStatus;
    }

    public String getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(String onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getVehicleUUID() {
        return vehicleUUID;
    }

    public void setVehicleUUID(String vehicleUUID) {
        this.vehicleUUID = vehicleUUID;
    }

    public Boolean getWaitlistedByFleet() {
        return waitlistedByFleet;
    }

    public void setWaitlistedByFleet(Boolean waitlistedByFleet) {
        this.waitlistedByFleet = waitlistedByFleet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgDriverInfo() {
        return orgDriverInfo;
    }

    public void setOrgDriverInfo(String orgDriverInfo) {
        this.orgDriverInfo = orgDriverInfo;
    }
}
