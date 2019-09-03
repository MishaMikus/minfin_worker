package server.logan.park;

import server.logan.park.service.GeneralPartnerSummary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import server.BaseController;
import server.logan.park.service.PaymentTabHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
public class LoganPark extends BaseController {
    private static final String ENDPOINT = "/logan_park";

    @RequestMapping(value = ENDPOINT)
    public String getLoganPark(HttpServletRequest request) {
        if (request.getSession(true).getAttribute("paymentTable") == null) {
            request.getSession(true).setAttribute("fileName", "");
            request.getSession(true).setAttribute("paymentTable", new HashMap<>());
            request.getSession(true).setAttribute("ownerTable", new HashMap<>());
            request.getSession(true).setAttribute("generalPartnerSummary", new GeneralPartnerSummary());

        }
        return "logan_park";
    }

    @RequestMapping(value = ENDPOINT + "/upload_payment_csv")
    public String uploadPaymentCSV(HttpServletRequest request, @RequestParam("payment_file") MultipartFile file) {
        String content = "NO_content";
        try {
            content = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.getSession(true).setAttribute("fileName", file.getOriginalFilename());
        PaymentTabHelper paymentTabHelper=new PaymentTabHelper(content);
        request.getSession(true).setAttribute("paymentTable", paymentTabHelper.makeMap());
        request.getSession(true).setAttribute("ownerTable", paymentTabHelper.makeOwnerMap());
        request.getSession(true).setAttribute("generalPartnerSummary", paymentTabHelper.makeGeneralPartnerSummary());
        return "redirect:" + ENDPOINT;
    }
}