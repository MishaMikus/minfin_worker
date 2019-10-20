package server.logan_park.view.filling_report.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateLabel implements Comparable {
    public static final SimpleDateFormat SDF_DD_MM_YYYY = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat SDF_DD_MM_YYYY_HH_MM_SS = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public static final SimpleDateFormat HH_MM_SS = new SimpleDateFormat(" HH:mm:ss");
    private Date date;
    private SimpleDateFormat sdf = SDF_DD_MM_YYYY;

    public DateLabel(Date dayDate) {
        date = dayDate;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public DateLabel(Date date, SimpleDateFormat sdf) {
        this.date = date;
        this.sdf = sdf;
    }

    public DateLabel() {
    }

    public DateLabel(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateLabel dateLabel = (DateLabel) o;
        return Objects.equals(date, dateLabel.date) &&
                Objects.equals(getLabel(), dateLabel.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, getLabel());
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
        return sdf.format(date);
    }
}
