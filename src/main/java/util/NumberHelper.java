package util;

public class NumberHelper {
    public static Double round(double v) {
        return Math.round(v * 100) / 100 + 0.0;
    }
}
