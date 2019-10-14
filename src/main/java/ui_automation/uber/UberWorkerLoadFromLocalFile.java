package ui_automation.uber;

import org.apache.log4j.Logger;
import server.logan_park.service.PaymentRecorder;
import util.IOUtils;

import static ui_automation.uber.bo.UberBO.PAYMENT_DATA_FILE;

public class UberWorkerLoadFromLocalFile {

    private static final Logger LOGGER = Logger.getLogger(UberWorkerLoadFromLocalFile.class);

    public static void main(String[] args) {
        new UberWorkerLoadFromLocalFile().recordPaymentFile();
    }

    private void recordPaymentFile() {
        String content = IOUtils.readTextFromFile(PAYMENT_DATA_FILE);
        new PaymentRecorder(content).recordToBD();
        LOGGER.info("FILE RECORDING DONE");
    }
}
