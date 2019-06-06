package server;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = {"/*"})
public class GeneralFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(req);
        MultiReadHttpServletResponse multiReadHttpServletResponse = new MultiReadHttpServletResponse(res);

        String url = multiReadHttpServletRequest.getRequestURL().toString();
        String queryString = multiReadHttpServletRequest.getQueryString();

        System.out.println("<<<--- REQUEST : " + multiReadHttpServletRequest.getMethod() + " " + url + (queryString == null ? "" : "?" + queryString));
        String body = multiReadHttpServletRequest.getBody();
        if (body != null && !body.trim().isEmpty()) {
            System.out.println("<<<--- REQUEST : BODY : " + body);
        }

        LocalDateTime startDateTime = LocalDateTime.now();
        filterChain.doFilter(multiReadHttpServletRequest, multiReadHttpServletResponse);
        LocalDateTime finishDateTime = LocalDateTime.now();
        Duration duration = Duration.between(startDateTime, finishDateTime);

        System.out.println("--->>> RESPONSE[" + duration.toMillis() + " ms] : " + ((body != null && !body.trim().isEmpty()) ? (" BODY : " + body) : ""));

    }

    @Override
    public void destroy() {

    }
}
