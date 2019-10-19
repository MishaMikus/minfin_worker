package server.logan_park.view.filling_report.model;

import java.util.Date;

public class KmRequest {
    private Integer km;
    private Long date;

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "KmRequest{" +
                "km=" + km +
                ", date=" + date +
                '}';
    }
}
