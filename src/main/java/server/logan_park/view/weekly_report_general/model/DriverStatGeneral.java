package server.logan_park.view.weekly_report_general.model;

public class DriverStatGeneral {
    private WeekDriverStat uberStat=new WeekDriverStat();
    private WeekDriverStat boltStat=new WeekDriverStat();
    private WeekDriverStat sum=new WeekDriverStat();
    private String plan;
    private Integer rate;
    private String driverName;

    @Override
    public String toString() {
        return "DriverStatGeneral{" +
                "uberStat=" + uberStat +
                ", boltStat=" + boltStat +
                ", sum=" + sum +
                ", plan='" + plan + '\'' +
                ", rate='" + rate + '\'' +
                ", driverName='" + driverName + '\'' +
                '}';
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public WeekDriverStat getUberStat() {
        return uberStat;
    }

    public void setUberStat(WeekDriverStat uberStat) {
        this.uberStat = uberStat;
    }

    public WeekDriverStat getBoltStat() {
        return boltStat;
    }

    public void setBoltStat(WeekDriverStat boltStat) {
        this.boltStat = boltStat;
    }

    public WeekDriverStat getSum() {
        return sum;
    }

    public void setSum(WeekDriverStat sum) {
        this.sum = sum;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
