package ui_automation.okko;

import org.openqa.selenium.By;
import orm.entity.okko.uber_okko_filling.FillingRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

public class OkkoBo extends BaseOkkoBO {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<FillingRecord> getAllLatestFillings(FillingRecord latestDBRecord) {
        List<FillingRecord> res = new ArrayList<>();
        boolean duplicated = false;
        openDetailedList();
        for (String pageIndex : parseAllPagesList()) {
            By currentPageLinkLocator = By.xpath("//table[contains(@id,'UserForm:transTable:')]//tr/td[text()=" + pageIndex + "]");
            $(currentPageLinkLocator).click();
            List<String> allFillingLinksText = parseAllFillingLinksText();
            System.out.println("PAGE " + (Integer.parseInt(pageIndex)));
            for (String fillingLinkText : allFillingLinksText) {
                $(currentPageLinkLocator).click();
                FillingRecord parsedFilling = parseFilling(fillingLinkText);
                if (latestDBRecord.getDate().getTime() < parsedFilling.getDate().getTime()) {
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

        System.out.println("try get info : " + fillingLinkText);
        $(By.linkText(fillingLinkText)).click();

        FillingRecord fillingRecord = new FillingRecord();
        List<String> valueList = parseFillingValueList();
        //Дата:	2019-10-02 08:37:29
        //0
        try {
            fillingRecord.setDate(SDF.parse(valueList.get(0)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Номер картки:	7825390000344935
        //1
        fillingRecord.setCard(valueList.get(1));

        //Сума:	262.33
        //2
        fillingRecord.setAmount(Double.parseDouble(valueList.get(2)));

        //Сума знижки:	17.95
        //3
        fillingRecord.setDiscount(Double.parseDouble(valueList.get(3)));

        //Сума зi знижкою:	280.28
        //4
        fillingRecord.setAmountAndDiscount(Double.parseDouble(valueList.get(4)));

        //Код контракту SAP:	24ПК-8276/19
        //5
        fillingRecord.setSapCode(valueList.get(5));

        //Назва АЗС:	АЗС 011 Львів ОККО-Рітейл
        //6
        fillingRecord.setShop(valueList.get(6));

        //Адрес АЗС:	Львівська, Львів, Дж.Вашингтона, 12
        //7
        fillingRecord.setAddress(valueList.get(7));

        //<td class="rich-table-cell " id="transactionDetail:transGoods:0:j_id109">12.49</td>
        fillingRecord.setPrice(Double.parseDouble($(By.xpath("//td[contains(@id,'transactionDetail:transGoods:')][3]")).text()));

        //<td class="rich-table-cell " id="transactionDetail:transGoods:0:j_id107">27.17</td>
        fillingRecord.setItemAmount(Double.parseDouble($(By.xpath("//td[contains(@id,'transactionDetail:transGoods:')][2]")).text()));

        fillingRecord.setCar(car(fillingRecord.getCard()));
        //←
        driver().getWebDriver().navigate().back();
        return fillingRecord;
    }

    private String car(String card) {
//        паливна картка	7825 3900 0034 4932		ОККО_1424	ЛОГАН_ВИШНЯ_ВС2646НХ
//        паливна картка	7825 3900 0034 4933		ОККО_9927	ЛОГАН_ЧЕРВОНИЙ_ВС5278ІА
//        паливна картка	7825 3900 0034 4934		ОККО_5650	ЛАНСЕР_BC7356EE
//        паливна картка	7825 3900 0034 4935		ОККО_2805	ФІЄСТА_BC6590IA
//        паливна картка	7825 3900 0034 6983		ОККО_2311	ФЮЖИН_BC9971IB
        switch (card) {
            case "7825390000344932":
                return "LOGAN_BC2646HX";
            case "7825390000344933":
                return "LODAN_RED_BC5278IA";
            case "7825390000344934":
                return "LANCER_BC7356EE";
            case "7825390000344935":
                return "FIESTA_BC6590IA";
            case "7825390000346983":
                return "FUSION_BC9971IB";
        }
        return "";
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
}
