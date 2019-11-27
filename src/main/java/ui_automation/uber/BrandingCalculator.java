package ui_automation.uber;

import org.apache.log4j.Logger;
import server.logan_park.helper.model.VehiclePerformance;
import util.IOUtils;

import java.io.File;
import java.util.*;

import static ui_automation.uber.bo.UberBO.DOWNLOAD_FOLDER;
import static util.IOUtils.FS;

public class BrandingCalculator {

    private final static Logger LOGGER = Logger.getLogger(BrandingCalculator.class);

    public static void main(String[] args) {
        calculate();
    }

    private static void calculate() {
        List<VehiclePerformance> vehiclePerformanceList = new ArrayList<>();
        int i = 1;
        String path = DOWNLOAD_FOLDER + FS + "vehicle_performance" + i + ".csv";
        while (new File(path).exists()) {
            path = DOWNLOAD_FOLDER + FS + "vehicle_performance" + i + ".csv";
            File file = new File(path);
            try {
                if (file.exists()) {
                    vehiclePerformanceList.addAll(parseFile(file));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
            LOGGER.info("parse : " + file.getAbsolutePath());
        }
        Map<String, Integer> res = new HashMap<>();

        vehiclePerformanceList.forEach(v -> {
                    System.out.println(v);
                    String vehicle = v.getModel() + " " + v.getLicensePlate();
                    res.putIfAbsent(vehicle, 0);
                    res.put(vehicle, res.get(vehicle) + v.getTrips());
                }
        );
        System.out.println("res: " + res);
    }


    private static Collection<VehiclePerformance> parseFile(File file) {
        Collection<VehiclePerformance> res = new ArrayList<>();
        String content = IOUtils.readTextFromFile(file);
        boolean first = true;
        String[] headerRow = null;
        for (String row : content.split("\r\n")) {
            if (first) {
                first = false;
                headerRow = row.split(",");
            } else {
                res.add(makeVehiclePerformanceRecordFromRawRow(headerRow, row));
            }
        }
        return res;
    }

    private static VehiclePerformance makeVehiclePerformanceRecordFromRawRow(String[] headerRow, String row) {
        VehiclePerformance res = new VehiclePerformance();
        if (headerRow != null) {
            String[] rowCells = row.split(",");
            for (int i = 0; i < rowCells.length; i++) {
                rowCells[i] = rowCells[i].replaceAll("\"", "");
            }
            List<String> headerList = new ArrayList<>();
            for (String header : headerRow) {
                headerList.add(header.replaceAll("\"", ""));
            }
//"Модель","Номерний знак","ТАРИФИ БЕЗ ПОДАТКІВ І ЗБОРІВ","За поїздку","За годину на лінії","За кілометр поїздки","Поїздки","Години на лінії","Поїздок на годину","Відстань кожної поїздки"
            res.setModel(parseString(headerList, rowCells, "Model", "Модель"));
            res.setLicensePlate(parseString(headerList, rowCells, "License plate", "Номерний знак"));
            res.setNetFares(parseDouble(headerList, rowCells, "Net Fares", "ТАРИФИ БЕЗ ПОДАТКІВ І ЗБОРІВ"));
            res.setPerTrip(parseDouble(headerList, rowCells, "Per trip", "За поїздку"));
            res.setPerHourOnline(parseDouble(headerList, rowCells, "Per hour online", "За годину на лінії"));
            res.setPerKmOnTrip(parseDouble(headerList, rowCells, "Per km on trip", "За кілометр поїздки"));
            res.setTrips(parseInteger(headerList, rowCells, "Trips", "Поїздки"));
            res.setHoursOnline(parseHoursOnline(headerList, rowCells, "Hours online", "Години на лінії"));
            res.setTripsPerHour(parseDouble(headerList, rowCells, "Trips per hour", "Поїздок на годину"));
            res.setDistancePerTrip(parseDistancePerTrip(headerList, rowCells,"Distance per trip","Відстань кожної поїздки"));
        } else {
            LOGGER.warn("file header(firstRow) Failure");
        }
        return res;
    }

    private static Double parseHoursOnline(List<String> headerList, String[] rowCells, String... headerPossibleArray) {
        String value = rowCells[findIndex(headerList, headerPossibleArray)];
        Integer h = Integer.parseInt(value.split("h ")[0]);
        Integer m = Integer.parseInt(value.split("h ")[1].split("m")[0]);
        return Math.round(100 * (m + h * 60) / 60d) / 100d;
    }

    private static Double parseDistancePerTrip(List<String> headerList, String[] rowCells, String... headerPossibleArray) {
        String value = rowCells[findIndex(headerList, headerPossibleArray)];
        return Double.parseDouble(value.replaceAll(" KM", ""));
    }

    private static Integer parseInteger(List<String> headerList, String[] values, String... headerPossibleArray) {
        return Integer.parseInt(values[findIndex(headerList, headerPossibleArray)]);
    }

    private static String parseString(List<String> headerList, String[] values, String... headerPossibleArray) {
        return values[findIndex(headerList, headerPossibleArray)];
    }

    private static Double parseDouble(List<String> headerList, String[] values, String... headerPossibleArray) {
        int i = findIndex(headerList, headerPossibleArray);
        try {
            return Double.parseDouble(values[i]);
        } catch (Exception e) {
            System.out.println("header : " + i);
            System.out.println("headerList : " + headerList);
            System.out.println("values : " + Arrays.asList(values));
            e.printStackTrace();
        }
        return null;
    }

    private static int findIndex(List<String> headerList, String[] headerPossibleArray) {
        for (String headerCandidate : headerPossibleArray) {
            if (headerList.indexOf(headerCandidate) != -1) return headerList.indexOf(headerCandidate);
        }
        System.out.println(headerList);
        System.out.println(new ArrayList<>(Arrays.asList(headerPossibleArray)));
        return -1;
    }
}
