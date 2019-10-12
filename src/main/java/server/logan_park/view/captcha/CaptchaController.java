package server.logan_park.view.captcha;

import org.apache.log4j.Logger;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import orm.entity.uber.uber_captcha.UberCaptcha;
import orm.entity.uber.uber_captcha.UberCaptchaDAO;
import server.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

import static util.IOUtils.FS;

@Controller
public class CaptchaController extends BaseController {
    public static final String PATH = "/logan_park/uber_captcha";
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    //IMAGE link
    @RequestMapping(method = RequestMethod.GET, value = PATH + "/img/{fileId}")
    public ResponseEntity<byte[]> initFile(@PathVariable String fileId, HttpServletRequest request) {
        UberCaptcha uberCaptcha = UberCaptchaDAO.getInstance().findWhereEqual("fileId", fileId);
        LOGGER.info("uberCaptcha : " + uberCaptcha);
        request.getSession(true).setAttribute("uberCaptcha", uberCaptcha.getRealPath());
        LOGGER.info("fileId : " + fileId);

        //String servletContext = request.getServletContext().getRealPath("");

        File file = new File("captcha" + FS + fileId);
        byte[] media = new byte[0];
        if (file.exists()) {
            media = getBytesFromFile(file);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }

    //IMAGE link
    @RequestMapping(method = RequestMethod.GET, value = PATH + "/{fileId}")
    public String initFormWithFile(@PathVariable String fileId, HttpServletRequest request) {
        request.getSession(true).setAttribute("fileId", "fileId");
        return "loganPark" + PATH;
    }

    public byte[] getBytesFromFile(File file) {
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            LOGGER.warn("File is too large!");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (is != null) {
                while (offset < bytes.length
                        && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                    offset += numRead;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (offset < bytes.length) {
            LOGGER.warn("Could not completely read file " + file.getName());
        }
        return bytes;
    }

    @RequestMapping(method = RequestMethod.GET, value = PATH)
    public String initForm(HttpServletRequest request) {
        request.getSession(true).setAttribute("uberCaptcha", "uberCaptcha");
        return "loganPark" + PATH;
    }


}