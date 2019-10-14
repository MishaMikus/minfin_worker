package server.logan_park.view.weekly_report_general.model;

public class WeekLink {
    private String label;
    private String href;
    private Integer id;
    @Override
    public String toString() {
        return "WeekLinks{" +
                "label='" + label + '\'' +
                ", href='" + href + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
