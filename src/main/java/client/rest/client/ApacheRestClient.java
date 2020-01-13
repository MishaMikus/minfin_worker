package client.rest.client;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import client.rest.model.RequestModel;
import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApacheRestClient implements RestClient {
    private final static Logger LOGGER = Logger.getLogger(ApacheRestClient.class);
    public void clearCookie(){
        cookies.clear();
    }
    @Override
    public ResponseModel call(RequestModel requestModel) {
        ResponseModel responseModel = executeRequest(requestModel);
        LOGGER.info(requestModel.getMethod() + " " + requestModel.getURL() + " " + responseModel.getStatusCode() + " " + responseModel.getResponseTime() + " ms");
        return responseModel;
    }

    private ResponseModel executeRequest(RequestModel requestModel) {

        HttpEntityEnclosingRequestBase request = null;
        HttpClientBuilder httpClient = null;
        try {
            //METHOD
            request = new HttpEntityEnclosingRequestBase() {
                @Override
                public String getMethod() {
                    if (requestModel != null) {
                        return requestModel.getMethod().toUpperCase();
                    } else return null;
                }
            };

            //URL
            URI uri = new URI(requestModel.getURLWithQuery());
            request.setURI(uri);

            //HOST

            //HEADERS, CONTENT_TYPE
            if (requestModel.getHeaders().size() > 0) {
                request.setHeaders(makeHeaders(requestModel));
            }

            //BODY
            if (requestModel.getBody() != null) {
                String charset = null == requestModel.getCharset() ? "UTF-8" : requestModel.getCharset();
                request.setEntity(new StringEntity(requestModel.getBody().toString(), charset));
            }


            //MULTIPART
            if (requestModel.getMultipartFile() != null) {
                request.setEntity(MultipartEntityBuilder.create()
                        .addPart("upload-file", new FileBody(requestModel.getMultipartFile()))
                        .build());
            }

            //PARAMS
            if (requestModel.getParams() != null) {
                request.setParams(makeParams(requestModel));
            }

            //COOKIES
            httpClient = HttpClientBuilder.create();
            if (!requestModel.getUseCookie()) {
                httpClient.disableCookieManagement();
            } else {
                BasicCookieStore cookieStore = new BasicCookieStore();
                cookies.forEach((key, value) -> cookieStore.addCookie(value));
                httpClient.setDefaultCookieStore(cookieStore);
            }


            //AUTH
            if (requestModel.getBaseUserName() != null && requestModel.getBaseUserPassword() != null) {
                baseAuth(request, requestModel);
            }

            //REQUEST_LOG
            if (requestModel.getRequestLog()) {
                System.out.println(requestModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //FOLLOW_REDIRECTS
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy() {
            @Override
            public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
                Boolean res = super.isRedirected(request, response, context);
                if (requestModel.getFollowRedirects() != null
                        && !requestModel.getFollowRedirects()
                        && !res) {
                    //LOGGER.info("Default Client would like to redirect? but custom followRedirect = false");
                    return false;
                }
                return res;
            }
        };
        if (httpClient != null) {
            httpClient.setRedirectStrategy(redirectStrategy);
        }

        //===========SEND======>>>====GET=RESPONSE===========
        HttpResponse response = null;
        ResponseModel responseModel = null;
        HttpClientContext context = new HttpClientContext();
        Date start = new Date();
        try {
            if (httpClient != null) {
                response = httpClient.build().execute(request, context);
            }
            responseModel = new ResponseModel();


            //RESPONSE STATUS LINE
            if (response != null) {
                responseModel.setStatusLine(response.getStatusLine().toString());
            }

            responseModel.setBody(getBody(response));

            //RESPONSE CODE
            if (response != null) {
                responseModel.setStatusCode(response.getStatusLine().getStatusCode());
            }

            //RESPONSE TIME
            responseModel.setStart(start.getTime());
            responseModel.setResponseTime(new Date().getTime() - start.getTime());

            //RESPONSE COOKIES

            responseModel.setCookiesMap(parseCookies(context.getCookieStore()));
            if (requestModel.getUseCookie()) {
                cookies.putAll(responseModel.getCookiesMap());
            }

            //RESPONSE HEADERS
            responseModel.setHeaderMap(headerMap(response));


            //RESPONSE LOG
            if (requestModel.getResponseLog()) {
                System.out.println(responseModel);
            }

            //RESPONSE LOG IF ERROR
            if (requestModel.getResponseIfErrorLog() && responseModel.getStatusCode() >= 400) {
                System.err.println(responseModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //RETURN
        return responseModel;
    }

    private Map<String, String> headerMap(HttpResponse response) {
        Map<String, String> headerMap = new HashMap<>();
        if (response.getAllHeaders() != null) {
            for (Header header : response.getAllHeaders()) {
                headerMap.put(header.getName(), header.getValue());
            }
        }
        return headerMap;
    }

    private Map<String, Cookie> parseCookies(CookieStore cookieStore) {
        Map<String, Cookie> cookiesMap = new HashMap<>();
        for (Cookie cookie : cookieStore.getCookies()) {
            cookiesMap.put(cookie.getName(), cookie);
        }
        return cookiesMap;
    }

    private String getBody(HttpResponse response) {
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder result = new StringBuilder();
        String line;
        try {
            if (rd != null) {
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private void baseAuth(HttpEntityEnclosingRequestBase request, RequestModel requestModel) {
        request.addHeader(new BasicHeader("Authorization", "Basic " + base64(requestModel.getBaseUserName() + ":" + requestModel.getBaseUserPassword())));
    }

    private String base64(String str) {
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    private CookieStore makeCookieStore() {
        CookieStore cookieStore = new BasicCookieStore();
        for (Map.Entry cookie : cookies.entrySet()) {
            cookieStore.addCookie(new BasicClientCookie(cookie.getKey().toString(), cookie.getValue().toString()));
        }

        return cookieStore;
    }

    private HttpParams makeParams(RequestModel requestModel) {
        HttpParams httpParams = new BasicHttpParams();
        for (Map.Entry param : requestModel.getParams().entrySet()) {
            httpParams.setParameter((String) param.getKey(), param.getValue());
        }
        return httpParams;
    }

    private Header[] makeHeaders(RequestModel requestModel) {
        //put contentType to Header
        if (requestModel.getContentType() != null) {
            requestModel.getHeaders().put("Content-Type", requestModel.getContentType());
        }
        final int headerSize = requestModel.getHeaders().size();
        Header[] headers = new Header[headerSize];
        int i = 0;
        for (Map.Entry headerEntry : requestModel.getHeaders().entrySet()) {
            headers[i++] = new BasicHeader(headerEntry.getKey().toString(), headerEntry.getValue().toString());
        }
        return headers;
    }

}
