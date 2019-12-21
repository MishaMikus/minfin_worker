package server.chart;

import client.rest.JSONModel;

public class Tooltip  extends JSONModel<Tooltip> {
    private String headerFormat;
    private String pointFormat;

    public String getHeaderFormat() {
        return headerFormat;
    }

    public void setHeaderFormat(String headerFormat) {
        this.headerFormat = headerFormat;
    }

    public String getPointFormat() {
        return pointFormat;
    }

    public void setPointFormat(String pointFormat) {
        this.pointFormat = pointFormat;
    }
}
