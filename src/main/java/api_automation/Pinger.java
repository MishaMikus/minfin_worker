package api_automation;

import api_automation.bolt_map.BoltMapHttpClient;
import api_automation.bolt_map.BoltMapPinger;
import api_automation.okko.OkkoBO;
import api_automation.tracker.TrackerMapHttp;
import api_automation.upg.UpgBO;

public class Pinger {
    private static final long PING_TIME_MS = 5000;

    public static void main(String[] args) {
        while (true) {
            try {
                BoltMapPinger.saveLodToDB(BoltMapHttpClient.ping());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                TrackerMapHttp.ping();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                OkkoBO.getFuelReport();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                UpgBO.getFuelReport();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(PING_TIME_MS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
