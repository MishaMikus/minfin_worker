package ui_automation.uber;

import org.apache.log4j.Logger;
import orm.entity.uber.update_request.UberUpdateWeekReportRequest;
import orm.entity.uber.update_request.UberUpdateWeekReportRequestDAO;
import ui_automation.uber.bo.UberBO;
import ui_automation.uber.bo.UberLoginBO;
import util.ApplicationPropertyUtil;

import java.util.Date;

import static com.codeborne.selenide.Configuration.headless;
import static util.ApplicationPropertyUtil.getBoolean;

public class UberWeeklyReportWorker {
    private static final Logger LOGGER = Logger.getLogger(UberWeeklyReportWorker.class);
    public static void stat() {

        if (ApplicationPropertyUtil.getBoolean("selenide.proxy", false)) {
            System.out.println("SET PROXY");
            System.setProperty("selenide.proxyEnabled", "true");
            System.setProperty("selenide.proxyHost", "127.0.0.1");
            System.setProperty("selenide.proxyPort", "6666");
        }

        headless = getBoolean("headless",true);

        //failed LOGIN
//        new UberLoginBO()
//                .clearCookie()
//                .loadCookie()
//                .login(ApplicationPropertyUtil.applicationPropertyGet("uber.login")
//                , ApplicationPropertyUtil.applicationPropertyGet("uber.password"));
//
//        driver().close();
        new UberBO().recordPayment();

    }

    public static void main(String[] args) {
        runWorker();
    }

    private static void runWorker() {
        Long pingTime=1000L;
        new UberLoginBO()
                .loginIfNotAuthorized(ApplicationPropertyUtil.applicationPropertyGet("uber.login")
                        , ApplicationPropertyUtil.applicationPropertyGet("uber.password"))
                .setSMSCodeIfNeed();
        while (true){
            LOGGER.info("check updateRequests");
            UberUpdateWeekReportRequest latest = UberUpdateWeekReportRequestDAO.getInstance().latest();
            if(latest!=null&&!latest.getStarted()){
                latest.setStarted(true);
                try {
                    UberUpdateWeekReportRequestDAO.getInstance().update(latest);
                    stat();
                    latest.setUpdated(new Date());
                    UberUpdateWeekReportRequestDAO.getInstance().update(latest);
                }catch (Exception e){
                    e.printStackTrace();
                    LOGGER.warn("Turn back started false");
                    latest.setStarted(false);
                    UberUpdateWeekReportRequestDAO.getInstance().update(latest);
                }
            }
            try {
                Thread.sleep(pingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
