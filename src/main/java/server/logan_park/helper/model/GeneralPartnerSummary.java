package server.logan_park.helper.model;

public class GeneralPartnerSummary {

    private String profit;
    private String transfer;
    private String cash;
    private String tax;
    private String withdraw;
    private String realProfit;

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getRealProfit() {
        return realProfit;
    }

    public void setRealProfit(String realProfit) {
        this.realProfit = realProfit;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "GeneralPartnerSummary{" +
                "profit='" + profit + '\'' +
                ", transfer='" + transfer + '\'' +
                ", cash='" + cash + '\'' +
                ", tax='" + tax + '\'' +
                ", withdraw='" + withdraw + '\'' +
                ", realProfit='" + realProfit + '\'' +
                '}';
    }
}
