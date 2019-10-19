package orm.entity.logan_park.filling;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.StrictMath.round;

@Entity
@Table(schema = "minfin", name = "uber_filling")
public class FillingRecord {
    @Id
    @Column
    //Дата:	2019-10-02 08:37:29
    private Date date;
    @Column
    //Номер картки:	7825390000344935
    private String card;
    @Column
    //Номер картки:	7825390000344935
    private String station;
    @Column
    //Сума:	262.33
    private Double amount;
    @Column
    //Сума знижки:	17.95
    private Double discount;
    @Column
    //Сума зi знижкою:	280.28
    private Double amountAndDiscount;
    @Column
    //Код контракту SAP:	24ПК-8276/19
    private String sapCode;
    @Column
    //Назва АЗС:	АЗС 011 Львів ОККО-Рітейл
    private String shop;
    @Column
    //Адрес АЗС:	Львівська, Львів, Дж.Вашингтона, 12
    private String address;
    @Column
    private Double itemAmount;
    @Column
    private Double price;
    @Column
    private String car;

    @Column
    private Integer week_id;

    @Column
    private String id;
    @Column
    private Integer km;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FillingRecord{" +
                "date=" + date +
                ", card='" + card + '\'' +
                ", station='" + station + '\'' +
                ", amount=" + amount +
                ", discount=" + discount +
                ", amountAndDiscount=" + amountAndDiscount +
                ", sapCode='" + sapCode + '\'' +
                ", shop='" + shop + '\'' +
                ", address='" + address + '\'' +
                ", itemAmount=" + itemAmount +
                ", price=" + price +
                ", car='" + car + '\'' +
                ", week_id=" + week_id +
                ", id='" + id + '\'' +
                '}';
    }



    public Integer getWeek_id() {
        return week_id;
    }

    public void setWeek_id(Integer week_id) {
        this.week_id = week_id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public FillingRecord() {
    }

    public FillingRecord(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAmountAndDiscount() {
        return amountAndDiscount;
    }

    public void setAmountAndDiscount(Double amountAndDiscount) {
        this.amountAndDiscount = amountAndDiscount;
    }

    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getAddress() {
        return address;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getRealPrice() {
        return round(amount / itemAmount * 100) / 100d + "";
    }

    private static final SimpleDateFormat TIME_SDF = new SimpleDateFormat("HH:mm:ss");

    public String getTime() {
        return TIME_SDF.format(date);
    }


}
