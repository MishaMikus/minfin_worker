package server.logan_park.service;

import org.apache.log4j.Logger;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import orm.entity.uber.description.UberDescription;
import orm.entity.uber.description.UberDescriptionDAO;
import orm.entity.logan_park.driver.UberDriver;
import orm.entity.logan_park.driver.UberDriverDAO;
import orm.entity.uber.item_type.UberItemTypeDAO;
import orm.entity.uber.payment_record_row.UberPaymentRecordRow;
import orm.entity.uber.payment_record_row.UberPaymentRecordRowDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentRecorder {

    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private String content;

    public PaymentRecorder(String content) {
        this.content = content;
        parsePrimaryData();
    }

    private List<PaymentRecordRawRow> primaryParsedData;

    private void parsePrimaryData() {
        primaryParsedData = new ArrayList<>();
        boolean firstRow = true;
        int i = 0;
        for (String row : content.split("\n")) {
            if (firstRow) {
                firstRow = false;
            } else {
                primaryParsedData.add(PaymentRecordRawRow.makeMeFromStringRow(row, i++));
            }
        }
    }

    private List<UberDriver> driverList = UberDriverDAO.getInstance().findAll();

    public void recordToBD(Date weekFlag) {
        LOGGER.info("primaryParsedData.size() : " + primaryParsedData.size());
        Integer week_id = WeekRangeDAO.getInstance().findOrCreateWeek(weekFlag).getId();
        List<UberPaymentRecordRow> tobeRecorded = new ArrayList<>();
        if (primaryParsedData.size() > 0) {
            UberPaymentRecordRow oldRecord = UberPaymentRecordRowDAO.getInstance().findLatest();
            LOGGER.info("OLD_RECORD=" + oldRecord);
            for (PaymentRecordRawRow paymentRecordRawRow : primaryParsedData) {
                UberPaymentRecordRow uberPaymentRecordRow = new UberPaymentRecordRow();
                uberPaymentRecordRow.setAmount(paymentRecordRawRow.getAmount());
                uberPaymentRecordRow.setCreation(new Date());

                uberPaymentRecordRow.setWeek_id(week_id);

                UberDescription uberDescription = UberDescriptionDAO.getInstance()
                        .getDescriptionByName(paymentRecordRawRow.getDescription());
                uberPaymentRecordRow.setDescription(uberDescription == null ? makeNewDescription(paymentRecordRawRow.getDescription()) : uberDescription.getId());

                uberPaymentRecordRow.setDisclaimer(paymentRecordRawRow.getDisclaimer());

                Integer id = findDriverId(paymentRecordRawRow);

                uberPaymentRecordRow.setDriverId(id);
                uberPaymentRecordRow.setFileRowIndex(primaryParsedData.indexOf(paymentRecordRawRow));
                uberPaymentRecordRow.setItemType(UberItemTypeDAO.getInstance()
                        .getItemTypeByName(paymentRecordRawRow.getItemType()).getId());
                uberPaymentRecordRow.setTimestamp(paymentRecordRawRow.getTimestamp());
                uberPaymentRecordRow.setTripUUID(paymentRecordRawRow.getTripUUID());
                tobeRecorded.add(uberPaymentRecordRow);
            }
        }
        LOGGER.info("tobeRecorded.size() : " + tobeRecorded.size());

        UberPaymentRecordRowDAO.getInstance().saveBatch(tobeRecorded);
        LOGGER.info("RECORDING DONE");
    }

    private Integer findDriverId(PaymentRecordRawRow paymentRecordRawRow) {
        UberDriver driver = driverList.stream().filter(d -> d.getDriverUUID().equals(paymentRecordRawRow.getDriverUUID())).findAny().orElse(null);
        //add default new driver
        if (driver == null) {
            driver = new UberDriver();
            driver.setDriverType("usual40");
            driver.setDriverUUID(paymentRecordRawRow.getDriverUUID());
            driver.setName(paymentRecordRawRow.driverName());
            Integer id = (Integer) UberDriverDAO.getInstance().save(driver);
            driver.setId(id);
            LOGGER.info("cant find driver, create default : " + driver);
            driverList = UberDriverDAO.getInstance().findAll();
        }
        return driver.getId();
    }

    private Integer makeNewDescription(String description) {
        return UberDescriptionDAO.getInstance().addNewDescription(new UberDescription(description));
    }
}

