package server.chart;

import client.rest.JSONModel;

public class FillColor extends JSONModel<FillColor> {
    private LinearGradient linearGradient=new LinearGradient();

    public LinearGradient getLinearGradient() {
        return linearGradient;
    }

    public void setLinearGradient(LinearGradient linearGradient) {
        this.linearGradient = linearGradient;
    }
}
