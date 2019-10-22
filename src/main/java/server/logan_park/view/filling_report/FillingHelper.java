package server.logan_park.view.filling_report;

import org.apache.log4j.Logger;
import orm.entity.logan_park.filling.FillingRecord;
import orm.entity.logan_park.filling.FillingRecordDAO;
import orm.entity.logan_park.fuel_account_leftover.FuelAccountLeftover;
import orm.entity.logan_park.fuel_account_leftover.FuelAccountLeftoverDAO;
import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import server.logan_park.view.filling_report.model.DateLabel;
import server.logan_park.view.filling_report.model.FillingTable;
import server.logan_park.view.filling_report.model.FillingValue;
import server.logan_park.view.filling_report.model.KmRequest;
import server.logan_park.view.weekly_report_general.WeekLinksHelper;
import ui_automation.common.FuelHelper;
import util.DateHelper;
import util.NumberHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FillingHelper {

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    private WeekLinksHelper weekLinksHelper = new WeekLinksHelper();

    public FillingTable makeReport(Date weekFlag) {
        FillingTable fillingTable = new FillingTable();

        fillingTable.setWeekLinksList(weekLinksHelper.linkList());
        Integer week_id = weekLinksHelper.findRangeByDate(weekFlag).getId();
        List<FillingRecord> fillingRecordList = FillingRecordDAO.getInstance()
                .findAllWhereEqual("week_id", week_id)
                .stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate())).collect(Collectors.toList());

        calculateFuel(fillingTable,fillingRecordList);

        addFuelCosts(fillingTable,week_id);

        addLeftover(fillingTable);
        return fillingTable;

    }

    private void calculateFuel(FillingTable fillingTable, List<FillingRecord> fillingRecordList) {
        for (FillingRecord fillingRecord : fillingRecordList) {
            DateLabel dayDateLabel = new DateLabel(DateHelper.getDayDate(fillingRecord.getDate()));
            fillingTable.getFillingRecordMap().putIfAbsent(dayDateLabel, new ArrayList<>());
            fillingTable.getFillingRecordMap().get(dayDateLabel).add(fillingRecord);
            fillingTable.getFillingInfo().getCarDistributedMap().putIfAbsent(fillingRecord.getCar(), new FillingValue());


            fillingTable.getFillingInfo().getWeekFilling().setAmount(NumberHelper.round100(
                    fillingRecord.getAmount() +
                            fillingTable.getFillingInfo().getWeekFilling().getAmount()));

            fillingTable.getFillingInfo().getWeekFilling().setCount(NumberHelper.round100(
                    fillingRecord.getItemAmount() +
                            fillingTable.getFillingInfo().getWeekFilling().getCount()));


            Double amount = fillingTable.getFillingInfo().getCarDistributedMap()
                    .get(fillingRecord.getCar()).getAmount() +
                    fillingRecord.getAmount();
            amount = NumberHelper.round100(amount);

            Double count = fillingTable.getFillingInfo().getCarDistributedMap()
                    .get(fillingRecord.getCar()).getCount() +
                    fillingRecord.getItemAmount();
            count = NumberHelper.round100(count);

            fillingTable.getFillingInfo().getCarDistributedMap().put(fillingRecord.getCar(), new FillingValue(amount, count));

        }
    }

    private void addFuelCosts(FillingTable fillingTable, Integer week_id) {

        WeekRange weekRange = weekLinksHelper.getPreviousWeek(WeekRangeDAO.getInstance().findById(week_id));
        List<FillingRecord> previousWeekFillingRecordList = FillingRecordDAO.getInstance().findAllWhereEqual("week_id", weekRange.getId());
        FuelHelper.getInstance().calculateFuelCost(fillingTable, previousWeekFillingRecordList);
    }

    private void addLeftover(FillingTable fillingTable) {
        FuelAccountLeftoverDAO fuelAccountLeftoverDAO = FuelAccountLeftoverDAO.getInstance();
        FuelAccountLeftover okkoFuelAccountLeftover = fuelAccountLeftoverDAO.latest("okko");
        FuelAccountLeftover upgFuelAccountLeftover = fuelAccountLeftoverDAO.latest("upg");
        fillingTable.setOkkoLeftover(okkoFuelAccountLeftover != null ? okkoFuelAccountLeftover.getValue() : 0.0);
        fillingTable.setUpgLeftover(upgFuelAccountLeftover != null ? upgFuelAccountLeftover.getValue() : 0.0);
    }


    public void addKm(KmRequest kmRequest) {
        FillingRecord fillingRecord = FillingRecordDAO.getInstance().findWhereEqual("date", new Date(kmRequest.getDate()));
        LOGGER.info("try update KM for " + fillingRecord);
        if (fillingRecord != null) {
            fillingRecord.setKm(kmRequest.getKm());
            FillingRecordDAO.getInstance().update(fillingRecord);
        } else {
            LOGGER.warn("can't find filling record for " + new Date(kmRequest.getDate()));
        }

    }
}
