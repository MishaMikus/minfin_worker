package server.chart;

import client.rest.JSONModel;

public class YAxis extends JSONModel<YAxis> {
    private Title title = new Title();
    private Tooltip tooltip = new Tooltip();

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
    }
}
