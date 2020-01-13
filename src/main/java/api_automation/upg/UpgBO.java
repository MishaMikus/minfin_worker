package api_automation.upg;

import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;
import orm.entity.logan_park.filling.FillingRecord;
import orm.entity.logan_park.filling.FillingRecordDAO;
import orm.entity.logan_park.fuel_account_leftover.FuelAccountLeftover;
import orm.entity.logan_park.fuel_account_leftover.FuelAccountLeftoverDAO;
import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import ui_automation.common.FuelHelper;
import util.NumberHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static api_automation.upg.UpgHttpClient.*;
import static util.DateHelper.getWeekStartDate;
import static util.DateHelper.roundToMidnight;

public class UpgBO {

    private final static Logger LOGGER = Logger.getLogger(UpgBO.class);

    public static void main(String[] args) {
        getFuelReport();
    }

    public static void getFuelReport() {
        ResponseModel responseModel = getHost();
        String tkn = responseModel.getBody().split("value=\"")[1].split("\"")[0];
        postLogin(tkn);
        FuelAccountLeftoverDAO.getInstance().save(findLeftover());
        FillingRecord latestFillingRecord = getLatestFillingRecord();
        List<FillingRecord> allRecords = getAllLatestFillings(latestFillingRecord);
        FillingRecordDAO.getInstance().saveBatch(allRecords);
    }

    //17.10.2019 19:09:54
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static List<FillingRecord> getAllLatestFillings(FillingRecord latestFillingRecord) {
        String source = getListPage().getBody();
        List<FillingRecord> res = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        Arrays.asList(source.split("cid=")).forEach(e -> {
            if (i.getAndIncrement() > 0) {
                String cid = e.split("\"")[0];
                res.addAll(parseTransactions(latestFillingRecord, upgGet("ua/owner/account/list?cid=" + cid).getBody()));
            }

        });
        res.forEach(LOGGER::info);
        return res;
    }


    private static List<FillingRecord> parseTransactions(FillingRecord latestFillingRecord, String source) {
        List<FillingRecord> res = new ArrayList<>();
        Arrays.asList(source.split("ui-corner-bottom ui-content")).forEach(e -> {
            try {
                FillingRecord fillingRecord = parseFillingPopup(e);
                if (fillingRecord.getDate().getTime() > latestFillingRecord.getDate().getTime()) {
                    res.add(fillingRecord);
                    //TODO viber notification
                }
            } catch (Exception ee) {
            }
        });
        return res;
    }

    private static FillingRecord parseFillingPopup(String content) {
        FillingRecord fillingRecord = new FillingRecord();
        String idString = bound(content, "№ ", "</h3>");
        String dateString = bound(content, "<p><b>ДАТА ЧАС</b>: ", "</p>");
        String cardNumberString = bound(content, "<p><b>КАРТА</b>: ", "</p>");
        String itemAmountString = bound(content, "<p><b>КІЛЬКІСТЬ</b>: ", "</p>");
        String priceString = bound(content, "<p><b>ЦІНА</b>: ", "</p>");
        String stationNameString = bound(content, "<p><b>АЗС</b>: ", "</p>");
        String addressString = bound(content, "<p><b>Адреса</b>: ", "</p>");
        fillingRecord.setDate(parseDate(dateString));
        fillingRecord.setWeek_id(findOrCreateWeek(fillingRecord.getDate()).getId());
        fillingRecord.setShop(stationNameString);
        fillingRecord.setAddress(addressString);
        fillingRecord.setId(idString);
        fillingRecord.setCard(cardNumberString.trim().replaceAll(" ", ""));
        fillingRecord.setItemAmount(Double.parseDouble(itemAmountString));
        fillingRecord.setPrice(Double.parseDouble(priceString));

        fillingRecord.setAmount(NumberHelper.round100(fillingRecord.getItemAmount() * fillingRecord.getPrice()));
        fillingRecord.setCar(FuelHelper.getInstance().findOutCarIdentity(fillingRecord.getCard(), "upg"));
        fillingRecord.setStation("upg");
        if (fillingRecord.getItemAmount() > 80) {
            System.err.println("parsing ERROR");
            System.out.println(content);
            System.exit(1);
        }
        return fillingRecord;
    }

    private static final List<WeekRange> WEEK_LIST = WeekRangeDAO.getInstance().findAll();

    private static WeekRange findOrCreateWeek(Date date) {
        date = roundToMidnight(date);
        Date finalDate = date;
        WeekRange weekRange = WEEK_LIST.stream().filter(r ->
                r.getStart().getTime() <= finalDate.getTime() && r.getEnd().getTime() >= finalDate.getTime()
        ).findAny().orElse(null);
        LOGGER.info(date + " -> " + weekRange);
        if (weekRange == null) {

            //create new week_range
            LOGGER.info("create new week_range");
            Date start = roundToMidnight(getWeekStartDate(date));
            long msInDay = 24L * 60L * 60L * 1000L;
            Date end = new Date(start.getTime() + 6L * msInDay);
            WeekRange newWeekRange = new WeekRange();
            newWeekRange.setStart(start);
            newWeekRange.setEnd(end);
            newWeekRange.setCreator("upg_worker");

            //save
            int id = (int) WeekRangeDAO.getInstance().save(newWeekRange);
            newWeekRange.setId(id);
            weekRange = newWeekRange;
            LOGGER.info("newWeekRange : " + newWeekRange);
            WEEK_LIST.add(newWeekRange);
        }
        return weekRange;

    }

    private static Date parseDate(String dateString) {
        try {
            SDF.setTimeZone(TimeZone.getTimeZone("EET"));
            return SDF.parse(dateString);
        } catch (ParseException e) {
            LOGGER.warn("date parsing failure : \n" + e.getMessage());
        }
        return null;
    }

    private static String bound(String content, String from, String to) {
        return content.split(from)[1].split(to)[0];
    }

    public static FuelAccountLeftover findLeftover() {
        FuelAccountLeftover fuelAccountLeftover = new FuelAccountLeftover();
        fuelAccountLeftover.setDate(new Date());
        fuelAccountLeftover.setStation("upg");
        String source = getHomePage().getBody();
        String leftover = parseLeftover(source);
        fuelAccountLeftover.setValue(Double.parseDouble(leftover));
        return fuelAccountLeftover;
    }

    private static String parseLeftover(String source) {
        //<div class="grid_3"><div class="ui-bar ui-bar-c wui-cell" align="right" style=""><div class="v-outer"><div class="v-middle">3179.86</div></div></div></div>
        return bound(source, "<div class=\"grid_3\"><div class=\"ui-bar ui-bar-c wui-cell\" align=\"right\" style=\"\"><div class=\"v-outer\"><div class=\"v-middle\">"
                , "</div></div></div></div>");
    }

    private static FillingRecord getLatestFillingRecord() {
        FillingRecord fillingRecordLatest = FillingRecordDAO.getInstance().latest("upg");
        return fillingRecordLatest == null ? new FillingRecord(new Date(0)) : fillingRecordLatest;
    }
}
