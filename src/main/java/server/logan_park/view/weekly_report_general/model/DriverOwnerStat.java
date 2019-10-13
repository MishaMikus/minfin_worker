package server.logan_park.view.weekly_report_general.model;

public class DriverOwnerStat {
    private String name;
    private Integer amount;
    private Integer cash;
    private Integer commission;
    private Integer withdraw;

    @Override
    public String toString() {
        return "DriverOwnerStat{" +
                "name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", cash='" + cash + '\'' +
                ", commission='" + commission + '\'' +
                ", withdraw='" + withdraw + '\'' +
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

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public Integer getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Integer withdraw) {
        this.withdraw = withdraw;
    }
}
