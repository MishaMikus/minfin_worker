package server.logan_park.view.week_report_manual_downloader;

import java.util.List;

public class PaymentView {
    private String name;
    private String count;
    private String amount;
    private String cash;
    private String salary;
    private String change;
    private String start;
    private String end;
    private String tips;
    private String promotion;
    private String duration;
    private String tripListId;
    private List<TripView> tripList;

    public PaymentView(String name, String count, String amount, String cash, String salary, String change) {
        this.name = name;
        this.count = count;
        this.amount = amount;
        this.cash = cash;
        this.salary = salary;
        this.change = change;
    }

    public String getTripListId() {
        return tripListId;
    }

    public void setTripListId(String tripListId) {
        this.tripListId = tripListId;
    }

    public List<TripView> getTripList() {
        return tripList;
    }

    public void setTripList(List<TripView> tripList) {
        this.tripList = tripList;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "PaymentView{" +
                "name='" + name + '\'' +
                ", count='" + count + '\'' +
                ", amount='" + amount + '\'' +
                ", cash='" + cash + '\'' +
                ", salary='" + salary + '\'' +
                ", change='" + change + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", tips='" + tips + '\'' +
                ", promotion='" + promotion + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public void setTripListId() {
    }
}
