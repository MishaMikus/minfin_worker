package parser;

import util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static parser.Parser.*;

public class UsdUahDataCollector {

//    public static void main(String[] args) {
//        usdUahDataCollector();
//    }

    private static void usdUahDataCollector() {
        //file format
        //1 date
        //2 usd sell
        //3 usd buy
        //sort by date
        String[] text = new String[1];
        text[0] = "date\tbuy\tsell";
        try {
            getRateList(getCurrencyJson("USD")).forEach(c -> {
                        String row = SDF.format(c.getDate()) + "\t" + c.getBuy() + "\t" + c.getSell();
                        text[0] += row + "\n";
                    }
            );

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.saveTextToFile(new File("usd_history.csv"), text[0]);
    }
}
