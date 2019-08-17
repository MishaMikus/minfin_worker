package server.logan.park.view;

import java.util.Date;

public class TripView {
    private String id;
    private String text;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TripView{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
