package server.logan_park.view.weekly_report_general.model;

import java.util.Date;

public class DateRange {
    private Date start;
    private Date end;

    @Override
    public String toString() {
        return "DateRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
