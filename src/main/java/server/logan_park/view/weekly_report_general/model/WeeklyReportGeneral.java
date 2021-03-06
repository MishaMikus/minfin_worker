package server.logan_park.view.weekly_report_general.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklyReportGeneral {
    private List<WeekLink> weekLinksList = new ArrayList<>();
    private List<DriverStatGeneral> driverStatList = new ArrayList<>();
    private List<DriverOwnerStat> driverOwnerStatList = new ArrayList<>();
    private Map<String, List<DriverOwnerStat>> driverOwnerStatMap = new HashMap<>();
    private CompanyAccountStat companyAccountStat=new CompanyAccountStat();

    @Override
    public String toString() {
        return "WeeklyReportGeneral{" +
                "driverStatList=" + driverStatList +
                ", driverOwnerStatList=" + driverOwnerStatList +
                ", companyAccountStat=" + companyAccountStat +
                '}';
    }

    public List<WeekLink> getWeekLinksList() {
        return weekLinksList;
    }

    public void setWeekLinksList(List<WeekLink> weekLinksList) {
        this.weekLinksList = weekLinksList;
    }

    public List<DriverStatGeneral> getDriverStatList() {
        return driverStatList;
    }

    public void setDriverStatList(List<DriverStatGeneral> driverStatList) {
        this.driverStatList = driverStatList;
    }

    public List<DriverOwnerStat> getDriverOwnerStatList() {
        return driverOwnerStatList;
    }

    public void setDriverOwnerStatList(List<DriverOwnerStat> driverOwnerStatList) {
        this.driverOwnerStatList = driverOwnerStatList;
    }

    public Map<String, List<DriverOwnerStat>> getDriverOwnerStatMap() {
        return driverOwnerStatMap;
    }

    public void setDriverOwnerStatMap(Map<String, List<DriverOwnerStat>> driverOwnerStatMap) {
        this.driverOwnerStatMap = driverOwnerStatMap;
    }

    public CompanyAccountStat getCompanyAccountStat() {
        return companyAccountStat;
    }

    public void setCompanyAccountStat(CompanyAccountStat companyAccountStat) {
        this.companyAccountStat = companyAccountStat;
    }

}
