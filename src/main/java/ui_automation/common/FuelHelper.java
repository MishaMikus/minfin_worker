package ui_automation.common;

import org.apache.log4j.Logger;
import orm.entity.logan_park.card.FillingCard;
import orm.entity.logan_park.card.FillingCardDAO;
import orm.entity.logan_park.filling.FillingRecord;
import orm.entity.logan_park.vehicle.Vehicle;
import orm.entity.logan_park.vehicle.VehicleDAO;
import server.logan_park.view.filling_report.model.DateLabel;
import server.logan_park.view.filling_report.model.FillingTable;
import server.logan_park.view.filling_report.model.FillingValue;
import server.logan_park.view.filling_report.model.FuelCosts;
import util.NumberHelper;

import java.util.*;
import java.util.stream.Collectors;

public class FuelHelper {
    private final static Logger LOGGER = Logger.getLogger(FuelHelper.class);
    private final static List<FillingCard> fillingCardList = FillingCardDAO.getInstance().findAll();
    private final static List<Vehicle> vehicleList = VehicleDAO.getInstance().findAll();

    private static final FuelHelper INSTANCE = new FuelHelper();

    public static FuelHelper getInstance() {
        return INSTANCE;
    }

    public String findOutCarIdentity(String card) {

        FillingCard fillingCard = fillingCardList.stream().filter(f -> f.getId().equals(card)).findAny().orElse(null);
        if (fillingCard == null) {
            LOGGER.warn("find new card : " + card + " UPG");
            fillingCard = new FillingCard();
            fillingCard.setId(card);
            fillingCard.setStation("upg");
            FillingCardDAO.getInstance().save(fillingCard);
        }

        FillingCard finalFillingCard = fillingCard;
        Vehicle vehicle = vehicleList.stream().filter(v -> v.getId().equals(finalFillingCard.getVehicle_id())).findAny().orElse(null);
        if (vehicle == null) {
            LOGGER.warn("unassigned card detected");
            return "UNKNOWN_CAR";
        } else {
            return vehicle.getName() + "_" + vehicle.getPlate();
        }
    }

    public void calculateFuelCost(FillingTable fillingTable, List<FillingRecord> previousWeekFillingRecordList) {
        LOGGER.info("Calculate fuel cost");
        for (Map.Entry<String, FillingValue> entry : fillingTable.getFillingInfo().getCarDistributedMap().entrySet()) {
            entry.getValue().setFuelCostsList(calculateCostList(entry.getKey(),
                    fillingTable.getFillingRecordMap(),
                    findAllLatestFillingForPreviousWeek(previousWeekFillingRecordList, entry.getKey())));
        }

    }

    private List<FillingRecord> findAllLatestFillingForPreviousWeek(List<FillingRecord> previousWeekFillingRecordList, String car) {
        Map<String, FillingRecord> result = new HashMap<>();
        previousWeekFillingRecordList.forEach(f -> {
            if (f.getCar().equals(car)) {
                result.putIfAbsent(f.getCard(), f);
                FillingRecord old = result.get(f.getCard());
                if (old.getDate().getTime() < f.getDate().getTime()) {
                    result.put(f.getCard(), f);
                }
            }
        });
        System.out.println(result.values());
        return new ArrayList<>(result.values());
    }


    private List<FuelCosts> calculateCostList(String car, Map<DateLabel, List<FillingRecord>> fillingRecordMap, List<FillingRecord> latestFillingList) {
        List<FuelCosts> fuelCostsList = new ArrayList<>();
        List<FillingRecord> carFillingRecordList = new ArrayList<>(latestFillingList);
        for (Map.Entry<DateLabel, List<FillingRecord>> entry : fillingRecordMap.entrySet()) {
            for (FillingRecord fillingRecord : entry.getValue()) {
                if (fillingRecord.getCar().equals(car)) {
                    carFillingRecordList.add(fillingRecord);
                }
            }
        }

        carFillingRecordList.sort(Comparator.comparing(FillingRecord::getDate));
        Collections.reverse(carFillingRecordList);
        for (int i = 0; i < carFillingRecordList.size() - 1; i++) {
            FillingRecord fillingRecordEnd = carFillingRecordList.get(i);
            FillingRecord fillingRecordStart = carFillingRecordList.get(i + 1);
            Date end = fillingRecordEnd.getDate();
            Date start = fillingRecordStart.getDate();
            if (fillingRecordStart.getKm() != null && fillingRecordEnd.getKm() != null) {
                int km = fillingRecordEnd.getKm() - fillingRecordStart.getKm();
                Double l = fillingRecordEnd.getItemAmount();
                if (km != 0) {
                    double costs = (l * 100.0) / ((double) km);
                    FuelCosts fuelCosts = new FuelCosts();
                    fuelCosts.setCar(car);
                    fuelCosts.setCost(NumberHelper.round100(costs));
                    fuelCosts.setDistance(km);
                    fuelCosts.setGasAmount(l);
                    fuelCosts.getDateLabelStart().setDate(start);
                    fuelCosts.getDateLabelEnd().setDate(end);
                    fuelCostsList.add(fuelCosts);
                }
            }
        }
        fuelCostsList = fuelCostsList.stream().distinct().collect(Collectors.toList());
        fuelCostsList.sort(Comparator.comparing(o -> o.getDateLabelStart().getDate()));
        Collections.reverse(fuelCostsList);
        return fuelCostsList;
    }
}
