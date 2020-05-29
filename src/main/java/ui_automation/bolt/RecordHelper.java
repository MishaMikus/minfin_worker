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
    private static final CharSequence COMMA_TABLE_SEPARATOR = ",,,,,,,,,,,,,,,";

    public List<BoltPaymentRecordDay> recordDayReportToDB(String date, String content) {
        List<String> rowArrayList = new ArrayList<>(Arrays.asList(content.split("\r\n")));
        LOGGER.info("try parse file " + date);
        Integer weekId = new WeekRangeDAO().findOrCreateWeek(parseDate(date), "bolt_worker").getId();
        int lastRowIndex = findCommaSeparatedEmptyRow(rowArrayList);
        ArrayList<String> header = new ArrayList<>(Arrays.asList(rowArrayList.get(0).split(",")));
        List<String> dayRowArrayList = rowArrayList.subList(2, lastRowIndex);

        //TODO use that table for trip counting
        List<String> tripRowArrayList = rowArrayList.subList(lastRowIndex, rowArrayList.size());

        List<BoltPaymentRecordDay> boltPaymentRecordDayList = new ArrayList<>();
        for (String row : dayRowArrayList) {
            LOGGER.info("parse ROW : " + row);
            String[] splitRow = row.replaceAll("\"", "").split(",");

            //"Водій","Телефон водія","Період","Загальний тариф","Плата за скасування",
            // "Авторизаційцний платіж (платіж)","Авторизаційцний платіж (відрахування)","Додатковий збір",
            // "Комісія Bolt","Поїздки за готівку (зібрана готівка)","Сума знижки Bolt за готівкові поїздки ",
            // "Водійський бонус","Компенсації","Повернення коштів","Чайові","Тижневий баланс"

            //"Ігор Максим'як","+380632168490","День 2020-04-24","0.00","0.00","0.00","0.00","0.00","0.00","0.00",
            // "0.00","0.00","","0.00","0.00","0.00"

            String driverName = find("Водій", header, splitRow).replace(" ", "_");
            UberDriver driver = findOrCreateBoltDriver(driverName);
            int driverId = driver.getId();
            try {
                BoltPaymentRecordDay boltPaymentRecordDay = new BoltPaymentRecordDay();

                boltPaymentRecordDay.setCreation(new Date());
                boltPaymentRecordDay.setDriverName(driverName);
                boltPaymentRecordDay.setDriver_id(driverId);
                boltPaymentRecordDay.setTimestamp(parseDate(date));
                boltPaymentRecordDay.setAmount(findDouble("Загальний тариф", header, splitRow));
                boltPaymentRecordDay.setReject_amount(findDouble("Плата за скасування", header, splitRow));
                boltPaymentRecordDay.setBooking_payment_amount(findDouble("Авторизаційцний платіж (платіж)", header, splitRow));
                boltPaymentRecordDay.setBooking_payment_minus(findDouble("Авторизаційцний платіж (відрахування)", header, splitRow));
                boltPaymentRecordDay.setAdditional_payment(findDouble("Додатковий збір", header, splitRow));
                boltPaymentRecordDay.setBolt_commission(findDouble("Комісія Bolt", header, splitRow));
                boltPaymentRecordDay.setCash(findDouble("Поїздки за готівку (зібрана готівка)", header, splitRow));
                boltPaymentRecordDay.setCash_turn(findDouble("Сума знижки Bolt за готівкові поїздки ", header, splitRow));
                boltPaymentRecordDay.setBonus(findDouble("Водійський бонус", header, splitRow));
                boltPaymentRecordDay.setCompensation(findDouble("Компенсації", header, splitRow));
                boltPaymentRecordDay.setWeek_balance(findDouble("Тижневий баланс", header, splitRow));
                boltPaymentRecordDay.setWeek_id(weekId);

                boltPaymentRecordDayList.add(boltPaymentRecordDay);
            } catch (Exception e) {
                LOGGER.warn("can't parse row: " + row);
                e.printStackTrace();
            }
        }
        boltPaymentRecordDayList.forEach(System.out::println);
        return boltPaymentRecordDayList;
    }

    private Double findDouble(String key, ArrayList<String> header, String[] splitRow) {
        try {
            return Double.parseDouble(Objects.requireNonNull(find(key, header, splitRow)));
        }catch (Exception e){
            LOGGER.info("can't parse "+key);
        }
        return 0d;
    }

    private String find(String key, ArrayList<String> header, String[] splitRow) {
        int index = header.indexOf("\""+key+"\"");
        if (index >= 0 && index < splitRow.length) {
            return splitRow[index];
        }
        LOGGER.warn("invalid index for " + key);
        return null;
    }

    private int findCommaSeparatedEmptyRow(List<String> rowArrayList) {
        for (String row : rowArrayList) {
            if (row.contains(COMMA_TABLE_SEPARATOR)) {
                return rowArrayList.indexOf(row);
            }
        }
        return rowArrayList.indexOf(COMMA_TABLE_SEPARATOR);
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
        if(!monthTripCsv.exists()) return;
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
