package server.logan_park.view.weekly_report_general.model;

public class OwnerStat {
    private String driverName;
    private Integer amount=0;
    private Integer cash=0;
    private Integer tips=0;
    private Integer commission=0;
    private Integer withdraw=0;

    @Override
    public String toString() {
        return "OwnerStat{" +
                "driverName='" + driverName + '\'' +
                ", amount=" + amount +
                ", cash=" + cash +
                ", tips=" + tips +
                ", commission=" + commission +
                ", withdraw=" + withdraw +
                '}';
    }

    public Integer getTips() {
        return tips;
    }

    public void setTips(Integer tips) {
        this.tips = tips;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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

    public Integer getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Integer withdraw) {
        this.withdraw = withdraw;
    }
}
