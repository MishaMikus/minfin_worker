package client.rest.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

class MapQuery {
    private String urlEncodeUTF8(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    String mapToURLQuery(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {sb.append("&");}
            sb.append(String.format("%s=%s",entry.getKey().toString(),entry.getValue().toString()
            ));
        }
        return sb.toString();
    }

}