package ui_automation.uber;

import org.apache.log4j.Logger;
import server.logan_park.service.PaymentRecordRawRow;
import server.logan_park.service.PaymentRecorder;
import util.IOUtils;

import java.io.File;
import java.util.Date;

public class UberWorkerLoadFromLocalFile {

    private static final Logger LOGGER = Logger.getLogger(UberWorkerLoadFromLocalFile.class);


    private void recordPaymentFile() {
        for (File file : new File("F:/").listFiles()) {
            if (!file.isDirectory()) {
                try {

                    String content = IOUtils.readTextFromFile(file);
                    new PaymentRecorder(content).recordToBD(parseFirstRowDate(content));
                    LOGGER.info("FILE RECORDING DONE");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Date parseFirstRowDate(String content) {
        return PaymentRecordRawRow.makeMeFromStringRow(content.split("\n")[5], 0).getTimestamp();
    }
}
