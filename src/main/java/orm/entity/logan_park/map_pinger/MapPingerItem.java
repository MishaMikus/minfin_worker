package orm.entity.logan_park.map_pinger;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "map_pinger_item")
public class MapPingerItem {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long base_id;
    @Column
    private Long timestamp = new Date().getTime();
    @Column
    private Double lat;
    @Column
    private Double lng;
    @Column
    private Integer state_id;
    @Column
    private Integer driver_id;
    @Column
    private Integer vehicle_id;

    @Override
    public String toString() {
        return "MapPingerItem{" +
                "base_id=" + base_id +
                ", timestamp=" + timestamp +
                ", lat=" + lat +
                ", lng=" + lng +
                ", state_id=" + state_id +
                ", driver_id=" + driver_id +
                ", vehicle_id=" + vehicle_id +
                '}';
    }

    public MapPingerItem() {
    }

    public Long getBase_id() {
        return base_id;
    }

    public void setBase_id(Long base_id) {
        this.base_id = base_id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }

    public Integer getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(Integer driver_id) {
        this.driver_id = driver_id;
    }

    public Integer getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Integer vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

}
