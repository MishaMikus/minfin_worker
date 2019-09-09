package orm.entity.uber.update_request;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "uber_update_request")
public class UberUpdateWeekReportRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private Date created;

    @Column
    private Boolean started;

    @Column
    private Date updated;


    public UberUpdateWeekReportRequest() {
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

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "UberUpdateWeekReportRequest{" +
                "id=" + id +
                ", created=" + created +
                ", started=" + started +
                ", updated=" + updated +
                '}';
    }
}
