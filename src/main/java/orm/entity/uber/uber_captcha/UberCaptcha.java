package orm.entity.uber.uber_captcha;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "uber_captcha")
public class UberCaptcha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private Date created;

    @Column
    private String fileId;

    @Column
    private String answer;

    @Column
    private String realPath;

    @Override
    public String toString() {
        return "UberCaptcha{" +
                "id=" + id +
                ", created=" + created +
                ", fileId='" + fileId + '\'' +
                ", answer='" + answer + '\'' +
                ", realPath='" + realPath + '\'' +
                '}';
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
