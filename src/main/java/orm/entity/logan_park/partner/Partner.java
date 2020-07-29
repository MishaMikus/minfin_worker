package orm.entity.logan_park.partner;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "partner")
public class Partner {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idpartner;

    @Column
    private String name;

    public Long getIdpartner() {
        return idpartner;
    }

    public void setIdpartner(Long idpartner) {
        this.idpartner = idpartner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "idpartner=" + idpartner +
                ", name='" + name + '\'' +
                '}';
    }
}
