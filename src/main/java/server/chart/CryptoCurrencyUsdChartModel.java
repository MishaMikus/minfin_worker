package server.chart;

import client.rest.JSONModel;

import java.util.ArrayList;
import java.util.List;

public class CryptoCurrencyUsdChartModel extends JSONModel<CryptoCurrencyUsdChartModel> {
    private Chart chart = new Chart();
    private Title title = new Title();
    private XAxis xAxis = new XAxis();
    private YAxis yAxis = new YAxis();
    private List<Series> series = new ArrayList<>();
    private PlotOptions plotOptions=new PlotOptions();

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public XAxis getxAxis() {
        return xAxis;
    }

    public void setxAxis(XAxis xAxis) {
        this.xAxis = xAxis;
    }

    public YAxis getyAxis() {
        return yAxis;
    }

    public void setyAxis(YAxis yAxis) {
        this.yAxis = yAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public PlotOptions getPlotOptions() {
        return plotOptions;
    }

    public void setPlotOptions(PlotOptions plotOptions) {
        this.plotOptions = plotOptions;
    }

}
