package orm.entity.uber.driver_realtime_table;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "uber_driver_realtime")
public class UberDriverRealTime {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer base_id;
    @Column
    private Integer driver_id;
    @Column
    private Long timestamp;
    @Column
    private Long lastTimeOnline;
    @Column
    private Integer realtime_state_id;

    @Override
    public String toString() {
        return "UberDriverRealTime{" +
                "base_id=" + base_id +
                ", driver_id=" + driver_id +
                ", timestamp=" + timestamp +
                ", lastTimeOnline=" + lastTimeOnline +
                ", realtime_state_id=" + realtime_state_id +
                '}';
    }

    public Integer getBase_id() {
        return base_id;
    }

    public void setBase_id(Integer base_id) {
        this.base_id = base_id;
    }

    public Integer getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(Integer driver_id) {
        this.driver_id = driver_id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getLastTimeOnline() {
        return lastTimeOnline;
    }

    public void setLastTimeOnline(Long lastTimeOnline) {
        this.lastTimeOnline = lastTimeOnline;
    }

    public Integer getRealtime_state_id() {
        return realtime_state_id;
    }

    public void setRealtime_state_id(Integer realtime_state_id) {
        this.realtime_state_id = realtime_state_id;
    }
}