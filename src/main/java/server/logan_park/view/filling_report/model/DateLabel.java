package server.logan_park.view.filling_report.model;

import java.util.Date;
import java.util.Objects;

import static ui_automation.bolt.RecordHelper.SDF;

public class DateLabel implements Comparable {
    private Date date;
    private String label;

    public DateLabel(Date dayDate) {
        date = dayDate;
        label = SDF.format(dayDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateLabel dateLabel = (DateLabel) o;
        return Objects.equals(date, dateLabel.date) &&
                Objects.equals(label, dateLabel.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, label);
    }

    @Override
    public int compareTo(Object o) {
        return ((DateLabel) o).getDate().compareTo(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
