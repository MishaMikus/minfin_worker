package api_automation.tracker;

import client.rest.JSONModel;

public class TrackerPingModel extends JSONModel<TrackerPingModel> {
    private Integer locationID;
    private String deviceUtcDate;
    private String serverUtcDate;
    private String latitude;
    private String longitude;
    private String baiduLat;
    private String baiduLng;
    private String oLat;
    private String oLng;
    private String speed;
    private Integer course;
    private Integer isStop;
    private Integer dataType;
    private String dataContext;
    private String distance;
    private String status;

    @Override
    public String toString() {
        return "TrackerPingModel{" +
                "locationID=" + locationID +
                ", deviceUtcDate='" + deviceUtcDate + '\'' +
                ", serverUtcDate='" + serverUtcDate + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", baiduLat='" + baiduLat + '\'' +
                ", baiduLng='" + baiduLng + '\'' +
                ", oLat='" + oLat + '\'' +
                ", oLng='" + oLng + '\'' +
                ", speed='" + speed + '\'' +
                ", course=" + course +
                ", isStop=" + isStop +
                ", dataType=" + dataType +
                ", dataContext='" + dataContext + '\'' +
                ", distance='" + distance + '\'' +
                ", status='" + status + '\'' +
                "} " + super.toString();
    }

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    public String getDeviceUtcDate() {
        return deviceUtcDate;
    }

    public void setDeviceUtcDate(String deviceUtcDate) {
        this.deviceUtcDate = deviceUtcDate;
    }

    public String getServerUtcDate() {
        return serverUtcDate;
    }

    public void setServerUtcDate(String serverUtcDate) {
        this.serverUtcDate = serverUtcDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBaiduLat() {
        return baiduLat;
    }

    public void setBaiduLat(String baiduLat) {
        this.baiduLat = baiduLat;
    }

    public String getBaiduLng() {
        return baiduLng;
    }

    public void setBaiduLng(String baiduLng) {
        this.baiduLng = baiduLng;
    }

    public String getoLat() {
        return oLat;
    }

    public void setoLat(String oLat) {
        this.oLat = oLat;
    }

    public String getoLng() {
        return oLng;
    }

    public void setoLng(String oLng) {
        this.oLng = oLng;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDataContext() {
        return dataContext;
    }

    public void setDataContext(String dataContext) {
        this.dataContext = dataContext;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
