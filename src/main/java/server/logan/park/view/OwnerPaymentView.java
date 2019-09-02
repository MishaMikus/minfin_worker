package server.logan.park.view;

import java.util.List;

public class OwnerPaymentView {
    private String taxPercentage;
    private String amountMinusCommission;
    private String commission;
    private String nonCash;
    private String withdraw;

    private String count;
    private String amount;
    private String cash;

    private String tips;
    private String promotion;

    private String dateRangeName;
    private String tripListId;
    private List<TripView> tripList;

    public OwnerPaymentView(String count, String amount, String cash) {
        this.count = count;
        this.amount = amount;
        this.cash = cash;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getAmountMinusCommission() {
        return amountMinusCommission;
    }

    public void setAmountMinusCommission(String amountMinusCommission) {
        this.amountMinusCommission = amountMinusCommission;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getNonCash() {
        return nonCash;
    }

    public void setNonCash(String nonCash) {
        this.nonCash = nonCash;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getDateRangeName() {
        return dateRangeName;
    }

    public void setDateRangeName(String dateRangeName) {
        this.dateRangeName = dateRangeName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }


    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
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
}
