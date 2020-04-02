package ui_automation.bolt;

import org.apache.log4j.Logger;
import orm.entity.bolt.payment_record_day.BoltPaymentRecordDay;
import orm.entity.bolt.payment_record_day.BoltPaymentRecordDayDAO;
import orm.entity.logan_park.driver.UberDriver;
import orm.entity.logan_park.driver.UberDriverDAO;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import util.IOUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecordHelper {
    public static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("dd.MM.yyyy");
    //"05.10.2019 21:41
    public static final SimpleDateFormat SDF_DATE_TIME = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private final static Logger LOGGER = Logger.getLogger(RecordHelper.class);
    private static final String DEFAULT_DRIVER_TYPE = "usual40";

    public List<BoltPaymentRecordDay> recordDayReportToDB(String date, String content) {
        List<String> rowArrayList = new ArrayList<>(Arrays.asList(content.split("\r\n")));
        LOGGER.info("try parse file " + date);
        Integer weekId = new WeekRangeDAO().findOrCreateWeek(parseDate(date), "bolt_worker").getId();
        int lastRowIndex = rowArrayList.indexOf(",,,,,,,,,,,,,,");
        lastRowIndex = lastRowIndex == -1 ? rowArrayList.size() - 1 : lastRowIndex;

        List<String> dayRowArrayList = rowArrayList.subList(2, lastRowIndex);
        List<String> tripRowArrayList = rowArrayList.subList(lastRowIndex, rowArrayList.size());
        tripRowArrayList.forEach(System.out::println);
        List<BoltPaymentRecordDay> boltPaymentRecordDayList = new ArrayList<>();
        for (String row : dayRowArrayList) {
            LOGGER.info("parse ROW : " + row);
            String[] cellArray = row.replaceAll("\"", "").split(",");
            //"Водій","Телефон водія","Період","Загальний тариф","Плата за скасування","Збір за бронювання (платіж)","Збір за бронювання (відрахування)","Додаткові збори","Комісія Bolt","Готівкові поїздки (отримано водієм)","Компенсована сума знижки Bolt за готівкові поїздки ","Водійський бонус","Компенсації","Тижневий баланс"
            //"Юрій Горбатий","+380961066201","День 2019-10-05","268.00","0.00","0.00","0.00","0.00","-32.16","-172.00","30.00","0.00","0.00","63.84"
            String driverName=cellArray[0].replace(" ", "_");
            UberDriver driver = findOrCreateBoltDriver(driverName);
            int driverId = driver.getId();
            boltPaymentRecordDayList.add(new BoltPaymentRecordDay(
                    new Date(),
                    cellArray[0].replace(" ", "_"),
                    driverId,
                    parseDate(date),
                    Double.parseDouble(cellArray[3]),
                    Double.parseDouble(cellArray[4]),
                    Double.parseDouble(cellArray[5]),
                    Double.parseDouble(cellArray[6]),
                    Double.parseDouble(cellArray[7]),
                    Double.parseDouble(cellArray[8]),
                    Double.parseDouble(cellArray[9]),
                    Double.parseDouble(cellArray[10]),
                    Double.parseDouble(cellArray[11]),
                    Double.parseDouble(cellArray[12]),
                    Double.parseDouble(cellArray[13]),
                    weekId
            ));

        }
        return boltPaymentRecordDayList;
    }

    private UberDriver findOrCreateBoltDriver(String driverName) {
        UberDriverDAO uberDriverDAO = UberDriverDAO.getInstance();
        UberDriver uberDriver = uberDriverDAO.findDriverByDriverName(driverName);
        if (uberDriver == null) {
            uberDriver = uberDriverDAO.findDriverByBoltDriverName(driverName);
        }
        if (uberDriver == null) {
            uberDriver = new UberDriver();
            uberDriver.setDriverType(DEFAULT_DRIVER_TYPE);
            uberDriver.setBolt_name(driverName);
            uberDriver.setDriverUUID("fake_bolt_UUID");
            uberDriver.setName(driverName);
            Integer id = (Integer) uberDriverDAO.save(uberDriver);
            uberDriver.setId(id);
            LOGGER.info("cant find driver, create default : " + uberDriver);
        }
        return uberDriver;
    }

    private Date parseDate(String content) {
        return parseDate(content, SDF_DATE);
    }

    private Date parseDate(String content, SimpleDateFormat sdf) {
        try {
            return sdf.parse(content);
        } catch (ParseException e) {
            LOGGER.warn("Date parsing error for pattern " + sdf + " \n" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Date parseDateTime(String content) {
        return parseDate(content, SDF_DATE_TIME);
    }

    public void recordDayReportToDB(Map<String, File> downloadAllCSV) {
        List<BoltPaymentRecordDay> res = new ArrayList<>();
        for (Map.Entry<String, File> entry : downloadAllCSV.entrySet()) {
            res.addAll(recordDayReportToDB(entry.getKey(), Objects.requireNonNull(IOUtils.readTextFromFile(entry.getValue()))));
            entry.getValue().deleteOnExit();
        }
        BoltPaymentRecordDayDAO.getInstance().saveBatch(res);
    }

    public static void main(String[] args) {
        new RecordHelper().recordMonthTripToDB(new File("C:\\Users\\mykhailo.mikus\\Downloads\\Bolt - Рахунки пасажирів - Жовтень 2019.csv"));
    }

    public void recordMonthTripToDB(File monthTripCsv) {
        String content = IOUtils.readTextFromFile(monthTripCsv);
        monthTripCsv.deleteOnExit();
        List<String> rowArrayList = new ArrayList<>(Arrays.asList(content.split("\n")));
        // "Номер рахунку",
        // "Дата",
        // "Водій",
        // "Адреса посадки",
        // "Спосіб оплати",
        // "Дата поїздки",
        // "Одержувач",
        // "Адреса одержувача",
        // "Реєстраційний номер одержувача",
        // "NIM / ПДВ номер одержувача",
        // "Назва компанії (водія)",
        // "Адреса компанії (вулиця, номер, індекс, країна)",
        // "Реєстраційний номер компанії",
        // "NIM / ПДВ номер компанії",
        // "Ціна (без ПДВ)",
        // "ПДВ",
        // "Загальна вартість"

        //"15703008846570",
        // "05.10.2019 21:41",
        // "Юрій Горбатий",
        // "Kniazia Sviatoslava Square 5, L'viv",
        // "Платежі Bolt",
        // "05.10.2019 21:28",
        // "Illia",
        // "",
        // "",
        // "",
        // "*",
        // "",
        // "",
        // "",
        // "66",
        // "0",
        // "66"

        rowArrayList = rowArrayList.subList(1, rowArrayList.size());
        List<BoltTripRecord> res = new ArrayList<>();
        for (String row : rowArrayList) {
            BoltTripRecord boltTripRecord = new BoltTripRecord();
            try {
                String[] cellArray = row.split("\",\"");
                boltTripRecord.setBillNumber(Long.parseLong(cellArray[0].replaceAll("\"", "")));
                boltTripRecord.setDate(parseDateTime(cellArray[1]));
                boltTripRecord.setDriver(cellArray[2]);
                boltTripRecord.setStartAddress(cellArray[3]);
                boltTripRecord.setPayMethod(cellArray[4]);
                boltTripRecord.setTravelDate(parseDateTime(cellArray[5]));
                boltTripRecord.setReceiver(cellArray[6]);
                boltTripRecord.setReceiverAddress(cellArray[7]);
                boltTripRecord.setReceiverNumber(cellArray[8]);
                boltTripRecord.setTaxNumber(cellArray[9]);
                boltTripRecord.setDriverCompanyName(cellArray[10]);
                boltTripRecord.setDriverCompanyAddress(cellArray[11]);
                boltTripRecord.setDriverCompanyNumber(cellArray[12]);
                boltTripRecord.setDriverCompanyTaxNumber(cellArray[13]);
                boltTripRecord.setPrice(Integer.parseInt(cellArray[14]));
                boltTripRecord.setTax(Integer.parseInt(cellArray[15]));
                boltTripRecord.setAmount(Integer.parseInt(cellArray[16].replaceAll("\"", "")));
                res.add(boltTripRecord);
            } catch (Exception e) {
                LOGGER.warn("can't parse row : " + row);
                LOGGER.warn("current object : " + boltTripRecord);
                e.printStackTrace();
            }
        }
        //TODO

    }

    public void recordMonthTripPDFMapping(String pageSource) {
        //TODO
    }
}
