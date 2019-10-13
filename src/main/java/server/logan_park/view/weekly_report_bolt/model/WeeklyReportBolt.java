package server.logan_park.view.weekly_report_bolt.model;

import java.util.ArrayList;
import java.util.List;

public class WeeklyReportBolt {
    private List<DriverStat> driverStatList=new ArrayList<>();
    private Integer generalAmount;
    private Integer generalProfit;

    public List<DriverStat> getDriverStatList() {
        return driverStatList;
    }

    public void setDriverStatList(List<DriverStat> driverStatList) {
        this.driverStatList = driverStatList;
    }

    @Override
    public String toString() {
        return "WeeklyReportBolt{" +
                "driverStatList=" + driverStatList +
                ", generalAmount='" + generalAmount + '\'' +
                ", generalProfit='" + generalProfit + '\'' +
                '}';
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
