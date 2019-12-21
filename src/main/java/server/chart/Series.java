package server.chart;

import client.rest.JSONModel;

import java.util.ArrayList;
import java.util.List;

public class Series extends JSONModel<Series> {
    private List<Data> data=new ArrayList<>();
    private String name;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
