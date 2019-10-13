package server.logan_park.helper.model;

import server.logan_park.view.weekly_report_manual_uber.PaymentView;

import java.util.List;

public class PaymentDriverRecord {
    private List<PaymentView> recordList;
    private SummaryPaymentDriverRecord summary;
    private String driverName;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public List<PaymentView> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<PaymentView> recordList) {
        this.recordList = recordList;
    }

    public SummaryPaymentDriverRecord getSummary() {
        return summary;
    }

    public void setSummary(SummaryPaymentDriverRecord summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "PaymentDriverRecord{" +
                "recordList=" + recordList +
                ", summary=" + summary +
                '}';
    }
}
