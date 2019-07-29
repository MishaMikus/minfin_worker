package server;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import ui_automation.minfin.Trader;
import ui_automation.uber.Uber;
import util.ApplicationPropertyUtil;

import javax.annotation.PostConstruct;

@Component
public class Scheduler {
    private static final Long TIME_DELTA = 1000L * 60L;//each minute
    private static final boolean TRADING_MODE = ApplicationPropertyUtil.getBoolean("trading_mode",false);
    private static final boolean UBER_MODE = ApplicationPropertyUtil.getBoolean("uber_mode",false);
    private TaskScheduler schedulerTrader = new ConcurrentTaskScheduler();
    private TaskScheduler schedulerUber = new ConcurrentTaskScheduler();

    @PostConstruct
    private void execute() {
        if(TRADING_MODE){ schedulerTrader.scheduleAtFixedRate(Trader::trade, TIME_DELTA);}
        if(UBER_MODE){ schedulerUber.scheduleAtFixedRate(Uber::stat, TIME_DELTA);}
    }
}
