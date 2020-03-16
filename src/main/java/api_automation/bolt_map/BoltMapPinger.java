package api_automation.bolt_map;

import org.apache.log4j.Logger;
import orm.entity.logan_park.driver.UberDriver;
import orm.entity.logan_park.driver.UberDriverDAO;
import orm.entity.logan_park.map_pinger.MapPingerItem;
import orm.entity.logan_park.map_pinger.MapPingerItemDAO;
import orm.entity.logan_park.vehicle.Vehicle;
import orm.entity.logan_park.vehicle.VehicleDAO;
import util.StringUtil;

import java.util.Date;

import static orm.entity.logan_park.map_pinger.taxi_brand.TaxiBrandDAO.BOLT;
import static ui_automation.uber.map_listener.UberMapListener.findStateId;

public class BoltMapPinger {
    private static final Logger LOGGER = Logger.getLogger(BoltMapPinger.class);

    public static void saveLodToDB(BoltDriverStatusResponse boltDriverStatusResponse) {
        for (BoltDriverStatusDataItem boltDriverStatusDataItem : boltDriverStatusResponse.getData().getList()) {
            MapPingerItem mapPingerItem = new MapPingerItem();
            mapPingerItem.setVehicle_id(findVehicleId(boltDriverStatusDataItem));
            mapPingerItem.setTimestamp(new Date().getTime());
            mapPingerItem.setState_id(findStateId(boltDriverStatusDataItem.getState(), BOLT.getBase_id()));
            mapPingerItem.setDriver_id(findDriverId(boltDriverStatusDataItem));
            mapPingerItem.setLng(boltDriverStatusDataItem.getLng());
            mapPingerItem.setLat(boltDriverStatusDataItem.getLat());
            MapPingerItemDAO.getInstance().save(mapPingerItem);
        }
    }

    private static Integer findDriverId(BoltDriverStatusDataItem boltDriverStatusDataItem) {
        UberDriverDAO uberDriverDAO=UberDriverDAO.getInstance();
        UberDriver driver = uberDriverDAO.findDriverByDriverName(boltDriverStatusDataItem.getName());
        if(driver==null){
            driver=uberDriverDAO.findDriverByBoltDriverName(boltDriverStatusDataItem.getName());
        }
        if (driver == null) {
            driver = new UberDriver();
            driver.setDriverType("usual40");
            driver.setName(boltDriverStatusDataItem.getName().replaceAll(" ", "_"));
            Integer id = (Integer) uberDriverDAO.save(driver);
            driver.setId(id);
            LOGGER.info("cant find driver, create default : " + driver);
        }
        return driver.getId();
    }

    private static Integer findVehicleId(BoltDriverStatusDataItem boltDriverStatusDataItem) {
        Vehicle vehicle = VehicleDAO.getInstance().findByPlate(boltDriverStatusDataItem.getCar_reg_number());
        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setName(boltDriverStatusDataItem.getModel().toUpperCase().replaceAll(" ", "_"));
            vehicle.setPlate(StringUtil.turnCyrillicLettersToEnglish(boltDriverStatusDataItem.getCar_reg_number().toUpperCase()));
            vehicle.setId((Integer) VehicleDAO.getInstance().save(vehicle));
        }
        return vehicle.getId();
    }
}
