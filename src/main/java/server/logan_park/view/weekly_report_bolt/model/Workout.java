package server.logan_park.view.weekly_report_bolt.model;

public class Workout {
    private String name;
    private Integer amount;
    private Integer cash;
    private Integer tips;
    private Integer salary;
    private Integer change;

    @Override
    public String toString() {
        return "Workout{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", cash=" + cash +
                ", tips=" + tips +
                ", salary=" + salary +
                ", change=" + change +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
