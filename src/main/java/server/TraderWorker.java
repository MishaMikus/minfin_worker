package server;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import ui_automation.Trader;

import javax.annotation.PostConstruct;

@Component
public class TraderWorker {
    private static final Long TIME_DELTA = 1000L * 60 * 1;//each 16 minutes
    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

    @PostConstruct
    private void execute() {
        scheduler.scheduleAtFixedRate(Trader::sell, TIME_DELTA);
    }
}
