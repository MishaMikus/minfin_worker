package server.logan_park.view.filling_report;

import orm.entity.okko.uber_okko_filling.FillingRecord;
import orm.entity.okko.uber_okko_filling.FillingRecordDAO;
import server.logan_park.view.filling_report.model.FillingTable;
import server.logan_park.view.filling_report.model.FillingValue;
import server.logan_park.view.weekly_report_general.WeekLinksHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static server.logan_park.view.weekly_report_general.DateValidator.SDF;

public class FillingHelper {
    private WeekLinksHelper weekLinksHelper = new WeekLinksHelper();

    public FillingTable makeReport(Date weekFlag) {
        FillingTable fillingTable = new FillingTable();

        fillingTable.setWeekLinksList(weekLinksHelper.linkList());
        Integer week_id = weekLinksHelper.findRangeByDate(weekFlag).getId();
        List<FillingRecord> fillingRecordList = FillingRecordDAO.getInstance()
                .findAllWhereEqual("week_id", week_id)
                .stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate())).collect(Collectors.toList());
        for (FillingRecord fillingRecord : fillingRecordList) {
            String day = SDF.format(fillingRecord.getDate());
            fillingTable.getFillingRecordMap().putIfAbsent(day, new ArrayList<>());
            fillingTable.getFillingRecordMap().get(day).add(fillingRecord);
            fillingTable.getFillingInfo().getCarDistributedMap().putIfAbsent(fillingRecord.getCar(), new FillingValue());

            Double amount = fillingTable.getFillingInfo().getCarDistributedMap()
                    .get(fillingRecord.getCar()).getAmount() +
                    fillingRecord.getAmount();
            amount =round100(amount);

            Double count = fillingTable.getFillingInfo().getCarDistributedMap()
                    .get(fillingRecord.getCar()).getCount() +
                    fillingRecord.getItemAmount();
            count = round100(count);

            fillingTable.getFillingInfo().getCarDistributedMap().put(fillingRecord.getCar(), new FillingValue(amount,count));

            fillingTable.getFillingInfo().getWeekFilling().setAmount(round100(amount+
                    fillingTable.getFillingInfo().getWeekFilling().getAmount()));

            fillingTable.getFillingInfo().getWeekFilling().setCount(round100(count+
                    fillingTable.getFillingInfo().getWeekFilling().getCount()));
        }

        return fillingTable;

    }

    public static Double round100(Double value) {
        return round(value * 100) / 100.0;
    }
}
