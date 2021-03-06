package orm.entity.bolt.payment_record_day;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "bolt_payment_record_day")
@IdClass(BoltPaymentRecordDayPK.class)
public class BoltPaymentRecordDay {

    @Id
    @Column
    private String driverName;//"Водій", //"Богдан Трунко",

    @Id
    @Column
    private Date timestamp;// "Період", // "День 2020-07-24",

    @Column
    private Integer driver_id; // generated
    private Date creation; // generated

    private Double amount;// "Загальний тариф", // "1526.00",
    private Double reject_amount; // "Плата за скасування", // "0.00",
    private Double booking_payment_amount;// "Авторизаційцний платіж (платіж)", // "0.00",
    private Double booking_payment_minus; // "Авторизаційцний платіж (відрахування)", // "0.00",
    private Double additional_payment;// "Додатковий збір", // "0.00",
    private Double bolt_commission;// "Комісія Bolt", // "-305.20",
    private Double cash; // "Поїздки за готівку (зібрана готівка)", // "-636.00",
    private Double cash_turn; // "Сума знижки Bolt за готівкові поїздки ", // "65.00",
    private Double bonus;// "Водійський бонус", // "0.00",
    private Double compensation; // "Компенсації", // "",

    private Double return_amount; // "Повернення коштів", // "0.00",
    private Double tips; // "Чайові", // "0.00",

    private Double week_balance;// "Тижневий баланс" // "584.80"
    private Integer week_id;

    public Integer getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(Integer driver_id) {
        this.driver_id = driver_id;
    }

    public BoltPaymentRecordDay() {
    }

    public Double getReturn_amount() {
        return return_amount;
    }

    public void setReturn_amount(Double return_amount) {
        this.return_amount = return_amount;
    }

    public Double getTips() {
        return tips;
    }

    public void setTips(Double tips) {
        this.tips = tips;
    }

    public Integer getWeek_id() {
        return week_id;
    }

    public void setWeek_id(Integer week_id) {
        this.week_id = week_id;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getReject_amount() {
        return reject_amount;
    }

    public void setReject_amount(Double reject_amount) {
        this.reject_amount = reject_amount;
    }

    public Double getBooking_payment_amount() {
        return booking_payment_amount;
    }

    public void setBooking_payment_amount(Double booking_payment_amount) {
        this.booking_payment_amount = booking_payment_amount;
    }

    public Double getBooking_payment_minus() {
        return booking_payment_minus;
    }

    public void setBooking_payment_minus(Double booking_payment_minus) {
        this.booking_payment_minus = booking_payment_minus;
    }

    public Double getAdditional_payment() {
        return additional_payment;
    }

    public void setAdditional_payment(Double additional_payment) {
        this.additional_payment = additional_payment;
    }

    public Double getBolt_commission() {
        return bolt_commission;
    }

    public void setBolt_commission(Double bolt_commission) {
        this.bolt_commission = bolt_commission;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getCash_turn() {
        return cash_turn;
    }

    public void setCash_turn(Double cash_turn) {
        this.cash_turn = cash_turn;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Double getCompensation() {
        return compensation;
    }

    public void setCompensation(Double compensation) {
        this.compensation = compensation;
    }

    public Double getWeek_balance() {
        return week_balance;
    }

    public void setWeek_balance(Double week_balance) {
        this.week_balance = week_balance;
    }

    @Override
    public String toString() {
        return "BoltPaymentRecordDay{" +
                "driverName='" + driverName + '\'' +
                ", timestamp=" + timestamp +
                ", driver_id=" + driver_id +
                ", creation=" + creation +
                ", amount=" + amount +
                ", reject_amount=" + reject_amount +
                ", booking_payment_amount=" + booking_payment_amount +
                ", booking_payment_minus=" + booking_payment_minus +
                ", additional_payment=" + additional_payment +
                ", bolt_commission=" + bolt_commission +
                ", cash=" + cash +
                ", cash_turn=" + cash_turn +
                ", bonus=" + bonus +
                ", compensation=" + compensation +
                ", return_amount=" + return_amount +
                ", tips=" + tips +
                ", week_balance=" + week_balance +
                ", week_id=" + week_id +
                '}';
    }
}
