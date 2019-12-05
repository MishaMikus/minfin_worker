package orm.entity.logan_park.map_pinger.taxi_brand;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "taxi_brand")
public class TaxiBrand {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer base_id;
    @Column
    private String name;

    public Integer getBase_id() {
        return base_id;
    }

    public void setBase_id(Integer base_id) {
        this.base_id = base_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TaxiBrand{" +
                "base_id=" + base_id +
                ", name='" + name + '\'' +
                '}';
    }
}
