package orm.entity.uber.sms_code;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "uber_sms_code")
public class UberSMSCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private Date created;

    @Column
    private Boolean used;

    @Column
    private String code;

    public UberSMSCode() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UberSMSCode{" +
                "id=" + id +
                ", created=" + created +
                ", used=" + used +
                ", code='" + code + '\'' +
                '}';
    }
}
