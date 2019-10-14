package orm.entity.logan_park.week_range;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "buy_price")
public class WeekRange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private Double start;

    @Column
    private Date end;

    @Override
    public String toString() {
        return "WeekRange{" +
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

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
