package server.logan.park.service;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Date;

public class PaymentRecordRow {
    private String driverUUID;
    private String tripUUID;
    private String firstName;
    private String lastName;
    private Double amount;
    private Date timestamp;
    private String itemType;
    private String description;
    private String disclaimer;

    private final static Logger LOGGER = Logger.getLogger(PaymentRecordRow.class);

    public static PaymentRecordRow makeMeFromStringRow(String row,int i) {
        //driverUUID	tripUUID	firstName	lastName	amount	timestamp	itemType	description	disclaimer
        //2a180965-5af6-41ea-bfd6-c59cb99e4268		Михайло	Мікусь	-32641.2	2019-08-26T04:00:19+03:00	payouts	Виплати
        //198ed624-f207-4229-96ad-7ab9d1e320db	b860be38-f63d-4a8a-a9c8-78bc79487634	Андрій	Павлеса	45.80	2019-08-26T07:35:54+03:00	trip	UberX
        LOGGER.info("row[" + i + "]:" + (i) + " " + row);
        row = row.replaceAll("\"", "");
        String[] rowArray = row.split(",");

        //2019-07-24T11:08:21+03:00
        Date timestamp = null;
        try {
            timestamp = PaymentTabHelper.SDF.parse(rowArray[5].split("\\+")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PaymentRecordRow paymentRecordRow=new PaymentRecordRow();
        paymentRecordRow.setAmount(Double.parseDouble(rowArray[4]));
        paymentRecordRow.setDescription(rowArray.length<=7?"":rowArray[7]);
        paymentRecordRow.setDisclaimer(rowArray.length<=8?"":rowArray[8]);
        paymentRecordRow.setDriverUUID(rowArray[0]);
        paymentRecordRow.setFirstName(rowArray[2]);
        paymentRecordRow.setLastName(rowArray[3]);
        paymentRecordRow.setItemType(rowArray[6]);
        paymentRecordRow.setTimestamp(timestamp);
        paymentRecordRow.setTripUUID(rowArray[1]);

        return paymentRecordRow;
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
}
