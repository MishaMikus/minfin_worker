package worker;


import ui_automation.bolt.BoltWorker;

public class DailyWorkerBolt {
    public static void main(String[] args) {
        runWorker();
    }

    public static void runWorker() {
        try {
            BoltWorker.runWorker();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //UberWorkerLoadHistory.runWorker();
        //Pinger.runWorker();
    }
}
