package server.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import server.BaseController;

@Controller
public class Dashboard extends BaseController {
    @RequestMapping(value = "/dashboard")
    public String getDashboard() {
        return "dashboard";
    }
}