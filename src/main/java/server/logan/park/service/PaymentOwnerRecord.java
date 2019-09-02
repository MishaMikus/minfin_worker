package server.logan.park.service;

import server.logan.park.view.OwnerPaymentView;


public class PaymentOwnerRecord {
    private OwnerPaymentView ownerPaymentViews;
    private String driverName;

    public OwnerPaymentView getOwnerPaymentViews() {
        return ownerPaymentViews;
    }

    public void setOwnerPaymentViews(OwnerPaymentView ownerPaymentViews) {
        this.ownerPaymentViews = ownerPaymentViews;
    }


    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
