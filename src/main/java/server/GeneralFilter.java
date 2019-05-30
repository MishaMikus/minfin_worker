package server;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})
public class GeneralFilter extends HttpFilter {
    protected final Logger LOGGER = Logger.getLogger(this.getClass());

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        String queryString = req.getQueryString();
        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(req);
        MultiReadHttpServletResponse multiReadHttpServletResponse = new MultiReadHttpServletResponse(res);

        //avoid swagger requests
        if (!urlHasSwaggerSignature(url)) {
            LOGGER.info("<<<--- REQUEST : " + req.getMethod() + " " + url + (queryString == null ? "" : "?" + queryString));
            String body = multiReadHttpServletRequest.getBody();
            if (body != null && !body.trim().isEmpty()) {
                LOGGER.info("<<<--- REQUEST : BODY : " + cutForLog(body));
            }
        }

        LocalDateTime startDateTime = LocalDateTime.now();
        chain.doFilter(multiReadHttpServletRequest, multiReadHttpServletResponse);
        LocalDateTime finishDateTime = LocalDateTime.now();
        Duration duration = Duration.between(startDateTime, finishDateTime);

        if (!urlHasSwaggerSignature(url)) {
            String body = multiReadHttpServletResponse.getBody();
            LOGGER.info("--->>> RESPONSE : " + multiReadHttpServletResponse.getStatus() + ((body != null && !body.trim().isEmpty()) ? (" BODY : " + cutForLog(body)) : ""));
        }
    }

    private String cutForLog(String body) {
        //TODO remove on release
        //return (body.length()>200)?body.substring(0,100)+" ... "+body.substring(body.length()-100):body;
        return body;
    }

    private static final List<String> SWAGGER_URL_PART_COLLECTION = new ArrayList<>(Arrays.asList(
            "swagger-ui.html",
            "swagger-resources",
            "v2/api-docs",
            "/csrf",
            "webjars",
            "/favicon.ico"
    ));

    private boolean urlHasSwaggerSignature(String url) {
        for (String swaggerPart : SWAGGER_URL_PART_COLLECTION) {
            if (url.contains(swaggerPart)) return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
