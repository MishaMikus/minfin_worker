package api_automation.bolt_map;

import orm.entity.bolt.map_pinger_item.MapPingerItem;
import orm.entity.bolt.map_pinger_item.MapPingerItemDAO;

public class BoltMapPinger {
    public static void main(String[] args) {
        while (true) {
            try {
                BoltDriverStatusResponse boltDriverStatusResponse = BoltMapHttpClient.ping();
                saveLodToDB(boltDriverStatusResponse);
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveLodToDB(BoltDriverStatusResponse boltDriverStatusResponse) {
        for (BoltDriverStatusDataItem boltDriverStatusDataItem : boltDriverStatusResponse.getData().getList()) {
            MapPingerItem mapPingerItem = new MapPingerItem(boltDriverStatusDataItem);
            MapPingerItemDAO.getInstance().save(mapPingerItem);
        }
    }
}
