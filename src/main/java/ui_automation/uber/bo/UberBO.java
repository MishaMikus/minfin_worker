package ui_automation.uber.bo;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import orm.entity.uber.payment_record_row.UberPaymentRecordRowDAO;
import server.logan_park.service.PaymentRecorder;
import ui_automation.BaseBO;
import util.IOUtils;

import java.io.File;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.driver;
import static util.IOUtils.FS;

public class UberBO extends BaseBO {
    public static final String DOWNLOAD_FOLDER = System.getProperty("user.home") + FS + "Downloads";

    private final static Logger LOGGER = Logger.getLogger(UberBO.class);

    private static final Long FILE_APPEAR_TIMEOUT_MS = 60 * 1000L;
    private static final Long ITERATION_TIMEOUT_MS = 5 * 60 * 1000L;
    private static final Long FILE_DOWNLOAD_WAITING_MS = 5 * 1000L;
    private static final Long DOWNLOAD_FILE_APPEAR_ITERATION_TIME_MS = 5 * 1000L;
    private static final Long DOWNLOAD_FILE_DISAPPEAR_ITERATION_TIME_MS = 5 * 1000L;
    public static final File PAYMENT_DATA_FILE = new File(DOWNLOAD_FOLDER + FS + "Payments.csv");

    public void recordPayment() {
        deletePaymentFile();
        goToPath("/p3/fleet-manager/payments");
        waitingForDownloadButtonAppear();
        clickDownload();
        waitingForFileDownload();
        recordPaymentFile(new Date());
        deletePaymentFile();
    }


    private void waitBeforeNextIteration() {
        LOGGER.info("WAIT BEFORE NEXT ITERATION");
        threadSleep(ITERATION_TIMEOUT_MS);
    }

    private void deletePaymentFile() {
        if (PAYMENT_DATA_FILE.exists()) {
            LOGGER.info("TRY DELETE " + PAYMENT_DATA_FILE.getAbsolutePath());
            if (PAYMENT_DATA_FILE.delete()) {
                LOGGER.info("FILE deleted " + !PAYMENT_DATA_FILE.exists());
            }
        }
    }

    private void recordPaymentFile(Date weekFlag) {
        String content = IOUtils.readTextFromFile(PAYMENT_DATA_FILE);
        new PaymentRecorder(content).recordToBD(weekFlag);
        LOGGER.info("FILE RECORDING DONE");
    }

    private void waitingForFileDownload() {
        Long start = new Date().getTime();
        while (!PAYMENT_DATA_FILE.exists() && timeout(start, FILE_APPEAR_TIMEOUT_MS)) {
            LOGGER.info("WAITING FOR FILE APPEAR");
            threadSleep(DOWNLOAD_FILE_APPEAR_ITERATION_TIME_MS);
        }
        threadSleep(FILE_DOWNLOAD_WAITING_MS);
        LOGGER.info("FILE EXISTS : " + PAYMENT_DATA_FILE.exists());
    }


    private void clickPreviousWeekButton() {
        By by = By.xpath("//img[contains(@class,'earnings-left-arrow')][@data-reactid]");
        if ($(by).isDisplayed()) {
            $(by).click();
            LOGGER.info("CLICK PREVIOUS WEEK");
        } else {
            LOGGER.warn("CLICK PREVIOUS WEEK FAILURE");
        }
    }

    private void clickDownload() {
        if ($(By.linkText("Download CSV")).isDisplayed()) {
            $(By.linkText("Download CSV")).click();
            LOGGER.info("CLICK DOWNLOAD");
        } else {
            LOGGER.warn("CLICK DOWNLOAD FAILURE");
        }
    }


    private void refreshPage() {
        driver().getWebDriver().navigate().refresh();
        LOGGER.info("refreshPage PAYMENT");
    }

    public void recordPaymentWithOneWeekHistory() {
        Date today = new Date();
        Date todayLastWeek = new Date(today.getTime() - 7 * 24 * 60 * 60 * 1000L);
        deletePaymentFile();
        goToPath("/p3/fleet-manager/payments");
        waitingForDownloadButtonAppear();
        clickDownload();
        waitingForFileDownload();
        recordPaymentFile(today);
        deletePaymentFile();
        if (needPreviousWeekData()) {
            clickPreviousWeekButton();
            waitingForDownloadButtonAppear();
            clickDownload();
            waitingForFileDownload();
            recordPaymentFile(todayLastWeek);
            deletePaymentFile();
        }
    }

    private boolean needPreviousWeekData() {
        return !UberPaymentRecordRowDAO.getInstance().findLatest().getWeek_id()
                .equals(WeekRangeDAO.getInstance().findOrCreateWeek(new Date()).getId());
    }
}
