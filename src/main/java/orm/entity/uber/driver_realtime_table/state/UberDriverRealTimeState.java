package orm.entity.uber.driver_realtime_table.state;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "uber_driver_realtime_state")
public class UberDriverRealTimeState {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer base_id;
    @Column
    private String state;

    @Override
    public String toString() {
        return "UberDriverRealTimeState{" +
                "base_id=" + base_id +
                ", state='" + state + '\'' +
                '}';
    }

    public Integer getBase_id() {
        return base_id;
    }

    public void setBase_id(Integer base_id) {
        this.base_id = base_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}