package server.logan_park.service;

import org.apache.log4j.Logger;
import orm.entity.uber.description.UberDescription;
import orm.entity.uber.description.UberDescriptionDAO;
import orm.entity.uber.driver.UberDriver;
import orm.entity.uber.driver.UberDriverDAO;
import orm.entity.uber.item_type.UberItemType;
import orm.entity.uber.item_type.UberItemTypeDAO;
import orm.entity.uber.payment_record_row.UberPaymentRecordRow;
import server.logan_park.helper.ManuallyWeeklyReportHelper;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class PaymentRecordRawRow {
    private String driverUUID;
    private String tripUUID;
    private String firstName;
    private String lastName;
    private Double amount;
    private Date timestamp;
    private String itemType;
    private String description;
    private String disclaimer;
    private Integer weekHash;//first record hash

    public static PaymentRecordRawRow makeMeFromDBRow(UberPaymentRecordRow row) {
        UberDriver uberDriver= UberDriverDAO.getInstance().driverById(row.getDriverId());
        UberItemType uberItemType= UberItemTypeDAO.getInstance().itemById(row.getItemType());
        UberDescription uberDescription= UberDescriptionDAO.getInstance().descriptionById(row.getDescription());
        PaymentRecordRawRow paymentRecordRawRow =new PaymentRecordRawRow();
        paymentRecordRawRow.setAmount(row.getAmount());
        paymentRecordRawRow.setDescription(uberDescription.getName());
        paymentRecordRawRow.setDisclaimer(row.getDisclaimer());
        paymentRecordRawRow.setDriverUUID(uberDriver.getDriverUUID());
        paymentRecordRawRow.setFirstName(uberDriver.getName().split("_")[0]);
        paymentRecordRawRow.setLastName(uberDriver.getName().split("_")[1]);
        paymentRecordRawRow.setItemType(uberItemType.getName());
        paymentRecordRawRow.setTimestamp(row.getTimestamp());
        paymentRecordRawRow.setTripUUID(row.getTripUUID());
        return paymentRecordRawRow;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentRecordRawRow that = (PaymentRecordRawRow) o;
        return Objects.equals(driverUUID, that.driverUUID) &&
                Objects.equals(tripUUID, that.tripUUID) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(itemType, that.itemType) &&
                Objects.equals(description, that.description) &&
                Objects.equals(disclaimer, that.disclaimer) &&
                Objects.equals(weekHash, that.weekHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverUUID, tripUUID, firstName, lastName, amount, timestamp, itemType, description, disclaimer, weekHash);
    }

    public Integer getWeekHash() {
        return weekHash;
    }

    public void setWeekHash(Integer weekHash) {
        this.weekHash = weekHash;
    }

    private final static Logger LOGGER = Logger.getLogger(PaymentRecordRawRow.class);

    public static PaymentRecordRawRow makeMeFromStringRow(String row, int i) {
        //driverUUID	tripUUID	firstName	lastName	amount	timestamp	itemType	description	disclaimer
        //2a180965-5af6-41ea-bfd6-c59cb99e4268		Михайло	Мікусь	-32641.2	2019-08-26T04:00:19+03:00	payouts	Виплати
        //198ed624-f207-4229-96ad-7ab9d1e320db	b860be38-f63d-4a8a-a9c8-78bc79487634	Андрій	Павлеса	45.80	2019-08-26T07:35:54+03:00	trip	UberX
       // LOGGER.info("row[" + i + "]:" + (i) + " " + row);
        row = row.replaceAll("\"", "");
        String[] rowArray = row.split(",");

        //2019-07-24T11:08:21+03:00
        Date timestamp = null;
        try {
            timestamp = ManuallyWeeklyReportHelper.SDF.parse(rowArray[5].split("\\+")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PaymentRecordRawRow paymentRecordRawRow =new PaymentRecordRawRow();
        paymentRecordRawRow.setAmount(Double.parseDouble(rowArray[4]));
        paymentRecordRawRow.setDescription(rowArray.length<=7?"":rowArray[7]);
        paymentRecordRawRow.setDisclaimer(rowArray.length<=8?"":rowArray[8]);
        paymentRecordRawRow.setDriverUUID(rowArray[0]);
        paymentRecordRawRow.setFirstName(rowArray[2]);
        paymentRecordRawRow.setLastName(rowArray[3]);
        paymentRecordRawRow.setItemType(rowArray[6]);
        paymentRecordRawRow.setTimestamp(timestamp);
        paymentRecordRawRow.setTripUUID(rowArray[1]);

        return paymentRecordRawRow;
    }

    public String getDriverUUID() {
        return driverUUID;
    }

    public void setDriverUUID(String driverUUID) {
        this.driverUUID = driverUUID;
    }

    public String getTripUUID() {
        return tripUUID;
    }

    public void setTripUUID(String tripUUID) {
        this.tripUUID = tripUUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String driverName() {
        return firstName+"_"+lastName;
    }

    @Override
    public String toString() {
        return "PaymentRecordRawRow{" +
                "driverUUID='" + driverUUID + '\'' +
                ", tripUUID='" + tripUUID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", itemType='" + itemType + '\'' +
                ", description='" + description + '\'' +
                ", disclaimer='" + disclaimer + '\'' +
                ", weekHash=" + weekHash +
                '}';
    }
}
