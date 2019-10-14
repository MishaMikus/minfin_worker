package orm.entity.uber.payment_record_row;

import orm.entity.bolt.BoltPaymentRecordDayPK;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "uber_payment_record_row")
@IdClass(UberPaymentRecordRowPK.class)
public class UberPaymentRecordRow {
    @Id
    @Column
    private String tripUUID;
    @Id
    @Column
    private Double amount;
    @Id
    @Column
    private Date timestamp;

    private Integer fileRowIndex;
    private Integer driverId;
    private Date creation;
    private Integer itemType;
    private Integer description;
    @Id
    @Column
    private String disclaimer;
    private Integer week_id;

    public UberPaymentRecordRow() {
    }


    public Integer getFileRowIndex() {
        return fileRowIndex;
    }

    public void setFileRowIndex(Integer fileRowIndex) {
        this.fileRowIndex = fileRowIndex;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getTripUUID() {
        return tripUUID;
    }

    public void setTripUUID(String tripUUID) {
        this.tripUUID = tripUUID;
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

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Integer getWeek_id() {
        return week_id;
    }

    public void setWeek_id(Integer week_id) {
        this.week_id = week_id;
    }



    @Override
    public String toString() {
        return "UberPaymentRecordRow{" +
                "fileRowIndex=" + fileRowIndex +
                ", driverId=" + driverId +
                ", tripUUID='" + tripUUID + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", creation=" + creation +
                ", itemType=" + itemType +
                ", description=" + description +
                ", disclaimer='" + disclaimer + '\'' +
                ", week_id=" + week_id +
                '}';
    }
}
