package api_automation;

import api_automation.bolt_map.BoltMapHttpClient;
import api_automation.bolt_map.BoltMapPinger;
import api_automation.tracker.TrackerMapHttp;

public class Pinger {
    private static final long PING_TIME_MS = 5000;

    public static void main(String[] args) {
        while (true) {
            try {
                BoltMapPinger.saveLodToDB(BoltMapHttpClient.ping());
                TrackerMapHttp.ping();
                Thread.sleep(PING_TIME_MS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
