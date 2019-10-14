package orm.entity.uber.payment_record_row;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class UberPaymentRecordRowPK implements Serializable {

    @Column
    private String tripUUID;
    @Column
    private Double amount;
    @Column
    private Date timestamp;
    @Column
    private String disclaimer;

    @Override
    public String toString() {
        return "UberPaymentRecordRowPK{" +
                "tripUUID='" + tripUUID + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", disclaimer='" + disclaimer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UberPaymentRecordRowPK that = (UberPaymentRecordRowPK) o;
        return Objects.equals(tripUUID, that.tripUUID) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(disclaimer, that.disclaimer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripUUID, amount, timestamp, disclaimer);
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

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }
}
