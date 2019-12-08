package orm.entity.logan_park.vehicle;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "uber_vehicle")
public class Vehicle {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String name;
    @Column
    private String plate;
    @Column
    private Integer tracker_id;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", plate='" + plate + '\'' +
                ", tracker_id=" + tracker_id +
                '}';
    }

    public Integer getTracker_id() {
        return tracker_id;
    }

    public void setTracker_id(Integer tracker_id) {
        this.tracker_id = tracker_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
