package ui_automation.uber.bo;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import server.logan_park.service.PaymentRecorder;
import ui_automation.bo.BaseBO;
import util.IOUtils;

import java.io.File;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.driver;
import static util.IOUtils.FS;

public class UberBO extends BaseBO {
    private static final String DOWNLOAD_FOLDER = System.getProperty("user.home") + FS + "Downloads";

    private final static Logger LOGGER = Logger.getLogger(UberBO.class);

    private static final Long FILE_APPEAR_TIMEOUT_MS = 60 * 1000L;
    private static final Long ITERATION_TIMEOUT_MS = 5 * 60 * 1000L;
    private static final Long FILE_DOWNLOAD_WAITING_MS = 5 * 1000L;
    private static final Long DOWNLOAD_FILE_APPEAR_ITERATION_TIME_MS = 5 * 1000L;
    private static final Long DOWNLOAD_FILE_DISAPPEAR_ITERATION_TIME_MS = 5 * 1000L;
    public static final File PAYMENT_DATA_FILE = new File(DOWNLOAD_FOLDER + FS + "Payments.csv");

    public void recordPayment() {
        deletePaymentFile();
        //wait for first default page loaded
        //goToPath("p3/payments/statements");//start page
        goToPath("/p3/fleet-manager/payments");
        waitingForDownloadButtonAppear();
        clickDownload();
        waitingForFileDownload();
        recordPaymentFile();
        deletePaymentFile();
    }


    private void waitBeforeNextIteration() {
        LOGGER.info("WAIT BEFORE NEXT ITERATION");
        threadSleep(ITERATION_TIMEOUT_MS);
    }

    private void deletePaymentFile() {
        //  Long startMs = new Date().getTime();
        if (PAYMENT_DATA_FILE.exists()) {
            LOGGER.info("TRY DELETE " + PAYMENT_DATA_FILE.getAbsolutePath());
            //IOUtils.deleteFile(PAYMENT_DATA_FILE.getAbsolutePath());
            if (PAYMENT_DATA_FILE.delete()) {
                LOGGER.info("FILE deleted " + !PAYMENT_DATA_FILE.exists());
            }
        }
//        while (PAYMENT_DATA_FILE.exists() && timeout(startMs, TIMEOUT_MS)) {
//            LOGGER.info("WAITING for file deleting");
//            threadSleep(DOWNLOAD_FILE_DISAPPEAR_ITERATION_TIME_MS);
//        }
//        LOGGER.info("FILE deleted " + !PAYMENT_DATA_FILE.exists());
    }

    private void recordPaymentFile() {
        String content = IOUtils.readTextFromFile(PAYMENT_DATA_FILE);
        new PaymentRecorder(content).recordToBD();
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

}
