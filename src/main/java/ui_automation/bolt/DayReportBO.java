package ui_automation.bolt;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.io.File;
import java.util.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ui_automation.uber.bo.UberBO.DOWNLOAD_FOLDER;
import static util.IOUtils.FS;

public class DayReportBO extends BaseBoltBO {
    private final static Logger LOGGER = Logger.getLogger(DayReportBO.class);

    public Map<String, File> downloadAllCSV() {
        $(By.xpath("//*[text()='Щоденні звіти' or text()='Daily Reports']")).click();
        //company/26068/reports/daily/07.10.2019
        List<String> hrefList = new ArrayList<>();
        $$(By.xpath("//a[text()='CSV']")).forEach(e -> hrefList.add(e.getAttribute("href")));
        Map<String, File> res = new HashMap<>();
        for (String href : hrefList) {
            String date = href.split("/")[7];
            $(By.xpath("//*[text()='" + date + "']/following-sibling::td//a")).click();
            res.put(date, newDownloadFile(date));
        }
        return res;
    }

    private File newDownloadFile(String date) {
        long start = new Date().getTime();
        long pingTime = 1000L;
        long timeout = 10 * pingTime;
        File file = new File(DOWNLOAD_FOLDER + FS + "Щоденний звіт Bolt – " + date + " – Lviv Fleet 02_28 park Mikus.csv");
        while (!file.exists() && new Date().getTime() - start < timeout) {
            try {
                Thread.sleep(pingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("file : " + file.getAbsolutePath() + " " + (file.exists() ? "EXISTS" : "NOT EXISTS"));
        return file;
    }
}
