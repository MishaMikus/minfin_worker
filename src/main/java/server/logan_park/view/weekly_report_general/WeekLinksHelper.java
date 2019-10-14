package server.logan_park.view.weekly_report_general;

import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import server.logan_park.view.weekly_report_general.model.WeekLink;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WeekLinksHelper {
    public List<WeekLink> linkList() {
        List<WeekLink> res=new ArrayList<>();
        final int[] i = {1};
        WeekRangeDAO.getInstance().findAll().stream()
                .sorted(Comparator.comparing(WeekRange::getStart))
                .collect(Collectors.toList()).forEach(w -> {
            WeekLink weekLink = new WeekLink();
            String start = server.logan_park.view.weekly_report_general.DateValidator.SDF.format(w.getStart());
            String end = server.logan_park.view.weekly_report_general.DateValidator.SDF.format(w.getEnd());
            weekLink.setLabel(start + "-" + end);
            weekLink.setHref(start);
            weekLink.setId(i[0]++);
            res.add(weekLink);
        });
        return res;
    }
}
