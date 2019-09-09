package orm.entity.uber.description;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "uber_description")
public class UberDescription {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String name;

    public UberDescription() {
    }

    public UberDescription(String name) {
        this.name=name;
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
        return "UberDescription{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}