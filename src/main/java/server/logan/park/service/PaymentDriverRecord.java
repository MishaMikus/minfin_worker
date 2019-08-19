package server.logan.park.service;

import server.logan.park.view.PaymentView;

import java.util.List;

public class PaymentDriverRecord {
    private List<PaymentView> recordList;
    private SummaryPaymentDriverRecord summary;

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
