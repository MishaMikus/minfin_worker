package ui_automation.uber.bo;

import org.apache.commons.io.FileUtils;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import orm.entity.uber.uber_captcha.UberCaptcha;
import orm.entity.uber.uber_captcha.UberCaptchaDAO;
import client.viber.ViberUberRestClient;
import ui_automation.bo.BaseBO;
import util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.driver;
import static util.IOUtils.FS;
import static util.SystemUtil.getMyIP;
import static util.SystemUtil.getMyPort;

public class UberLoginBO extends BaseBO {
    //Thu, 31 Dec 2099 03:00:00 EET;
    private static final SimpleDateFormat SDF_FULL = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss Z");
    private final static Logger LOGGER = Logger.getLogger(UberLoginBO.class);
    private final static File COOKIE_FILE = new File("cookie.csv");

    public UberLoginBO() {
        baseUrl = "https://partners.uber.com";
        goToPath("/");
    }


    public UberLoginBO login(String login, String password) {
        $(By.id("useridInput")).setValue(login);
        LOGGER.info("input login");
        $(By.tagName("button")).click();
        LOGGER.info("click next button");
        checkNotRobot();
        $(By.id("password")).setValue(password);
        LOGGER.info("input password");
        $(By.tagName("button")).click();
        LOGGER.info("click login");
        waitingForDownloadButtonAppear();
        LOGGER.info("login SUCCESS");
        return this;
    }

    private void checkNotRobot() {
        int i = 0;
        WebDriver wd = switchToFrame(waitForRecaptchaFrameAppear("role", "presentation"));

        new WebDriverWait(wd, 10, 500)
                .until(w -> w.findElement(By.id("recaptcha-anchor")).isDisplayed());

        wd.findElement(By.id("recaptcha-anchor")).click();
        LOGGER.info("click I IM NOT A ROBOT");
        wd = switchToFrame(waitForRecaptchaFrameAppear("style","width: 400px; height: 580px;"));
        new WebDriverWait(wd, 10, 500)
                .until(w -> w.findElement(By.tagName("table")).getText().isEmpty());

        LOGGER.info("BEGIN SOLVE CAPTCHA");
        for (WebElement we : wd.findElement(By.tagName("table")).findElements(By.tagName("td"))) {
            int h = we.getRect().height;
            int w = we.getRect().width;
            int x = we.getRect().x;
            int y = we.getRect().y;
            LOGGER.info("cell : "+i++ + " : " + "[h:" + h + "][w:" + w + "][x:" + x + "][y:" + y + "]");
        }



        //upload screen to some file repo (localhost)
        String fileId=UUID.randomUUID().toString();
        File screenshotFile=new File("captcha"+FS+fileId+".jpg");
        takeSnapShot(wd,screenshotFile.getAbsolutePath());
        LOGGER.info("WAIT OPERATOR");

        //save image file name to db record
        UberCaptcha uberCaptcha=new UberCaptcha();
        uberCaptcha.setCreated(new Date());
        uberCaptcha.setFileId(fileId);
        UberCaptchaDAO.getInstance().save(uberCaptcha);

        //send viber message to operator with solving url
        ViberUberRestClient.getInstance().sendCaptcha("http://"+getMyIP()+getMyPort()+"/uber_captcha/"+fileId);

        //TODO
        //click link for solving captcha
            //link redirect to page where one image (screenshot) and one input field for solving
            //update captcha solving order
        //wait for captcha solving record solved
        //click solving
    }

