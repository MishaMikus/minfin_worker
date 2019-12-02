package ui_automation.bolt;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import util.IOUtils;

import java.io.File;
import java.text.ParseException;
import java.util.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.driver;
import static ui_automation.bolt.RecordHelper.SDF_DATE;
import static ui_automation.uber.bo.UberBO.DOWNLOAD_FOLDER;
import static util.IOUtils.FS;

public class DayReportBO extends BaseBoltBO {
    private final static Logger LOGGER = Logger.getLogger(DayReportBO.class);

    public Map<String, File> downloadAllNewCSV(Date latestDate) {
        $(By.xpath("//*[text()='Daily Reports' or text()='Щоденні звіти']")).click();
        //company/26068/reports/daily/07.10.2019
        List<String> hrefList = new ArrayList<>();
        $$(By.xpath("//a[text()='CSV']")).forEach(e -> hrefList.add(e.getAttribute("href")));
        Map<String, File> res = new HashMap<>();
        for (String href : hrefList) {
            String date = href.split("/")[7];
            Date currentDate = null;
            try {
                currentDate = SDF_DATE.parse(date);
            } catch (ParseException e) {
                LOGGER.info("invalid href date for pattern : " + SDF_DATE + " for date : " + date + "\n" + e.getMessage());
            }
            if (latestDate == null || (currentDate != null && currentDate.getTime() > latestDate.getTime())) {
                $(By.xpath("//*[text()='" + date + "']/following-sibling::td//a")).click();
                res.put(date, newDownloadFile(date));
            }
        }
        return res;
    }

    private File newDownloadFile(String date) {
        long start = new Date().getTime();
        long pingTime = 1000L;
        long timeout = 5 * pingTime;
        File file = new File(DOWNLOAD_FOLDER + FS + "/Щоденний звіт Bolt – " + date + " – Lviv Fleet 02_28 park Mikus.csv");
        while (!file.exists() && new Date().getTime() - start < timeout) {
            try {
                Thread.sleep(pingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (file.exists()) {
            LOGGER.info("file : " + file.getAbsolutePath() + " " + (file.exists() ? "EXISTS" : "NOT EXISTS"));
            return file;
        } else
            return new File(DOWNLOAD_FOLDER + FS + "Bolt Daily Report - " + date + " - Lviv Fleet 02_28 park Mikus.csv");

    }

    public File downloadMonthTripCsv() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String pSource = monthTripPageSource();
        String month = null;
        try {
            month = pSource
                    .split("data-test-select-picker")[2]
                    .split(">")[1]
                    .split("<")[0]
                    .trim()
                    .split(" ")[0];
        } catch (Exception e) {
            e.printStackTrace();
            IOUtils.saveTextToFile(new File("pSource.html"), pSource);
            LOGGER.info("can't get month from page source: "+pSource);
        }
        $(By.xpath("//a[text()=\"Завантажити CSV-файл\"]")).click();
        LOGGER.info("month : " + month);
        return new File(DOWNLOAD_FOLDER + FS + "Bolt - Рахунки пасажирів - " + month + " 2019.csv");
    }

    public String monthTripPageSource() {
        $(By.xpath("//*[contains(@href,\"/invoices/rider-invoices\")]")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver().getWebDriver().getPageSource();
    }
}
