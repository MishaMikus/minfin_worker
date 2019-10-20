package server.logan_park.view.filling_report.model;

import orm.entity.logan_park.filling.FillingRecord;
import server.logan_park.view.weekly_report_general.model.WeekLink;

import java.util.*;

public class FillingTable {
    private List<WeekLink> weekLinksList = new ArrayList<>();
    private Map<DateLabel, List<FillingRecord>> fillingRecordMap = new TreeMap<>();
    private FillingInfo fillingInfo = new FillingInfo();

    public List<WeekLink> getWeekLinksList() {
        return weekLinksList;
    }

    public void setWeekLinksList(List<WeekLink> weekLinksList) {
        this.weekLinksList = weekLinksList;
    }

    public Map<DateLabel, List<FillingRecord>> getFillingRecordMap() {
        return fillingRecordMap;
    }

    public void setFillingRecordMap(Map<DateLabel, List<FillingRecord>> fillingRecordMap) {
        this.fillingRecordMap = fillingRecordMap;
    }

    public FillingInfo getFillingInfo() {
        return fillingInfo;
    }

    public void setFillingInfo(FillingInfo fillingInfo) {
        this.fillingInfo = fillingInfo;
    }

    @Override
    public String toString() {
        return "FillingTable{" +
                "weekLinksList=" + weekLinksList +
                ", fillingRecordMap=" + fillingRecordMap +
                ", fillingInfo=" + fillingInfo +
                '}';
    }
}
