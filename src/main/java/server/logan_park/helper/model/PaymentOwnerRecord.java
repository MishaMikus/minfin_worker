package server.logan_park.helper.model;

import server.logan_park.view.week_report_manual_downloader.OwnerPaymentView;


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

    @Override
    public String toString() {
        return "PaymentOwnerRecord{" +
                "ownerPaymentViews=" + ownerPaymentViews +
                ", driverName='" + driverName + '\'' +
                '}';
    }
}
