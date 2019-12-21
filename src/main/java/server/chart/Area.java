package server.chart;

import client.rest.JSONModel;

public class Area extends JSONModel<Area> {
    private FillColor fillColor = new FillColor();
    private Marker marker = new Marker();
    private Integer lineWidth;
    private States states = new States();

    public FillColor getFillColor() {
        return fillColor;
    }

    public void setFillColor(FillColor fillColor) {
        this.fillColor = fillColor;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Integer getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

}
