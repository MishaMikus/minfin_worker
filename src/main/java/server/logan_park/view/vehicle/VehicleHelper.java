package server.logan_park.view.vehicle;

import orm.entity.logan_park.card.FillingCard;
import orm.entity.logan_park.card.FillingCardDAO;
import orm.entity.logan_park.vehicle.Vehicle;
import orm.entity.logan_park.vehicle.VehicleDAO;

import java.util.ArrayList;
import java.util.List;

public class VehicleHelper {
    public static List<VehicleView> allVehicleList() {
        List<VehicleView> res = makeView(VehicleDAO.getInstance().findAll());
        return res;
    }

    private static List<VehicleView> makeView(List<Vehicle> allVehicleList) {
        List<VehicleView> res = new ArrayList<>();
        for (Vehicle vehicle : allVehicleList) {
            res.add(new VehicleView(vehicle));
        }
        return res;
    }

    public void addVehicle(VehicleView vehicleView) {
        Vehicle vehicle = makeVehicle(vehicleView);
        int id = (int) VehicleDAO.getInstance().save(vehicle);
        FillingCard fillingCard = new FillingCard();
        if (vehicleView.getUpgCard() != null) {
            fillingCard = FillingCardDAO.getInstance().findById(vehicleView.getUpgCard());
            if (fillingCard != null) {
                fillingCard.setId(vehicleView.getUpgCard());
            }
        }
        if (vehicleView.getOkkoCard() != null) {
            fillingCard = FillingCardDAO.getInstance().findById(vehicleView.getOkkoCard());
            if (fillingCard != null) {
                fillingCard.setId(vehicleView.getOkkoCard());
            }
        }
        fillingCard.setVehicle_id(id);
        FillingCardDAO.getInstance().saveOrUpdate(fillingCard);
    }

    private Vehicle makeVehicle(VehicleView vehicleView) {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(vehicleView.getPlate());
        vehicle.setName(vehicleView.getName());
        return vehicle;
    }

    public void updateVehicle(VehicleUpdate vehicleUpdate) {
        //TODO
    }
}
