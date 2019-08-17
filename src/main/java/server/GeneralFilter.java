package server;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = {"/*"})
public class GeneralFilter implements Filter {

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(req);
        MultiReadHttpServletResponse multiReadHttpServletResponse = new MultiReadHttpServletResponse(res);

        String url = multiReadHttpServletRequest.getRequestURL().toString();
        String queryString = multiReadHttpServletRequest.getQueryString();

        LOGGER.info("<<<--- REQUEST : " + multiReadHttpServletRequest.getMethod() + " " + url + (queryString == null ? "" : "?" + queryString));
        String body = multiReadHttpServletRequest.getBody();
        String bodyLog = body.length() < 200 ? body : body.substring(0, 100) + " ... " + body.substring(body.length()-100);
        if (!body.trim().isEmpty()) {
            LOGGER.info("<<<--- REQUEST : BODY : " + bodyLog);
        }

        LocalDateTime startDateTime = LocalDateTime.now();
        filterChain.doFilter(multiReadHttpServletRequest, multiReadHttpServletResponse);
        LocalDateTime finishDateTime = LocalDateTime.now();
        Duration duration = Duration.between(startDateTime, finishDateTime);
        LOGGER.info("--->>> RESPONSE[" + duration.toMillis() + " ms] : " + (!body.trim().isEmpty() ? " BODY : " + bodyLog : ""));

    }

    @Override
    public void destroy() {

    }
}
