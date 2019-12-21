package server.chart;
import client.rest.JSONModel;

public class Chart extends JSONModel<Chart> {
    private String type;
    private Boolean animation;
    private String zoomType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAnimation() {
        return animation;
    }

    public void setAnimation(Boolean animation) {
        this.animation = animation;
    }

    public String getZoomType() {
        return zoomType;
    }

    public void setZoomType(String zoomType) {
        this.zoomType = zoomType;
    }
}
