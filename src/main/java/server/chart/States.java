package server.chart;

import client.rest.JSONModel;

public class States extends JSONModel<States> {
    private Hover hover=new Hover();

    public Hover getHover() {
        return hover;
    }

    public void setHover(Hover hover) {
        this.hover = hover;
    }
}
