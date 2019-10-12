package ui_automation.bolt;

import org.apache.log4j.Logger;
import orm.entity.bolt.BoltPaymentRecordDay;
import orm.entity.bolt.BoltPaymentRecordDayDAO;
import util.IOUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecordHelper {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");
    private final static Logger LOGGER = Logger.getLogger(RecordHelper.class);

    public List<BoltPaymentRecordDay> recordDayReportToDB(String date, String content) {
        List<String> rowArrayList = new ArrayList<>(Arrays.asList(content.split("\r\n")));
        LOGGER.info("try parse file " + date);
        int lastRowIndex = rowArrayList.indexOf(",,,,,,,,,,,,,");
        lastRowIndex = lastRowIndex == -1 ? rowArrayList.size() - 1 : lastRowIndex;

        rowArrayList = rowArrayList.subList(2, lastRowIndex);
        List<BoltPaymentRecordDay> boltPaymentRecordDayList = new ArrayList<>();
        for (String row : rowArrayList) {
            System.out.println("parse ROW : " + row);
            String[] cellArray = row.replaceAll("\"", "").split(",");
            BoltPaymentRecordDay boltPaymentRecordDay = new BoltPaymentRecordDay();
            //"Водій","Телефон водія","Період","Загальний тариф","Плата за скасування","Збір за бронювання (платіж)","Збір за бронювання (відрахування)","Додаткові збори","Комісія Bolt","Готівкові поїздки (отримано водієм)","Компенсована сума знижки Bolt за готівкові поїздки ","Водійський бонус","Компенсації","Тижневий баланс"
            //"Юрій Горбатий","+380961066201","День 2019-10-05","268.00","0.00","0.00","0.00","0.00","-32.16","-172.00","30.00","0.00","0.00","63.84"

            boltPaymentRecordDayList.add(new BoltPaymentRecordDay(
                    new Date(),
                    cellArray[0].replace(" ", "_"),
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
                    Double.parseDouble(cellArray[13])
            ));
        }
        return boltPaymentRecordDayList;
    }

    private Date parseDate(String stringDate) {
        try {
            return SDF.parse(stringDate);
        } catch (ParseException e) {
            LOGGER.warn("Date parsing error for pattern " + SDF + " \n" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void recordDayReportToDB(Map<String, File> downloadAllCSV) {
        List<BoltPaymentRecordDay> res = new ArrayList<>();
        for (Map.Entry<String, File> entry : downloadAllCSV.entrySet()) {
            res.addAll(recordDayReportToDB(entry.getKey(), IOUtils.readTextFromFile(entry.getValue())));
        }
        BoltPaymentRecordDayDAO.getInstance().saveBatch(res);
    }
}
