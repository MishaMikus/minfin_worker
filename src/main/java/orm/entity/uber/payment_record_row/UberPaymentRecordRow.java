package orm.entity.uber.payment_record_row;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "uber_payment_record_row")
public class UberPaymentRecordRow {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer fileRowIndex;
    private Integer driverId;
    private String tripUUID;
    private Double amount;
    private Date timestamp;
    private Date creation;
    private Integer itemType;
    private Integer description;
    private String disclaimer;
    private Integer weekHash;
    private Integer hash;

    public UberPaymentRecordRow() {
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getWeekHash() {
        return weekHash;
    }

    public void setWeekHash(Integer weekHash) {
        this.weekHash = weekHash;
    }

    @Override
    public String toString() {
        return "UberPaymentRecordRow{" +
                "id=" + id +
                ", fileRowIndex=" + fileRowIndex +
                ", driverId=" + driverId +
                ", tripUUID='" + tripUUID + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", creation=" + creation +
                ", itemType=" + itemType +
                ", description=" + description +
                ", disclaimer='" + disclaimer + '\'' +
                ", weekHash=" + weekHash +
                ", hash=" + hash +
                '}';
    }
}
