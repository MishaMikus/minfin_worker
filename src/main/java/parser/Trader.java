package parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static parser.Parser.getCurrencyJson;
import static parser.Parser.getRateList;

public class Trader {

//    public static void main(String[] args) {
//        trade();
//    }

    private static void trade() {

        List<DayRate> dataList = null;
        try {
            dataList = getRateList(getCurrencyJson("USD")).subList(0, 119);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<DayRate> currentHistoryList = new ArrayList<>();
        int day = 0;
        System.out.println("NEWTON");
        for (DayRate dayRate : dataList) {
            double nextY = newton(makeAverageHistory(dataList, day));
            System.out.println("dayRate.av:" + dayRate.av() + "\tday: " + day + "\t nextY=" + nextY);
            day++;
        }

        System.out.println("LAGRANGE3");
        day = 0;
        for (DayRate dayRate : dataList) {
            double[] y = makeAverageHistory(dataList, day);
            double[] x = makeDigitNumberArray(y.length);
            double nextY = lagrange3(x, y, (double) x.length + 1);
            System.out.println("dayRate.av:" + dayRate.av() + "\tday: " + day + "\t nextY=" + nextY);
            day++;
        }

        //вхід
        //у нас є історія точок до сьогоднішнього дня (array<Rate>[n])
        // Rate - обєкт який скалдається з дати,середньої продажі,середньої покупки (потім можна добавити ще і масив всіх пропозицій по даті)
        //є цифра сьогоднішньої мінімальної покупки і мінімальної продажі по двох валютах minBuyUSH, minSellUSD, maxBuyUSD, maxSellUSD
        //це все інтові величини.. з часом можна ускладнити ці дані додавши масив позицій з уточненням контрагента для формування контракту в кінці виконання алгоритму
        //є два гаманці UAH і USD
        //гаманець - інт значення може приймати від 0 і до максІнт

        //алгоритм
        //вирахувати поліном (порядок(максСтепінь) берем з константи, глибину історії також з константи)
        //зробити ймовірнісний прогноз і при потребі запропонувати одну з операцій (купити долар, продати долар, нічого не робити)

    }

    static double lagrange3(double[] x, double[] y, double nextX) {
        double r = 0, ra, rb;
        for (int i = 0; i < x.length; i++) {
            ra = rb = 1;
            for (int j = 0; j < x.length; j++)
                if (i != j) {
                    ra *= nextX - x[j];
                    rb *= x[i] - x[j];
                }
            r += ra * y[i] / rb;
        }
        return r;
    }

    private static double[] makeAverageHistory(List<DayRate> dataList, int day) {
        if (day >= dataList.size()) return dataList.stream().mapToDouble(DayRate::av).toArray();
        return dataList.subList(0, day).stream().mapToDouble(DayRate::av).toArray();
    }

    static double lagrange2(double[] x, double[] y, short n, double nextX) {

        System.out.println("lagrange2(x[" + x.length + "]\ty[" + y.length + "])");
        if (y.length == 0) return 0;
        if (y.length == 1) return y[0];
        if (y.length == 2) return y[1] + (y[1] - y[0]);


        double result = 0.0;
        double h = x[1] - x[0];
        for (short i = 0; i < n; i++) {
            double p = 1.0;

            for (short j = 0; j < n; j++)
                if (i != j)
                    p *= (nextX - x[0] - h * j) / h / (i - j);

            result += p * y[i];
        }

        return result;
    }

    static double lagrange(double[] x, double[] y, short n, double nextX) {
        System.out.println("lagrange(x[" + x.length + "]\ty[" + y.length + "])");

        double result = 0.0;
        for (short i = 0; i < n; i++) {
            double p = 1.0;
            for (short j = 0; j < n; j++)
                if (j != i)
                    p *= (nextX - x[j]) / (x[i] - x[j]);

            result += p * y[i];
        }
        return result;
    }

    public static double newton(double[] y) {

        if (y.length == 0) return 0;
        if (y.length == 1) return y[0];
        if (y.length == 2) return y[1] + (y[1] - y[0]);

        int n = y.length;
        double nextX = n + 1;
        double step = 1;

        double[] x = makeDigitNumberArray(y.length);
        return newtonExt(nextX, n, x, y, step);
    }

    private static double[] makeDigitNumberArray(int length) {
        double[] x = new double[length];
        for (int i = 0; i < length; i++) {
            x[i] = i;
        }
        return x;
    }

    // x - координата, в которой необходимо вычислить значение полинома Ньютона; n - количество узлов; MasX - массив x; MasY - массив значений x; step - шаг
    public static double newtonExt(double x, int n, double[] masX, double[] masY, double step) {

        System.out.println("newton(masY[" + masY.length + "]\tmasX[" + masX.length + "])");

        double[][] mas = new double[n + 2][n + 1];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0)
                    mas[i][j] = masX[j];
                else if (i == 1)
                    mas[i][j] = masY[j];
            }
        }
        int m = n;
        for (int i = 2; i < n + 2; i++) {
            for (int j = 0; j < m; j++) {
                mas[i][j] = mas[i - 1][j + 1] - mas[i - 1][j];
            }
            m--;
        }

        double[] dy0 = new double[n + 1];

        for (int i = 0; i < n + 1; i++) {
            dy0[i] = mas[i + 1][0];
        }

        double res = dy0[0];
        double[] xn = new double[n];
        xn[0] = x - mas[0][0];

        for (int i = 1; i < n; i++) {
            double ans = xn[i - 1] * (x - mas[0][i]);
            xn[i] = ans;
            ans = 0;
        }

        int m1 = n + 1;
        int fact = 1;
        for (int i = 1; i < m1; i++) {
            fact = fact * i;
            res = res + (dy0[i] * xn[i - 1]) / (fact * Math.pow(step, i));
        }

        return res;
    }

}
