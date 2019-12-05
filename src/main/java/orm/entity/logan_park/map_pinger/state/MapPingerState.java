package orm.entity.logan_park.map_pinger.state;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "map_pinger_state")
public class MapPingerState {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer base_id;
    @Column
    private String state;
    @Column
    private Integer taxi_brand_id;

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

    public Integer getTaxi_brand_id() {
        return taxi_brand_id;
    }

    public void setTaxi_brand_id(Integer taxi_brand_id) {
        this.taxi_brand_id = taxi_brand_id;
    }

    @Override
    public String toString() {
        return "MapPingerState{" +
                "base_id=" + base_id +
                ", state='" + state + '\'' +
                ", taxi_brand_id=" + taxi_brand_id +
                '}';
    }
}
