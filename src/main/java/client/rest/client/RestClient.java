package client.rest.client;

import client.rest.model.RequestModel;
import client.rest.model.ResponseModel;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.HashMap;
import java.util.Map;

public interface RestClient {
    public static final String HTTPS = "https://";
    public static final String HTTP = "http://";

    public static final String GET = "GET";
    public static final String POST = "POST";
    Map<String, org.apache.http.cookie.Cookie> cookies = new HashMap<>();

    /**
     * =========== Request Builder :
     * CONTENT_TYPE
     * HEADERS
     * BODY
     * MULTIPART
     * PARAMS
     * COOKIES
     * AUTH
     * REQUEST_LOG
     * FOLLOW_REDIRECTS
     * ===========SEND======>>>====GET=RESPONSE===========
     * RESPONSE BY METHOD BY PATH_PRECISE_SUBMIT
     * TIME
     * RESPONSE
     * RESPONSE_LOG
     * RESPONSE_LOG_IF_ERROR
     * RETURN
     */
    ResponseModel call(RequestModel responseModel);

    void clearCookie();

}
