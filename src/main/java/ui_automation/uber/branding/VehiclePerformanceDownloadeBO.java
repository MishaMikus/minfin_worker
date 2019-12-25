package ui_automation.uber.branding;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static ui_automation.uber.bo.UberLoginBO.login;

public class VehiclePerformanceDownloadeBO {
    public static void download() throws InterruptedException {
        login();
        gotoPerformancePage();
        selectVehicleSort();
        List<Integer> dayListReactId = getCurrentMonthDayList();
        for (Integer dayReactId : dayListReactId) {
            uploadDay(dayReactId);
            Thread.sleep(10000);
        }
    }

    private static void uploadDay(Integer dayReactId) {
        $(By.xpath("//span[@data-reactid=\"dayReactId\"]")).click();
        //TODO
    }

    private static List<Integer> getCurrentMonthDayList() {
        $(By.xpath("//div[@class=\"cursor--pointer primary-font--semibold push-tiny--left\"][1]")).click();
        $(By.xpath("//*[@class=\"datepicker__cal flush\"][1]")).findElements(By.xpath("//td/span")).forEach(e-> System.out.println(e.getText()));
        return new ArrayList<>();
    }

    private static void selectVehicleSort() {
        $(By.xpath("//span[text()=\"View by drivers\"]")).click();
        $(By.xpath("//td[text()=\"View by vehicles\"]")).click();
    }

    private static void gotoPerformancePage() {
        $(By.xpath("//i[@class=\"icon icon_menu\"]")).click();
        $(By.xpath("//a[@href=\"/p3/fleet-manager\"]")).click();
        $(By.xpath("//a[@href=\"/p3/fleet-manager/performance-xp\"]")).click();
    }
}
