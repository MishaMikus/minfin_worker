package server.chart;

import client.rest.JSONModel;

import java.util.ArrayList;
import java.util.List;

public class XAxis extends JSONModel<XAxis> {
    private String type;
    private List<String> categories = new ArrayList();
    private Title title=new Title();

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
