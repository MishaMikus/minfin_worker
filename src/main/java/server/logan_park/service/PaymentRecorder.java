package server.logan_park.service;

import org.apache.log4j.Logger;
import orm.entity.uber.description.UberDescription;
import orm.entity.uber.description.UberDescriptionDAO;
import orm.entity.uber.driver.UberDriverDAO;
import orm.entity.uber.item_type.UberItemTypeDAO;
import orm.entity.uber.payment_record_row.UberPaymentRecordRow;
import orm.entity.uber.payment_record_row.UberPaymentRecordRowDAO;
import parser.IOUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ui_automation.uber.bo.UberBO.PAYMENT_DATA_FILE;

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

    public static void main(String[] args) {
        new PaymentRecorder(IOUtils.readTextFromFile(PAYMENT_DATA_FILE)).recordToBD();
    }

    public void recordToBD() {
        LOGGER.info("primaryParsedData.size() : " + primaryParsedData.size());
        List<UberPaymentRecordRow> tobeRecorded = new ArrayList<>();
        if (primaryParsedData.size() > 0) {
            Integer weekHash = primaryParsedData.get(0).hashCode();
            UberPaymentRecordRow oldRecord = UberPaymentRecordRowDAO.getInstance().findLatest();
            List<UberPaymentRecordRow> oldRecordList =
                    oldRecord==null?new ArrayList<>()
                            :UberPaymentRecordRowDAO.getInstance().findAllWhereEqual("weekHash", oldRecord.getWeekHash());
            LOGGER.info("OLD_RECORD=" + oldRecord);
            for (PaymentRecordRawRow paymentRecordRawRow : primaryParsedData) {
                UberPaymentRecordRow uberPaymentRecordRow = new UberPaymentRecordRow();
                uberPaymentRecordRow.setAmount(paymentRecordRawRow.getAmount());
                uberPaymentRecordRow.setHash(paymentRecordRawRow.hashCode());
                uberPaymentRecordRow.setCreation(new Date());

                uberPaymentRecordRow.setWeekHash(weekHash);

                UberDescription uberDescription = UberDescriptionDAO.getInstance()
                        .getDescriptionByName(paymentRecordRawRow.getDescription());
                uberPaymentRecordRow.setDescription(uberDescription == null ? makeNewDescription(paymentRecordRawRow.getDescription()) : uberDescription.getId());

                uberPaymentRecordRow.setDisclaimer(paymentRecordRawRow.getDisclaimer());
                uberPaymentRecordRow.setDriverId(UberDriverDAO.getInstance().driverByUUID(paymentRecordRawRow.getDriverUUID()).getId());
                uberPaymentRecordRow.setFileRowIndex(primaryParsedData.indexOf(paymentRecordRawRow));
                uberPaymentRecordRow.setItemType(UberItemTypeDAO.getInstance()
                        .getItemTypeByName(paymentRecordRawRow.getItemType()).getId());
                uberPaymentRecordRow.setTimestamp(paymentRecordRawRow.getTimestamp());
                uberPaymentRecordRow.setTripUUID(paymentRecordRawRow.getTripUUID());

                if (oldRecord == null || !oldRecord.getWeekHash().equals(uberPaymentRecordRow.getWeekHash())) {
                    tobeRecorded.add(uberPaymentRecordRow);
                } else {
                    if (oldRecordList.stream().filter(r->r.getHash()
                            .equals(uberPaymentRecordRow.getHash()))
                            .findAny().orElse(null)==null) {
                        tobeRecorded.add(uberPaymentRecordRow);
                    }
                }
            }
        }
        LOGGER.info("tobeRecorded.size() : " + tobeRecorded.size());
        UberPaymentRecordRowDAO.getInstance().saveBatch(tobeRecorded);
        LOGGER.info("RECORDING DONE");
    }

    private Integer makeNewDescription(String description) {
        return UberDescriptionDAO.getInstance().addNewDescription(new UberDescription(description));
    }
}

