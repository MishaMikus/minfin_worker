package server.chart;

import client.rest.JSONModel;

public class Hover extends JSONModel<Hover> {
    private Integer lineWidth;

    public Integer getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }
}
