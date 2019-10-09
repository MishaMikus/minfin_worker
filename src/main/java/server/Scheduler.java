package server;

import org.apache.log4j.Logger;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import ui_automation.minfin.Trader;
import ui_automation.okko.OkkoWorker;
import ui_automation.uber.UberWeeklyReportWorker;
import util.ApplicationPropertyUtil;

import javax.annotation.PostConstruct;

@Component
public class Scheduler {
    private static final Long TIME_DELTA = 1000L * 60L;//each minute
    private static final boolean TRADING_MODE = ApplicationPropertyUtil.getBoolean("trading_mode", false);
    private static final boolean UBER_MODE = ApplicationPropertyUtil.getBoolean("uber_mode", false);
    private static final boolean OKKO_MODE = ApplicationPropertyUtil.getBoolean("okko_mode", false);
    private static final boolean BOLT_MODE = ApplicationPropertyUtil.getBoolean("bolt_mode", false);

    private TaskScheduler schedulerTrader = new ConcurrentTaskScheduler();
    private TaskScheduler schedulerUber = new ConcurrentTaskScheduler();
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @PostConstruct
    private void execute() {
        if (TRADING_MODE) {
            schedulerTrader.scheduleAtFixedRate(Trader::trade, TIME_DELTA);
            LOGGER.info("TRADING_MODE scheduled");
        }

        if (UBER_MODE) {
            schedulerUber.scheduleAtFixedRate(UberWeeklyReportWorker::stat, TIME_DELTA);
            LOGGER.info("UBER_MODE scheduled");
        }

        if (OKKO_MODE) {
            schedulerUber.scheduleAtFixedRate(OkkoWorker::runWorker, TIME_DELTA*2);
            LOGGER.info("OKKO_MODE scheduled");
        }

        if (BOLT_MODE) {
            schedulerUber.scheduleAtFixedRate(OkkoWorker::runWorker, TIME_DELTA*2);
            LOGGER.info("BOLT_MODE scheduled");
        }

    }
}
