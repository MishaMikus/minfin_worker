package server.logan_park.helper.model;

public class VehiclePerformance {
    private String model;//"Model",
    private String licensePlate;//"License plate",
    private Double netFares;//"Net Fares",
    private Double perTrip;//"Per trip",
    private Double perHourOnline;//"Per hour online",
    private Double perKmOnTrip;//"Per km on trip",
    private Integer Trips;//"Trips",
    private Double hoursOnline;//"Hours online",
    private Double tripsPerHour;//"Trips per hour",
    private Double distancePerTrip;//"Distance per trip"

    @Override
    public String toString() {
        return "VehiclePerformance{" +
                "model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", netFares=" + netFares +
                ", perTrip=" + perTrip +
                ", perHourOnline=" + perHourOnline +
                ", perKmOnTrip=" + perKmOnTrip +
                ", Trips=" + Trips +
                ", hoursOnline=" + hoursOnline +
                ", tripsPerHour=" + tripsPerHour +
                ", distancePerTrip=" + distancePerTrip +
                '}';
    }

    public Double getHoursOnline() {
        return hoursOnline;
    }

    public void setHoursOnline(Double hoursOnline) {
        this.hoursOnline = hoursOnline;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Double getNetFares() {
        return netFares;
    }

    public void setNetFares(Double netFares) {
        this.netFares = netFares;
    }

    public Double getPerTrip() {
        return perTrip;
    }

    public void setPerTrip(Double perTrip) {
        this.perTrip = perTrip;
    }

    public Double getPerHourOnline() {
        return perHourOnline;
    }

    public void setPerHourOnline(Double perHourOnline) {
        this.perHourOnline = perHourOnline;
    }

    public Double getPerKmOnTrip() {
        return perKmOnTrip;
    }

    public void setPerKmOnTrip(Double perKmOnTrip) {
        this.perKmOnTrip = perKmOnTrip;
    }

    public Integer getTrips() {
        return Trips;
    }

    public void setTrips(Integer trips) {
        Trips = trips;
    }


    public Double getTripsPerHour() {
        return tripsPerHour;
    }

    public void setTripsPerHour(Double tripsPerHour) {
        this.tripsPerHour = tripsPerHour;
    }

    public Double getDistancePerTrip() {
        return distancePerTrip;
    }

    public void setDistancePerTrip(Double distancePerTrip) {
        this.distancePerTrip = distancePerTrip;
    }
}
