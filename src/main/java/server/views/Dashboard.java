package server.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import server.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Dashboard extends BaseController {
    @RequestMapping(value = "/dashboard")
    public String getDashboard(
            HttpServletRequest request,
            HttpServletResponse response) {
        request.getSession(true).setAttribute("msg","Server_Message");

        return "dashboard";
    }
}