package server.logan_park.view.weekly_report_manual_uber;

import server.logan_park.helper.model.GeneralPartnerSummary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import server.BaseController;
import server.logan_park.helper.ManuallyWeeklyReportHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
public class LoganParkController extends BaseController {

    @RequestMapping(value = "/logan_park/week_report_manual_uber")
    public String getLoganPark(HttpServletRequest request) {
        if (request.getSession(true).getAttribute("paymentTable") == null) {
            request.getSession(true).setAttribute("fileName", "");
            request.getSession(true).setAttribute("paymentTable", new HashMap<>());
            request.getSession(true).setAttribute("ownerTable", new HashMap<>());
            request.getSession(true).setAttribute("generalPartnerSummary", new GeneralPartnerSummary());
            request.getSession(true).setAttribute("driverRateScreen", new HashMap<>());
        }
        return "loganPark/week_report_manual_uber";
    }

    @RequestMapping(value = "/logan_park/upload_payment_csv")
    public String uploadPaymentCSV(HttpServletRequest request, @RequestParam("payment_file") MultipartFile file) {
        String content = "NO_content";
        try {
            content = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.getSession(true).setAttribute("fileName", file.getOriginalFilename());
        ManuallyWeeklyReportHelper manuallyWeeklyReportHelper =new ManuallyWeeklyReportHelper(content);
        request.getSession(true).setAttribute("paymentTable", manuallyWeeklyReportHelper.makeMap());
        request.getSession(true).setAttribute("ownerTable", manuallyWeeklyReportHelper.makeOwnerMap());
        request.getSession(true).setAttribute("generalPartnerSummary", manuallyWeeklyReportHelper.makeGeneralPartnerSummary());
        request.getSession(true).setAttribute("driverRateScreen", manuallyWeeklyReportHelper.driverRateScreen());
        return "redirect:" + "/logan_park/week_report_manual_uber";
    }
}