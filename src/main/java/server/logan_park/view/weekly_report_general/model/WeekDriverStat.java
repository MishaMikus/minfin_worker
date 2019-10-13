package server.logan_park.view.weekly_report_general.model;

public class WeekDriverStat {
    private Integer amount=0;
    private Integer cash=0;
    private Integer salary=0;
    private Integer change=0;
    private Integer tips=0;

    @Override
    public String toString() {
        return "WeekDriverStat{" +
                "amount='" + amount + '\'' +
                ", cash='" + cash + '\'' +
                ", salary='" + salary + '\'' +
                ", change='" + change + '\'' +
                ", tips='" + tips + '\'' +
                '}';
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
}
