package server.chart;

import client.rest.JSONModel;

public class Marker extends JSONModel<Marker> {
    private Integer radius;

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }
}