    public static void takeSnapShot(WebDriver webdriver,String fileWithPath){
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(fileWithPath);
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String waitForRecaptchaFrameAppear(String attribute, String value) {
        Map<String,String> map=new HashMap<>();
        map.put(attribute,value);
        return waitForRecaptchaFrameAppear(map);
    }

    private WebDriver switchToFrame(String name) {
        WebDriver webDriver = driver().getWebDriver();
        webDriver.switchTo().frame(name);
        return webDriver;
    }

    private String waitForRecaptchaFrameAppear(Map<String, String> attributePattern) {
        driver().getWebDriver().switchTo().defaultContent();
        boolean neeedToCheck = true;
        long start = new Date().getTime();
        long timeout = 10 * 1000;
        String name;
        while (neeedToCheck) {
            final List<WebElement> iframeList = driver().getWebDriver().findElements(By.tagName("iframe"));
            //<iframe
            // title="recaptcha challenge"
            // src="https://www.google.com/recaptcha/api2/bframe?hl=en&amp;v=v1566858990656&amp;k=6LdoZSkTAAAAAEyquKnCAeiBngVx1w1DOfML7cix&amp;cb=vtqsa97lh4ji"
            // name="c-n9hlxltahfa2"
            // frameborder="0"
            // scrolling="no"
            // sandbox="allow-forms allow-popups allow-same-origin allow-scripts allow-top-navigation allow-modals allow-popups-to-escape-sandbox"
            // style="width: 400px; height: 580px;"></iframe>

            for (WebElement iframe : iframeList) {
                LOGGER.info("FRAME");
                LOGGER.info("iframe.title : "+getAttribute(iframe,"title"));
                LOGGER.info("iframe.src : "+getAttribute(iframe,"src"));
                LOGGER.info("iframe.name : "+getAttribute(iframe,"name"));
                LOGGER.info("iframe.frameborder : "+getAttribute(iframe,"frameborder"));
                LOGGER.info("iframe.scrolling : "+getAttribute(iframe,"scrolling"));
                LOGGER.info("iframe.sandbox : "+getAttribute(iframe,"sandbox"));
                LOGGER.info("iframe.style : "+getAttribute(iframe,"style"));
                boolean allMatched = true;
                for (Map.Entry<String, String> entry : attributePattern.entrySet()) {
                    allMatched &= iframe.getAttribute(entry.getKey()) != null &&
                            iframe.getAttribute(entry.getKey()).contains(entry.getValue());
                }

                if (allMatched) {
                    name = iframe.getAttribute("name");
                    LOGGER.info("MATCH : " + name);
                    return name;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                neeedToCheck = (new Date().getTime() - start) < timeout;
            }
        }
        return "";
    }

    private String getAttribute(WebElement iframe, String attribute) {
        try
        {
            return iframe.getAttribute(attribute);
        }catch (Exception e){
            LOGGER.warn("attribute '"+attribute+"' not found");
            return null;
        }
    }

    public void setSMSCodeIfNeed() {
        //TODO use Viber BOT
    }


    public UberLoginBO loadCookie() {

        for (Cookie cookie : loadCookieFromFile()) {
            driver().getWebDriver().manage().addCookie(cookie);
        }
        return this;
    }


    private List<Cookie> loadCookieFromFile() {
        List<Cookie> res = new ArrayList<>();
        String content = IOUtils.readTextFromFile(COOKIE_FILE);
        for (String row : content.split("\n")) {
            BasicClientCookie basicClientCookie = parseRawCookie(row);
            //String name, String value, String domain, String path, Date expiry, boolean isSecure, boolean isHttpOnly
            if (basicClientCookie != null) {
                Cookie cookie = new Cookie(
                        basicClientCookie.getName(),
                        basicClientCookie.getValue(),
                        basicClientCookie.getDomain(),
                        basicClientCookie.getPath(),
                        basicClientCookie.getExpiryDate(),
                        basicClientCookie.isSecure(),
                        false);
                res.add(cookie);
            }
        }
        return res;
    }

    BasicClientCookie parseRawCookie(String rawCookie) {
        String[] rawCookieParams = rawCookie.split(";");

        String[] rawCookieNameAndValue = rawCookieParams[0].split("=");
        if (rawCookieNameAndValue.length != 2) {
            LOGGER.warn("Invalid cookie: missing name and value.");
            return null;
        }

        String cookieName = rawCookieNameAndValue[0].trim();
        String cookieValue = rawCookieNameAndValue[1].trim();
        BasicClientCookie cookie = new BasicClientCookie(cookieName, cookieValue);
        for (int i = 1; i < rawCookieParams.length; i++) {
            String rawCookieParamNameAndValue[] = rawCookieParams[i].trim().split("=");

            String paramName = rawCookieParamNameAndValue[0].trim();

            if (paramName.equalsIgnoreCase("secure")) {
                cookie.setSecure(true);
            } else {
                if (rawCookieParamNameAndValue.length != 2) {
                    LOGGER.warn("Invalid cookie: attribute not a flag or missing value.");
                }

                String paramValue = rawCookieParamNameAndValue[1].trim();

                if (paramName.equalsIgnoreCase("expires")) {
                    Date expiryDate = null;
                    try {
                        //Thu, 31 Dec 2099 03:00:00 EET;
                        expiryDate = SDF_FULL.parse(paramValue);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cookie.setExpiryDate(expiryDate);
                } else if (paramName.equalsIgnoreCase("max-age")) {
                    long maxAge = Long.parseLong(paramValue);
                    Date expiryDate = new Date(new Date().getTime() + maxAge);
                    cookie.setExpiryDate(expiryDate);
                } else if (paramName.equalsIgnoreCase("domain")) {
                    cookie.setDomain(paramValue);
                } else if (paramName.equalsIgnoreCase("path")) {
                    cookie.setPath(paramValue);
                } else if (paramName.equalsIgnoreCase("comment")) {
                    cookie.setPath(paramValue);
                } else {
                    LOGGER.warn("Invalid cookie: invalid attribute name.");
                    return null;
                }
            }
        }
        return cookie;
    }


    private void saveCookie() {
        Set<Cookie> cookieset = driver().getWebDriver().manage().getCookies();
        String[] cookieString = new String[1];
        cookieString[0] = "";
        cookieset.forEach(c -> cookieString[0] += c.toString() + "\n");
        IOUtils.saveTextToFile(COOKIE_FILE, cookieString[0]);
    }

    public UberLoginBO clearCookie() {
        driver().getWebDriver().manage().deleteAllCookies();
        return this;
    }

    public UberLoginBO loginIfNotAuthorized(String login, String pass) {
        if (!authorized()) return login(login, pass);
        goToPath("p3/payments/statements");//start page
        return this;
    }

    private boolean authorized() {
        return driver().getWebDriver().getPageSource().contains("Михайло Мікусь");
    }
}
