package server.chart;

import client.rest.JSONModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Data  extends JSONModel<Data> {

    public Data() {
    }

    @JsonIgnore
    public Data(Integer x, Double y) {
        this.x = x;
        this.y = y;
    }

    private Integer x;
    private Double y;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
