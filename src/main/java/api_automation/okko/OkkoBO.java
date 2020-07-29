package api_automation.okko;

import client.rest.model.ResponseModel;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import orm.entity.logan_park.filling.FillingRecord;
import orm.entity.logan_park.filling.FillingRecordDAO;
import orm.entity.logan_park.fuel_account_leftover.FuelAccountLeftover;
import orm.entity.logan_park.fuel_account_leftover.FuelAccountLeftoverDAO;
import orm.entity.logan_park.week_range.WeekRange;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import ui_automation.common.FuelHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static api_automation.okko.OkkoHttpClient.getHost;

public class OkkoBO {
    private final static Logger LOGGER = Logger.getLogger(OkkoBO.class);

    public static void main(String[] args) {
        getFuelReport();
    }

    public static void getFuelReport() {
        ResponseModel responseModel = getHost();
        String urlToken = parseUrlToken(responseModel.getBody());
        OkkoHttpClient.postLogin(urlToken);
        ResponseModel responseModelHomePage = OkkoHttpClient.getHomePage();
        FuelAccountLeftoverDAO.getInstance().save(findLeftover(responseModelHomePage));

        FillingRecord latestFillingRecord = getLatestFillingRecord();
        List<FillingRecord> allRecords = getAllLatestFillings(responseModelHomePage.getBody(), latestFillingRecord);
        FillingRecordDAO.getInstance().saveBatch(allRecords);
    }


    private static String parseUrlToken(String body) {
        String res = body.split("OKKO-online")[1].split("\" rel=\"stylesheet\"")[0].split("jspx")[1];
        //<head><title>OKKO-online: Login</title>
        // <link class="component" href="/a4j/s/3_3_3.Finalorg/richfaces/renderkit/html/css/basic_classes.xcss/DATB/eAF7sqpgb-jyGdIAFrMEaw__.jspx
        // ;jsessionid=bJPpph8T0RjtqxknJgBnY62PB2QgNh8Bhlvbrt8WcfYtplvDsY3C!-1496176412" rel="stylesheet"
        return res;
    }


    private static WeekRange findOrCreateWeek(Date date) {
        //TODO improve performance
        return WeekRangeDAO.getInstance().findOrCreateWeek(date, "upg_worker");
    }


    public static FuelAccountLeftover findLeftover(ResponseModel responseModelHomePage) {
        FuelAccountLeftover fuelAccountLeftover = new FuelAccountLeftover();
        fuelAccountLeftover.setDate(new Date());
        fuelAccountLeftover.setStation("okko");
        String leftover = parseLeftover(responseModelHomePage.getBody());
        fuelAccountLeftover.setValue(Double.parseDouble(leftover));
        return fuelAccountLeftover;
    }

    private static String parseLeftover(String source) {
        Document htmlDocument = Jsoup.parse(source);
        String uah = htmlDocument.getElementsByClass("accounts").get(0).getElementsByClass("right-align bold").get(0).text();
        return uah.split(" ")[0];
    }

    private static List<FillingRecord> getAllLatestFillings(String html, FillingRecord latestFillingRecord) {
        List<FillingRecord> fillingRecordList = new ArrayList<>();
        Document htmlDocument = Jsoup.parse(html);
        Element table = htmlDocument.getElementById("transactionTable:tb");
        table.getElementsByTag("tr").forEach(tr -> {
            FillingRecord currentFillingRecord = parseFillingRecord(tr);
            if (currentFillingRecord.getDate().getTime() > latestFillingRecord.getDate().getTime()) {
                currentFillingRecord.setWeek_id(WeekRangeDAO.getInstance()
                        .findOrCreateWeek(currentFillingRecord.getDate(), "okko_api_worker").getId());
                fillingRecordList.add(currentFillingRecord);
            }
        });

        if (fillingRecordList.size() == table.getElementsByTag("tr").size()) {
            fillingRecordList.addAll(getOldRecords());
        }

        return fillingRecordList;
    }

    private static List<FillingRecord> getOldRecords() {
        //TODO
        return new ArrayList<>();
    }

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static FillingRecord parseFillingRecord(Element tr) {
        Elements tdList = tr.getElementsByTag("td");
        String stringDate = tdList.get(0).text();
        String stringCard = tdList.get(1).text();
        String stringPrice = tdList.get(2).text().split(" ")[0];
        String stringAmount = tdList.get(3).text();
        String stringAddress = tdList.get(4).text();

        FillingRecord fillingRecord = new FillingRecord();
        try {
            fillingRecord.setDate(SDF.parse(stringDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fillingRecord.setCard(stringCard);
        fillingRecord.setAmount(Double.parseDouble(stringPrice));
        fillingRecord.setAddress(stringAddress);
        fillingRecord.setItemAmount(Double.parseDouble(stringAmount));
        fillingRecord.setCar(FuelHelper.getInstance().findOutCarIdentity(stringCard, "okko"));
        fillingRecord.setId(fillingRecord.getDate().getTime() + "");
        fillingRecord.setStation("okko");
        return fillingRecord;
    }

    private static FillingRecord getLatestFillingRecord() {
        FillingRecord fillingRecordLatest = FillingRecordDAO.getInstance().latest("okko");
        return fillingRecordLatest == null ? new FillingRecord(new Date(0)) : fillingRecordLatest;
    }
}
