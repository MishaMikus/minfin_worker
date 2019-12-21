package server.chart;

import client.rest.JSONModel;

public class PlotSeries extends JSONModel<PlotSeries> {
    private Integer turboThreshold = 0;

    public Integer getTurboThreshold() {
        return turboThreshold;
    }

    public void setTurboThreshold(Integer turboThreshold) {
        this.turboThreshold = turboThreshold;
    }
}
