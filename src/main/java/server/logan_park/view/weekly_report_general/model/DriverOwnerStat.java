package server.logan_park.view.weekly_report_general.model;

public class DriverOwnerStat {
    private String name;
    private OwnerStat bolt_stat=new OwnerStat();
    private OwnerStat uber_stat=new OwnerStat();
    private OwnerStat general_stat=new OwnerStat();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OwnerStat getBolt_stat() {
        return bolt_stat;
    }

    public void setBolt_stat(OwnerStat bolt_stat) {
        this.bolt_stat = bolt_stat;
    }

    public OwnerStat getUber_stat() {
        return uber_stat;
    }

    public void setUber_stat(OwnerStat uber_stat) {
        this.uber_stat = uber_stat;
    }

    public OwnerStat getGeneral_stat() {
        return general_stat;
    }

    public void setGeneral_stat(OwnerStat general_stat) {
        this.general_stat = general_stat;
    }
}
