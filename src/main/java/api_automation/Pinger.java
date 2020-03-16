package api_automation;

import api_automation.bolt_map.BoltMapHttpClient;
import api_automation.bolt_map.BoltMapPinger;
import api_automation.okko.OkkoBO;
import api_automation.tracker.TrackerMapHttp;
import api_automation.upg.UpgBO;
import util.ApplicationPropertyUtil;

import java.util.Date;

import static api_automation.BaseClient.CLIENT;

public class Pinger {
    private static final long PING_TIME_MS = 5000;

    public static void main(String[] args) {
        runWorker();
        System.exit(0);
    }

    public static void runWorker() {

        long api_worker_lifetime_ms = ApplicationPropertyUtil.getLong("api_worker_lifetime_ms", 3600000);
        long start = new Date().getTime();
      //  while (new Date().getTime() - start < api_worker_lifetime_ms) {
            try {
                BoltMapPinger.saveLodToDB(BoltMapHttpClient.ping());
                clearCookie();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                TrackerMapHttp.ping();
                clearCookie();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                OkkoBO.getFuelReport();
                clearCookie();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            try {
//                UpgBO.getFuelReport();
//                clearCookie();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            try {
                Thread.sleep(PING_TIME_MS);
            } catch (Exception e) {
                e.printStackTrace();
            }
       // }
    }

    private static void clearCookie() {
        CLIENT.clearCookie();
    }
}
