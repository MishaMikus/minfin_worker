package server.logan_park.view.vehicle;

import orm.entity.logan_park.vehicle.Vehicle;

public class VehicleView extends Vehicle {
    private String upgCard;
    private String okkoCard;

    public VehicleView(Vehicle vehicle) {
        super();
        setName(vehicle.getName());
        setPlate(vehicle.getPlate());
        setId(vehicle.getId());
    }

    public String getUpgCard() {
        return upgCard;
    }

    public void setUpgCard(String upgCard) {
        this.upgCard = upgCard;
    }

    public String getOkkoCard() {
        return okkoCard;
    }

    public void setOkkoCard(String okkoCard) {
        this.okkoCard = okkoCard;
    }

    @Override
    public String toString() {
        return "VehicleView{" +
                "upgCard='" + upgCard + '\'' +
                ", okkoCard='" + okkoCard + '\'' +
                "} " + super.toString();
    }
}
