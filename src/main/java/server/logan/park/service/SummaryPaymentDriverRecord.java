package server.logan.park.service;

public class SummaryPaymentDriverRecord {
    private String rate;
    private String count;
    private String amount;
    private String cash;
    private String salary;
    private String change;
    private String tips;
    private String promotion;
    private String duration;
    private String uahPerHour;
    private String uahPerTrip;
    private String salaryWithTips;

    @Override
    public String toString() {
        return "SummaryPaymentDriverRecord{" +
                "count='" + count + '\'' +
                ", amount='" + amount + '\'' +
                ", cash='" + cash + '\'' +
                ", salary='" + salary + '\'' +
                ", change='" + change + '\'' +
                ", tips='" + tips + '\'' +
                ", promotion='" + promotion + '\'' +
                ", duration='" + duration + '\'' +
                ", uahPerHour='" + uahPerHour + '\'' +
                ", uahPerTrip='" + uahPerTrip + '\'' +
                ", salaryWithTips='" + salaryWithTips + '\'' +
                '}';
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUahPerHour() {
        return uahPerHour;
    }

    public void setUahPerHour(String uahPerHour) {
        this.uahPerHour = uahPerHour;
    }

    public String getUahPerTrip() {
        return uahPerTrip;
    }

    public void setUahPerTrip(String uahPerTrip) {
        this.uahPerTrip = uahPerTrip;
    }

    public String getSalaryWithTips() {
        return salaryWithTips;
    }

    public void setSalaryWithTips(String salaryWithTips) {
        this.salaryWithTips = salaryWithTips;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
