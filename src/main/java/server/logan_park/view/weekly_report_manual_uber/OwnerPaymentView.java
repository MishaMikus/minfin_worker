package server.logan_park.view.weekly_report_manual_uber;

import java.util.List;

public class OwnerPaymentView {
    private Double taxPercentage;
    private Integer amountMinusCommission;
    private Integer commission;
    private Integer nonCash;
    private Integer withdraw;

    private Integer count;
    private Integer amount;
    private Integer cash;

    private Integer tips;
    private Integer promotion;

    private String dateRangeName;
    private String tripListId;
    private List<TripView> tripList;

    public OwnerPaymentView(Integer count, Integer amount, Integer cash) {
        this.count = count;
        this.amount = amount;
        this.cash = cash;
    }

    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Integer getAmountMinusCommission() {
        return amountMinusCommission;
    }

    public void setAmountMinusCommission(Integer amountMinusCommission) {
        this.amountMinusCommission = amountMinusCommission;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public Integer getNonCash() {
        return nonCash;
    }

    public void setNonCash(Integer nonCash) {
        this.nonCash = nonCash;
    }

    public Integer getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Integer withdraw) {
        this.withdraw = withdraw;
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

    public String getDateRangeName() {
        return dateRangeName;
    }

    public void setDateRangeName(String dateRangeName) {
        this.dateRangeName = dateRangeName;
    }

    public String getTripListId() {
        return tripListId;
    }

    public void setTripListId(String tripListId) {
        this.tripListId = tripListId;
    }

    public List<TripView> getTripList() {
        return tripList;
    }

    public void setTripList(List<TripView> tripList) {
        this.tripList = tripList;
    }

    @Override
    public String toString() {
        return "OwnerPaymentView{" +
                "taxPercentage='" + taxPercentage + '\'' +
                ", amountMinusCommission='" + amountMinusCommission + '\'' +
                ", commission='" + commission + '\'' +
                ", nonCash='" + nonCash + '\'' +
                ", withdraw='" + withdraw + '\'' +
                ", count='" + count + '\'' +
                ", amount='" + amount + '\'' +
                ", cash='" + cash + '\'' +
                ", tips='" + tips + '\'' +
                ", promotion='" + promotion + '\'' +
                ", dateRangeName='" + dateRangeName + '\'' +
                ", tripListId='" + tripListId + '\'' +
                ", tripList=" + tripList +
                '}';
    }
}
