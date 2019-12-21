package server.chart;

import client.rest.JSONModel;

public class PlotOptions extends JSONModel<PlotOptions> {
    private PlotSeries series=new PlotSeries();
    private Area area=new Area();

    public PlotSeries getSeries() {
        return series;
    }

    public void setSeries(PlotSeries series) {
        this.series = series;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}
