package server.logan_park.helper;

import org.apache.log4j.Logger;
import server.logan_park.helper.model.VehiclePerformance;
import util.IOUtils;

import java.io.File;
import java.util.*;

public class BrandingCalculator {

    private final static Logger LOGGER = Logger.getLogger(BrandingCalculator.class);

    public static void main(String[] args) {
        calculate();
    }

    private static void calculate() {
        List<VehiclePerformance> vehiclePerformanceList = new ArrayList<>();
        int i = 1;
        while (new File("F:/vehicle_performance" + i + ".csv").exists()) {
            File file = new File("F:/vehicle_performance" + i + ".csv");
            vehiclePerformanceList.addAll(parseFile(file));
            i++;
            LOGGER.info("parse : " + file.getAbsolutePath());
        }
        Map<String, Integer> res = new HashMap<>();

        vehiclePerformanceList.forEach(v -> {
                    String vehicle = v.getModel() + " " + v.getLicensePlate();
                    res.putIfAbsent(vehicle, 0);
                    res.put(vehicle, res.get(vehicle) + v.getTrips());
                }
        );
        System.out.println(res);
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

            res.setModel(parseString("Model", headerList, rowCells));
            res.setLicensePlate(parseString("License plate", headerList, rowCells));
            res.setNetFares(parseDouble("Net Fares", headerList, rowCells));
            res.setPerTrip(parseDouble("Per trip", headerList, rowCells));
            res.setPerHourOnline(parseDouble("Per hour online", headerList, rowCells));
            res.setPerKmOnTrip(parseDouble("Per km on trip", headerList, rowCells));
            res.setTrips(parseInteger("Trips", headerList, rowCells));
            res.setHoursOnline(parseHourceOnline(headerList, rowCells));
            res.setTripsPerHour(parseDouble("Trips per hour", headerList, rowCells));
            res.setDistancePerTrip(parseDistancePerTrip(headerList, rowCells));
        } else {
            LOGGER.warn("file header(firstRow) Failure");
        }
        return res;
    }

    private static Double parseHourceOnline(List<String> headerList, String[] rowCells) {
        String value = rowCells[headerList.indexOf("Hours online")];
        Integer h = Integer.parseInt(value.split("h ")[0]);
        Integer m = Integer.parseInt(value.split("h ")[1].split("m")[0]);
        return Math.round(100 * (m + h * 60) / 60d) / 100d;
    }

    private static Double parseDistancePerTrip(List<String> headerList, String[] rowCells) {
        String value = rowCells[headerList.indexOf("Distance per trip")];
        return Double.parseDouble(value.replaceAll(" KM", ""));
    }

    private static Integer parseInteger(String header, List<String> headerList, String[] values) {
        return Integer.parseInt(values[headerList.indexOf(header)]);
    }

    private static String parseString(String header, List<String> headerList, String[] values) {
        return values[headerList.indexOf(header)];
    }

    private static Double parseDouble(String header, List<String> headerList, String[] values) {
        try {
            return Double.parseDouble(values[headerList.indexOf(header)]);
        } catch (Exception e) {
            System.out.println("header : " + header);
            System.out.println("headerList : " + headerList);
            System.out.println("values : " + Arrays.asList(values));
            e.printStackTrace();
        }
        return null;
    }
}
