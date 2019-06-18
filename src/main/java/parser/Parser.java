package parser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Parser {

    private static final String MEDIAN_DAILY_USD_JSON_URL = "https://minfin.com.ua/data/currency/auction/usd.32.median.daily.json";
    private static final String MEDIAN_DAILY_EUR_JSON_URL = "https://minfin.com.ua/data/currency/auction/eur.32.median.daily.json";

    private static final String USER_AGENT = "PostmanRuntime/7.6.0";

    static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

//    public static void main(String[] args) throws IOException {
//        parse(args);
//    }

    private static void parse(String[] args) {

        //2018-03-06
        String date = SDF.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
        String currency = "usd";

        if (args.length > 0) {
            date = args[0];
        }

        if (args.length > 1) {
            currency = args[1].toLowerCase();
        }

        System.out.println("try get " + currency + " rate for " + date);
        JSONObject json = null;
        try {
            json = getCurrencyJson(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printRate(json, date);
        System.out.println("Press enter to exit...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printRate(JSONObject json, String date) {
        boolean found = false;
        for (String key : json.keySet()) {
            if (key.contains(date)) {
                if (json.get(key) instanceof JSONObject) {
                    JSONObject dayMedian = json.getJSONObject(key);
                    double buy = dayMedian.getDouble("b");
                    double sell = dayMedian.getDouble("s");
                    double av = (buy + sell) / 2;
                    System.out.println(key + ": buy = " + buy + " sell = " + sell + " av = " + av);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("cant find rate");
        }
    }

    static JSONObject getCurrencyJson(String currency) throws IOException {
        String url = MEDIAN_DAILY_USD_JSON_URL;
        if (currency.equals("eur")) {
            url = MEDIAN_DAILY_EUR_JSON_URL;
        }
        URLConnection uc = new URL(url).openConnection();
        uc.addRequestProperty("User-Agent", USER_AGENT);
        BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        JSONObject json = new JSONObject(br.readLine());
        br.close();
        IOUtils.saveTextToFile(new File("rate_history_" + currency + "_" + new Date().getTime() + ".json"), json.toString());
        return json;
    }

    static List<DayRate> getRateList(JSONObject json) throws ParseException {
        Map<Date, DayRate> res = new TreeMap<>();
        for (String key : json.keySet()) {
            if (json.get(key) instanceof JSONObject) {
                JSONObject dayMedian = json.getJSONObject(key);
                double buy = dayMedian.getDouble("b");
                double sell = dayMedian.getDouble("s");
                Date date = SDF.parse(key);
                res.putIfAbsent(date, new DayRate(date, buy, sell));
            }
        }
        return new ArrayList<>(res.values());
    }
}
