package server.views.dashboard;

public class TransactionView {
    private String date;
    private String transaction;
    private String course;
    private String usd_change;
    private String uah_change;
    private String usd;
    private String uah;
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUsd_change() {
        return usd_change;
    }

    public void setUsd_change(String usd_change) {
        this.usd_change = usd_change;
    }

    public String getUah_change() {
        return uah_change;
    }

    public void setUah_change(String uah_change) {
        this.uah_change = uah_change;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getUah() {
        return uah;
    }

    public void setUah(String uah) {
        this.uah = uah;
    }
}
