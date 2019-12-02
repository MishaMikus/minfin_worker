package ui_automation.okko;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import orm.entity.logan_park.fuel_account_leftover.FuelAccountLeftover;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import orm.entity.logan_park.filling.FillingRecord;
import ui_automation.common.FuelHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

public class OkkoBo extends BaseOkkoBO {
    private final static Logger LOGGER = Logger.getLogger(OkkoBo.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<FillingRecord> getAllLatestFillings(FillingRecord latestDBRecord) {
        List<FillingRecord> res = new ArrayList<>();
        boolean duplicated = false;
        openDetailedList();
        for (String pageIndex : parseAllPagesList()) {
            By currentPageLinkLocator = By.xpath("//table[contains(@id,'UserForm:transTable:')]//tr/td[text()=" + pageIndex + "]");
            $(currentPageLinkLocator).click();
            List<String> allFillingLinksText = parseAllFillingLinksText();
            LOGGER.info("PAGE " + (Integer.parseInt(pageIndex)));
            for (String fillingLinkText : allFillingLinksText) {
                $(currentPageLinkLocator).click();
                FillingRecord parsedFilling = parseFilling(fillingLinkText);
                if (latestDBRecord.getDate().getTime() < parsedFilling.getDate().getTime()) {
                    parsedFilling.setWeek_id(WeekRangeDAO.getInstance().findOrCreateWeek(parsedFilling.getDate(), "okko_worker").getId());
                    //TODO viber notification
                    res.add(parsedFilling);
                } else {
                    duplicated = true;
                }
                if (duplicated) return res;
            }
        }
        close();
        return res;
    }

    private FillingRecord parseFilling(String fillingLinkText) {
        LOGGER.info("try get info : " + fillingLinkText);
        $(By.linkText(fillingLinkText)).click();

        FillingRecord fillingRecord = new FillingRecord();
        List<String> valueList = parseFillingValueList();
        try {
            fillingRecord.setDate(SDF.parse(valueList.get(0)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fillingRecord.setCard(valueList.get(1));
        fillingRecord.setAmount(Double.parseDouble(valueList.get(2)));
        fillingRecord.setDiscount(Double.parseDouble(valueList.get(3)));
        fillingRecord.setAmountAndDiscount(Double.parseDouble(valueList.get(4)));
        fillingRecord.setSapCode(valueList.get(5));
        fillingRecord.setShop(valueList.get(6));
        fillingRecord.setAddress(valueList.get(7));
        fillingRecord.setPrice(Double.parseDouble($(By.xpath("//td[contains(@id,'transactionDetail:transGoods:')][3]")).text()));
        fillingRecord.setItemAmount(Double.parseDouble($(By.xpath("//td[contains(@id,'transactionDetail:transGoods:')][2]")).text()));
        fillingRecord.setCar(FuelHelper.getInstance().findOutCarIdentity(fillingRecord.getCard(), "okko"));
        fillingRecord.setId(fillingRecord.getDate().getTime() + "");
        fillingRecord.setStation("okko");
        driver().getWebDriver().navigate().back();
        LOGGER.info("fillingRecord: " + fillingRecord);
        return fillingRecord;
    }


    private void openDetailedList() {
        $(By.id("refresh")).findAll(By.tagName("a")).get(1).click();
    }

    private List<String> parseFillingValueList() {
        List<String> valueList = new ArrayList<>();
        String source = $(By.xpath("//form[@id='transactionDetail']")).innerHtml();
        String[] split = source.split("<span class=\"bold\">");
        for (String s : new ArrayList<>(Arrays.asList(split).subList(1, split.length))) {
            s = s.split("</span>")[0];
            valueList.add(s);
        }
        return valueList;
    }

    private List<String> parseAllFillingLinksText() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> res = new ArrayList<>();
        String source = $(By.xpath("//table[@id='UserForm:transTable']")).innerHtml();
        String[] split = source.split("<a href=\"#\"");
        for (String s : new ArrayList<>(Arrays.asList(split).subList(1, split.length))) {
            if (!res.contains(s)) {
                res.add(s.split("</a>")[0].split("false;\">")[1]);
            }
        }
        return res;
    }

    private List<String> parseAllPagesList() {
        List<String> res = new ArrayList<>();
        String source = $(By.xpath("//table[contains(@id,'UserForm:transTable:')]")).innerHtml();
        new ArrayList<>(Arrays.asList(source.split("</td>"))).forEach(i -> {
            i = i.split(">").length > 1 ? i.split(">")[1] : "";
            if (isNumber(i)) {
                res.add(i);
            }
        });
        return res;
    }

    private boolean isNumber(String i) {
        try {
            return Integer.parseInt(i) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void setupFilter() {
        $$(By.className("rich-calendar-input ")).get(0).click();
        $(By.className("rich-calendar-header")).find(By.className("rich-calendar-month")).click();
        $(By.xpath("//div[text()=2018]")).click();
        $(By.xpath("//span[text()='OK']")).click();
        $(By.xpath("//td[text()=12]")).click();
        $(By.id("UserForm:dataFilter-searchButton")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FuelAccountLeftover findLeftover() {
        FuelAccountLeftover fuelAccountLeftover = new FuelAccountLeftover();
        fuelAccountLeftover.setDate(new Date());
        fuelAccountLeftover.setStation("okko");
        fuelAccountLeftover.setValue(Double.parseDouble($(By.xpath("//tr[@class='accounts']/td[4]")).text().split(" ")[0]));
        return fuelAccountLeftover;
    }
}
