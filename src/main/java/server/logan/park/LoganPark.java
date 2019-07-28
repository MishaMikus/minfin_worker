package server.logan.park;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import server.BaseController;
import server.logan.park.service.PaymentTabHelper;
import server.logan.park.view.PaymentView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LoganPark extends BaseController {
    private static final String ENDPOINT = "/logan_park";

    @RequestMapping(value = ENDPOINT)
    public String getLoganPark(HttpServletRequest request) {
        if (request.getSession(true).getAttribute("paymentView") == null) {
            request.getSession(true).setAttribute("paymentView", new PaymentView());
        }
        return "logan_park";
    }

    @RequestMapping(value = ENDPOINT + "/upload_payment_csv")
    public String uploadPaymentCSV(HttpServletRequest request, @RequestParam("payment_file") MultipartFile file) {
        String content = "NO_content";
        try {
            content = new String(file.getBytes(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PaymentTabHelper paymentTabHelper=new PaymentTabHelper();
        PaymentView paymentView = new PaymentView(content);
        paymentView.setName(file.getOriginalFilename());
        paymentView.setReport(new String(paymentTabHelper.makeReport(content).getBytes(), Charset.forName("UTF-8")));
        paymentView.setAllDataTable(new String(paymentTabHelper.makeAllDataTable(content).getBytes(), Charset.forName("UTF-8")));
        System.out.println(paymentView.getReport());
        paymentView.setContent(content);
        request.getSession(true).setAttribute("paymentView", paymentView);
        return "redirect:" + ENDPOINT;
    }
}