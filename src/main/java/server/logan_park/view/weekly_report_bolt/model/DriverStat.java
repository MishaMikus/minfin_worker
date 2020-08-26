package server.logan_park.view.weekly_report_bolt.model;

import java.util.ArrayList;
import java.util.List;

public class DriverStat {
    private String plan;
    private String driverName;
    private Integer rate;

    private List<Workout> workoutList = new ArrayList<>();

    private Integer workoutCount;
    private Integer amount;
    private Integer cash;
    private Integer salary;
    private Integer tips;
    private Integer change;

    public Integer getTips() {
        return tips;
    }

    public void setTips(Integer tips) {
        this.tips = tips;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public Integer getWorkoutCount() {
        return workoutCount;
    }

    public void setWorkoutCount(Integer workoutCount) {
        this.workoutCount = workoutCount;
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

    @Override
    public String toString() {
        return "DriverStat{" +
                "plan='" + plan + '\'' +
                ", driverName='" + driverName + '\'' +
                ", rate='" + rate + '\'' +
                ", workoutList=" + workoutList +
                ", workoutCount='" + workoutCount + '\'' +
                ", amount='" + amount + '\'' +
                ", cash='" + cash + '\'' +
                ", tips='" + cash + '\'' +
                ", salary='" + salary + '\'' +
                ", change='" + change + '\'' +
                '}';
    }
}
