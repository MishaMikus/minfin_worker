package orm.entity.uber.uber_captcha;

import javax.persistence.*;
import java.util.Base64;
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
    private Integer size;

    @Column
    private String fileId;

    @Column
    private String answer;

    @Column
    private byte[] image;

    @Override
    public String toString() {
        return "UberCaptcha{" +
                "id=" + id +
                ", created=" + created +
                ", size=" + size +
                ", fileId='" + fileId + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public String captchaAsBase64() {
        return new String(Base64.getEncoder().encode(image));
    }
}
