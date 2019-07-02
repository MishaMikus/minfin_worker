package orm.entity.transaction;

import orm.entity.currency.Currency;

import javax.persistence.*;

@Entity
@Table(schema = "minfin", name = "transaction_type")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name="from", nullable=false)
    private Currency from;
    @ManyToOne
    @JoinColumn(name="to", nullable=false)
    private Currency to;

    @Column
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", name='" + name + '\'' +
                '}';
    }
}
