package orm.entity.bolt.payment_record_day;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class BoltPaymentRecordDayPK implements Serializable {

    @Column
    private String driverName;
    @Column
    private Date timestamp;

    @Override
    public String toString() {
        return "BoltPaymentRecordDayPK{" +
                "driverName='" + driverName + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoltPaymentRecordDayPK that = (BoltPaymentRecordDayPK) o;
        return Objects.equals(driverName, that.driverName) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverName, timestamp);
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
