package server.logan_park.view.weekly_report_general;

import org.apache.log4j.Logger;
import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import server.logan_park.view.weekly_report_general.model.WeekLink;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class WeekLinksHelper {
    private final static Logger LOGGER = Logger.getLogger(WeekLinksHelper.class);

    public List<WeekLink> linkList() {
        List<WeekLink> res = new ArrayList<>();
        final int[] i = {1};
        WeekRangeDAO.getInstance().findAll().stream()
                .sorted(Comparator.comparing(WeekRange::getStart))
                .collect(Collectors.toList()).forEach(w -> {
            WeekLink weekLink = new WeekLink();
            Date firstDay=new Date(w.getStart().getTime()+24*60*60*1000L);
            String start = server.logan_park.view.weekly_report_general.DateValidator.SDF.format(firstDay);
            String start_Label = server.logan_park.view.weekly_report_general.DateValidator.SDF.format(w.getStart());
            String end = server.logan_park.view.weekly_report_general.DateValidator.SDF.format(w.getEnd());
            weekLink.setLabel(start_Label + "-" + end);
            weekLink.setHref(start);
            weekLink.setId(i[0]++);
            res.add(weekLink);
        });

        return res.subList(res.size()<3?0:res.size()-3,res.size());
    }

    public WeekRange findRangeByDate(Date weekFlag) {
        return WeekRangeDAO.getInstance().findOrCreateWeek(weekFlag, "link_helper");
    }

    public WeekRange getPreviousWeek(WeekRange weekRange) {
        WeekRange res = null;
        if (weekRange.getStart() == null) return res;
        res = WeekRangeDAO.getInstance().findAll()
                .stream().sorted((o1, o2) -> o2.getStart().compareTo(o1.getStart()))
                .filter(w -> w.getStart().getTime() < weekRange.getStart().getTime()).findFirst().orElse(null);
        LOGGER.info("getPreviousWeek : " + res);
        return res;
    }
}
