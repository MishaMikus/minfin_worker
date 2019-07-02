package orm.entity.trade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "trade_status")
public class TradeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column
    private Date start;
    @Column
    private Date end;

    @Override
    public String toString() {
        return "TradeStatus{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}