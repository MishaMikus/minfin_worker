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

@WebFilter(urlPatterns = {"/*"})
public class GeneralFilter extends HttpFilter {
    protected final Logger LOGGER = Logger.getLogger(this.getClass());

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        String queryString = req.getQueryString();
        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(req);
        MultiReadHttpServletResponse multiReadHttpServletResponse = new MultiReadHttpServletResponse(res);

        LOGGER.info("<<<--- REQUEST : " + req.getMethod() + " " + url + (queryString == null ? "" : "?" + queryString));
        String body = multiReadHttpServletRequest.getBody();
        if (body != null && !body.trim().isEmpty()) {
            LOGGER.info("<<<--- REQUEST : BODY : " + body);
        }

        LocalDateTime startDateTime = LocalDateTime.now();
        chain.doFilter(multiReadHttpServletRequest, multiReadHttpServletResponse);
        LocalDateTime finishDateTime = LocalDateTime.now();
        Duration duration = Duration.between(startDateTime, finishDateTime);

        LOGGER.info("--->>> RESPONSE["+duration.toMillis()+" ms] : " + multiReadHttpServletResponse.getStatus() + ((body != null && !body.trim().isEmpty()) ? (" BODY : " + body) : ""));
    }


    @Override
    public void destroy() {

    }
}
