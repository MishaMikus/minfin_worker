package server.logan_park.view.vehicle;

import orm.entity.logan_park.vehicle.Vehicle;

public class VehicleUpdate {
    private String card;
    private String plate;
    private String station;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }


    public String getPlate() {
        return plate;
    }


    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "VehicleUpdate{" +
                "card='" + card + '\'' +
                ", plate='" + plate + '\'' +
                ", station='" + station + '\'' +
                "} " + super.toString();
    }
}
