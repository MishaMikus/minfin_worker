package orm.entity.logan_park.card;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "uber_filling_card")
public class FillingCard {
    @Id
    @Column
    private String id;
    @Column
    private String station;
    @Column
    private Integer vehicle_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Integer getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Integer vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    @Override
    public String toString() {
        return "FillingCard{" +
                "id=" + id +
                ", station='" + station + '\'' +
                ", vehicle_id=" + vehicle_id +
                '}';
    }
}
