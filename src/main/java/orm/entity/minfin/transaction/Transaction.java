package orm.entity.minfin.transaction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column
    private Date date;
    @Column
    private double currency_rate;

    @ManyToOne
    @JoinColumn(name="type", nullable=false)
    private TransactionType type;
    @Column
    private Integer change_usd;
    @Column
    private Integer change_uah;
    @Column
    private Integer usd_before;
    @Column
    private Integer usd_after;
    @Column
    private Integer uah_before;
    @Column
    private Integer uah_after;


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", currency_rate=" + currency_rate +
                ", type=" + type +
                ", change_usd=" + change_usd +
                ", change_uah=" + change_uah +
                ", usd_before=" + usd_before +
                ", usd_after=" + usd_after +
                ", uah_before=" + uah_before +
                ", uah_after=" + uah_after +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCurrency_rate() {
        return currency_rate;
    }

    public void setCurrency_rate(double currency_rate) {
        this.currency_rate = currency_rate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Integer getChange_usd() {
        return change_usd;
    }

    public void setChange_usd(Integer change_usd) {
        this.change_usd = change_usd;
    }

    public Integer getChange_uah() {
        return change_uah;
    }

    public void setChange_uah(Integer change_uah) {
        this.change_uah = change_uah;
    }

    public Integer getUsd_before() {
        return usd_before;
    }

    public void setUsd_before(Integer usd_before) {
        this.usd_before = usd_before;
    }

    public Integer getUsd_after() {
        return usd_after;
    }

    public void setUsd_after(Integer usd_after) {
        this.usd_after = usd_after;
    }

    public Integer getUah_before() {
        return uah_before;
    }

    public void setUah_before(Integer uah_before) {
        this.uah_before = uah_before;
    }

    public Integer getUah_after() {
        return uah_after;
    }

    public void setUah_after(Integer uah_after) {
        this.uah_after = uah_after;
    }
}