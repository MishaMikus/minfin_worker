package orm.entity.minfin.sell_price;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "sell_price")
public class SellPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private Double price;

    @Column
    private Date date;

    @Override
    public String toString() {
        return "SellPrice{" +
                "id=" + id +
                ", price=" + price +
                ", date=" + date +
                '}';
    }

    public SellPrice(Double price) {
        this.date = new Date();
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SellPrice() {
    }
}
