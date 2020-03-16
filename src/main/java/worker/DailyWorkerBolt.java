package worker;


import ui_automation.bolt.BoltWorker;
import ui_automation.uber.UberWorkerLoadHistory;

public class DailyWorkerBolt {
    public static void main(String[] args) {
        runWorker();
    }

    public static void runWorker() {
        BoltWorker.runWorker();
       //UberWorkerLoadHistory.runWorker();
        //Pinger.runWorker();
    }
}
