package server.logan_park.view.weekly_report_uber.model;

import server.logan_park.helper.model.GeneralPartnerSummary;
import server.logan_park.helper.model.PaymentDriverRecord;
import server.logan_park.helper.model.PaymentOwnerRecord;
import server.logan_park.view.weekly_report_general.model.WeekLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutomaticallyWeeklyUberReport {
    private List<WeekLink> weekLinksList = new ArrayList<>();
    private Map<String, PaymentDriverRecord> paymentTable;
    private Map<String, PaymentOwnerRecord> ownerTable;
    private GeneralPartnerSummary generalPartnerSummary;

    @Override
    public String toString() {
        return "AutomaticallyWeeklyUberReport{" +
                "paymentTable=" + paymentTable +
                ", ownerTable=" + ownerTable +
                ", generalPartnerSummary=" + generalPartnerSummary +
                '}';
    }

    public List<WeekLink> getWeekLinksList() {
        return weekLinksList;
    }

    public void setWeekLinksList(List<WeekLink> weekLinksList) {
        this.weekLinksList = weekLinksList;
    }

    public Map<String, PaymentDriverRecord> getPaymentTable() {
        return paymentTable;
    }

    public void setPaymentTable(Map<String, PaymentDriverRecord> paymentTable) {
        this.paymentTable = paymentTable;
    }

    public Map<String, PaymentOwnerRecord> getOwnerTable() {
        return ownerTable;
    }

    public void setOwnerTable(Map<String, PaymentOwnerRecord> ownerTable) {
        this.ownerTable = ownerTable;
    }

    public GeneralPartnerSummary getGeneralPartnerSummary() {
        return generalPartnerSummary;
    }

    public void setGeneralPartnerSummary(GeneralPartnerSummary generalPartnerSummary) {
        this.generalPartnerSummary = generalPartnerSummary;
    }
}
