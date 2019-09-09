package ui_automation.uber.bo;

import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import parser.IOUtils;
import ui_automation.bo.BaseBO;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.driver;

public class UberLoginBO extends BaseBO {
    //Thu, 31 Dec 2099 03:00:00 EET;
    private static final SimpleDateFormat SDF_FULL=new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss Z");
    private final static Logger LOGGER = Logger.getLogger(UberLoginBO.class);
    private final static File COOKIE_FILE = new File("cookie.csv");

    public UberLoginBO() {
        baseUrl = "https://partners.uber.com";
        goToPath("/");
    }


    public UberLoginBO login(String login, String password) {
            $(By.id("useridInput")).setValue(login);
            $(By.tagName("button")).click();
            $(By.id("password")).setValue(password);
            $(By.tagName("button")).click();
        waitingForDownloadButtonAppear();
            return this;
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
                if(basicClientCookie!=null){
                Cookie cookie=new Cookie(
                        basicClientCookie.getName(),
                        basicClientCookie.getValue(),
                        basicClientCookie.getDomain(),
                        basicClientCookie.getPath(),
                        basicClientCookie.getExpiryDate(),
                        basicClientCookie.isSecure(),
                        false );
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
        if(!authorized()) return login(login,pass);
        goToPath("p3/payments/statements");//start page
        return this;
    }

    private boolean authorized() {
        return driver().getWebDriver().getPageSource().contains("Михайло Мікусь");
    }
}
