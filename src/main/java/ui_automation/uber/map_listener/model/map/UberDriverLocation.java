package ui_automation.uber.map_listener.model.map;

public class UberDriverLocation {
    private Double latitude;
    private Double longitude;
    private Double course;

    @Override
    public String toString() {
        return "UberDriverLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", course=" + course +
                '}';
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }
}
