package server.chart;

import client.rest.JSONModel;

public class Title extends JSONModel<Title> {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
