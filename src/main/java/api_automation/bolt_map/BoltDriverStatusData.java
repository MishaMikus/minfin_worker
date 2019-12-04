package api_automation.bolt_map;

import java.util.List;

public class BoltDriverStatusData {
    private List<BoltDriverStatusDataItem> list;

    public List<BoltDriverStatusDataItem> getList() {
        return list;
    }

    public void setList(List<BoltDriverStatusDataItem> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BoltDriverStatusData{" +
                "list=" + list +
                '}';
    }
}
