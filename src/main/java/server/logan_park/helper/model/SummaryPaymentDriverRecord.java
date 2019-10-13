package server.logan_park.helper.model;

public class SummaryPaymentDriverRecord {
    private Integer rate;
    private Integer count;
    private Integer amount;
    private Integer cash;
    private Integer salary;
    private Integer change;
    private Integer tips;
    private Integer promotion;
    private Double duration;
    private Double uahPerHour;
    private Double uahPerTrip;
    private Integer salaryWithTips;
    private Integer changeWithoutTips;
    private String formula;

    @Override
    public String toString() {
        return "SummaryPaymentDriverRecord{" +
                "rate=" + rate +
                ", count=" + count +
                ", amount=" + amount +
                ", cash=" + cash +
                ", salary=" + salary +
                ", change=" + change +
                ", tips=" + tips +
                ", promotion=" + promotion +
                ", duration=" + duration +
                ", uahPerHour=" + uahPerHour +
                ", uahPerTrip=" + uahPerTrip +
                ", salaryWithTips=" + salaryWithTips +
                ", changeWithoutTips=" + changeWithoutTips +
                ", formula='" + formula + '\'' +
                '}';
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public Integer getTips() {
        return tips;
    }

    public void setTips(Integer tips) {
        this.tips = tips;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getUahPerHour() {
        return uahPerHour;
    }

    public void setUahPerHour(Double uahPerHour) {
        this.uahPerHour = uahPerHour;
    }

    public Double getUahPerTrip() {
        return uahPerTrip;
    }

    public void setUahPerTrip(Double uahPerTrip) {
        this.uahPerTrip = uahPerTrip;
    }

    public Integer getSalaryWithTips() {
        return salaryWithTips;
    }

    public void setSalaryWithTips(Integer salaryWithTips) {
        this.salaryWithTips = salaryWithTips;
    }

    public Integer getChangeWithoutTips() {
        return changeWithoutTips;
    }

    public void setChangeWithoutTips(Integer changeWithoutTips) {
        this.changeWithoutTips = changeWithoutTips;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
