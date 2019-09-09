package orm.entity.uber.item_type;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "uber_item_type")
public class UberItemType {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String name;

    public UberItemType() {
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

    @Override
    public String toString() {
        return "UberItemType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}