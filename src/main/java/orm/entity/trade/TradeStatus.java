package orm.entity.trade;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(schema = "minfin", name = "trade_status")
public class TradeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column
    private Date start_date;
    @Column
    private Date end_date;

    public TradeStatus() {
    }

    public TradeStatus(Date start_date) {
        this.start_date = start_date;
    }

    @Override
    public String toString() {
        return "TradeStatus{" +
                "id=" + id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeStatus that = (TradeStatus) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(start_date, that.start_date) &&
                Objects.equals(end_date, that.end_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start_date, end_date);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}