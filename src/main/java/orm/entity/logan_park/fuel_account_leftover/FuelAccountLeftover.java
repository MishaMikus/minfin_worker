package orm.entity.logan_park.fuel_account_leftover;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "fuel_account_leftover")
public class FuelAccountLeftover {
    @Id
    @Column
    private Date date;
    @Column
    private String station;
    @Column
    private Double value;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
