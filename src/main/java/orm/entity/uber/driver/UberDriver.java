package orm.entity.uber.driver;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "uber_driver")
public class UberDriver {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String driverUUID;
    @Column
    private String driverType;
    @Column
    private String name;

    public UberDriver() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDriverUUID() {
        return driverUUID;
    }

    public void setDriverUUID(String driverUUID) {
        this.driverUUID = driverUUID;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UberDriver{" +
                "id=" + id +
                ", driverUUID='" + driverUUID + '\'' +
                ", driverType='" + driverType + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}