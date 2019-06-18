package parser;

import java.util.Date;

public class DayRate {
    private Date date;
    private double buy;
    private double sell;

    public DayRate(Date date, double buy, double sell) {
        this.date = date;
        this.buy = buy;
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "DayRate{" +
                "date=" + date +
                ", buy=" + buy +
                ", sell=" + sell +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public double av() {
        return (sell+buy)/2;
    }
}
