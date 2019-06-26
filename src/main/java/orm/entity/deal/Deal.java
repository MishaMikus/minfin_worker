package orm.entity.deal;

import orm.entity.currency.Currency;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "deal")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column
    private String url;//buy or sell depends on URL
    @Column
    private String minfin_id;//<div class="au-deal js-au-deal dfMfs0" data-bid="102980775" tabindex="0">
    @Column
    private String time;//<span class="au-deal-time">11:10</span>
    @Column
    private Date date;//current page parsing date
    @Column
    private String currencyRate; //<span class="au-deal-currency">26,80</span>
    @Column
    private String sum;//<span class="au-deal-sum">10 000<label title="доллары США">$</label></span>
    @ManyToOne
    @JoinColumn(name = "currency", nullable = false)
    private Currency currency;//<label title="доллары США">$</label>
    @Column
    private String phone;//<div class="au-dealer-phone"><span class="c-desc">098</span> <a class="js-showPhone au-dealer-phone-xxx" data-mf="3" data-status="3"    data-timer="1559635812" data-bid-id="102980775"><span class="c-desc">xxx-x</span></a><span class="c-desc">4-15</span></div>
    @Column
    private String msg;//<div class="au-msg-wrapper js-au-msg-wrapper">Сихів_Довженка 2 ,целиком, Можна частинами, Можна більше</div>


    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", minfin_id='" + minfin_id + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", currencyRate='" + currencyRate + '\'' +
                ", sum='" + sum + '\'' +
                ", currency='" + currency + '\'' +
                ", phone='" + phone + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMinfin_id() {
        return minfin_id;
    }

    public void setMinfin_id(String minfin_id) {
        this.minfin_id = minfin_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(String currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}