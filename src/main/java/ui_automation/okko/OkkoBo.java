package ui_automation.okko;

import org.openqa.selenium.By;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.*;

public class OkkoBo extends BaseOkkoBO {
    List<FillingRecord> getAllFillings() {
        List<FillingRecord> res = new ArrayList<>();
        $(By.id("refresh")).findAll(By.tagName("a")).get(1).click();
        $$(By.className("rich-calendar-input ")).get(0).click();
        $(By.className("rich-calendar-header")).find(By.className("rich-calendar-month")).click();
        $(By.xpath("//div[text()=2018]")).click();
        $(By.xpath("//span[text()='OK']")).click();
        $(By.xpath("//td[text()=12]")).click();
        $(By.id("UserForm:dataFilter-searchButton")).click();
        By by = By.xpath("//table[contains(@id,'UserForm:transTable:')]//tr/td");
        if ($$(by).get(0).getText() != null) {
            int i = 0;
            boolean hasMorePages = true;
            while (hasMorePages) {
                System.out.println("PAGE " + ++i);
                res.addAll(parseFillingsPage(i));
                hasMorePages = $(By.xpath("//table[contains(@id,'UserForm:transTable:')]//tr/td[text()=" + i + "]")).isDisplayed();
            }
        }
        return res;
    }

    private List<FillingRecord> parseFillingsPage(int i) {
        List<FillingRecord> res = new ArrayList<>();
        if ($$(By.xpath("//td[@class='rich-table-cell']/a")).get(0).getText() != null) {
            List<String> fillingLinkList = new ArrayList<>();
            $$(By.xpath("//td[@class='rich-table-cell']/a")).forEach(e -> {
                fillingLinkList.add(e.attr("id"));
            });
            fillingLinkList.forEach(f -> {
                $(By.id(f)).click();
                res.add(parseFillingPage());
                if(i>1){
                $(By.xpath("//table[contains(@id,'UserForm:transTable:')]//tr/td[text()=" + i + "]")).click();
                }
            });

        }
        return res;
    }

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private FillingRecord parseFillingPage() {
        FillingRecord res = new FillingRecord();
        List<String> valueList = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            valueList.add($(By.xpath("//form[@id='transactionDetail']//tr[" + i + "]/td[2]")).text());
        }
        //Дата:	2019-10-02 08:37:29
        //0
        try {
            res.setDate(SDF.parse(valueList.get(0)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Номер картки:	7825390000344935
        //1
        res.setCard(valueList.get(1));

        //Сума:	262.33
        //2
        res.setAmount(Double.parseDouble(valueList.get(2)));

        //Сума знижки:	17.95
        //3
        res.setDiscount(Double.parseDouble(valueList.get(3)));

        //Сума зi знижкою:	280.28
        //4
        res.setAmountAndDiscount(Double.parseDouble(valueList.get(4)));

        //Код контракту SAP:	24ПК-8276/19
        //5
        res.setSapCode(valueList.get(5));

        //Назва АЗС:	АЗС 011 Львів ОККО-Рітейл
        //6
        res.setShop(valueList.get(6));

        //Адрес АЗС:	Львівська, Львів, Дж.Вашингтона, 12
        //7
        res.setAddress(valueList.get(7));
        System.out.println(res);

        //←
        driver().getWebDriver().navigate().back();
        return res;
    }
}
