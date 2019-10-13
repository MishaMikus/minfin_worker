package server.logan_park.view.weekly_report_general.model;

public class CompanyAccountStat {
    private Integer brandingUber;
    private Integer brandingBolt;
    private Integer tax;
    private Integer cash;
    private Integer clearDriverOwnerProfit;
    private Integer generalAmount;
    private Integer generalProfit;

    @Override
    public String toString() {
        return "CompanyAccountStat{" +
                "brandingUber=" + brandingUber +
                ", brandingBolt=" + brandingBolt +
                ", tax=" + tax +
                ", cash=" + cash +
                ", clearDriverOwnerProfit=" + clearDriverOwnerProfit +
                ", generalAmount=" + generalAmount +
                ", generalProfit=" + generalProfit +
                '}';
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getBrandingUber() {
        return brandingUber;
    }

    public void setBrandingUber(Integer brandingUber) {
        this.brandingUber = brandingUber;
    }

    public Integer getBrandingBolt() {
        return brandingBolt;
    }

    public void setBrandingBolt(Integer brandingBolt) {
        this.brandingBolt = brandingBolt;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getClearDriverOwnerProfit() {
        return clearDriverOwnerProfit;
    }

    public void setClearDriverOwnerProfit(Integer clearDriverOwnerProfit) {
        this.clearDriverOwnerProfit = clearDriverOwnerProfit;
    }

    public Integer getGeneralAmount() {
        return generalAmount;
    }

    public void setGeneralAmount(Integer generalAmount) {
        this.generalAmount = generalAmount;
    }

    public Integer getGeneralProfit() {
        return generalProfit;
    }

    public void setGeneralProfit(Integer generalProfit) {
        this.generalProfit = generalProfit;
    }
}
