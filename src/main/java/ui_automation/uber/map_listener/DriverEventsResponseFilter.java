package ui_automation.uber.map_listener;

import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

public class DriverEventsResponseFilter implements ResponseFilter {
    @Override
    public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
//        if(messageInfo.getUrl().endsWith("p3/fleet-manager/_rpc?rpc=getDriverEvents")){
//            System.out.println(contents.getTextContents());
//        }
    }
}
