package parser.sole;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SystemOfLinearEquationsSolver {

    final static BigDecimal MINUS_BD = new BigDecimal(-1);

    private SystemOfLinearEquations systemOfLinearEquations;
    private BigDecimal[] x;
    private BigDecimal minDividor;
    private int accuracy;

    public SystemOfLinearEquationsSolver(SystemOfLinearEquations systemOfLinearEquations, int accuracy) {
        this.systemOfLinearEquations = systemOfLinearEquations;
        this.accuracy = accuracy;
        this.minDividor = new BigDecimal(1).divide(new BigDecimal(accuracy), Byte.MAX_VALUE, RoundingMode.HALF_UP);
        initX();
    }

    private void initX() {
        x = new BigDecimal[systemOfLinearEquations.size()];
        for (int i = 0; i < x.length; i++) {
            //   x[i]=new BigDecimal(0);
        }
    }


    public BigDecimal[] solve() {
        System.out.println("try to solve:");
        System.out.println(systemOfLinearEquations.view());

        int n = systemOfLinearEquations.size();
        BigDecimal[][] a = systemOfLinearEquations.getA();
        BigDecimal[] b = systemOfLinearEquations.getB();

        //make triangle
        for (int fixRowIndex = 0; fixRowIndex < n; fixRowIndex++) {
            for (int i = fixRowIndex + 1; i < n; i++) {
                //1 M = - a21 / a11
                //2 M = - a31 / a11
                //calculate m
                BigDecimal m = (a[i][fixRowIndex].divide(a[fixRowIndex][fixRowIndex], accuracy, RoundingMode.HALF_UP)).multiply(MINUS_BD);

                for (int j = 0; j < n; j++) {
                    //multiply row to m
                    a[fixRowIndex][j] = a[fixRowIndex][j].multiply(m);

                    //add row to targetRow and replace it
                    a[i][j] = a[i][j].add(a[fixRowIndex][j]);
                }
                b[i] = b[i].multiply(m);
            }
        }

        //reverse solving
        for (int rowIndex = n - 1; rowIndex >= 0; rowIndex--) {
            BigDecimal sum = new BigDecimal(0);
            for (int j = rowIndex + 1; j < n; j++) {
                sum = sum.add(a[rowIndex][j].multiply(x[j]));
            }
            x[rowIndex] = (b[rowIndex].subtract(sum)).divide(a[rowIndex][rowIndex], accuracy, RoundingMode.HALF_UP);
        }

        return x;
    }


//    public static void main(String[] args) {
//        staticSolve();
//    }

    private static void staticSolve() {

        SystemOfLinearEquations systemOfLinearEquations = new SystemOfLinearEquations();

        BigDecimal[][] a = new BigDecimal[3][3];
        a[0][0] = new BigDecimal(2);
        a[0][1] = new BigDecimal(1);
        a[0][2] = new BigDecimal(3);

        a[1][0] = new BigDecimal(2);
        a[1][1] = new BigDecimal(6);
        a[1][2] = new BigDecimal(8);

        a[2][0] = new BigDecimal(6);
        a[2][1] = new BigDecimal(8);
        a[2][2] = new BigDecimal(18);
        systemOfLinearEquations.setA(a);

        BigDecimal[] b = new BigDecimal[3];
        b[0] = new BigDecimal(1);
        b[1] = new BigDecimal(3);
        b[2] = new BigDecimal(5);
        systemOfLinearEquations.setB(b);

        SystemOfLinearEquationsSolver systemOfLinearEquationsSolver = new SystemOfLinearEquationsSolver(systemOfLinearEquations, 99);
        systemOfLinearEquationsSolver.solve();
        systemOfLinearEquationsSolver.printResult();
        systemOfLinearEquationsSolver.test();
    }

    private BigDecimal[] getX() {
        return x;
    }

    private boolean test() {
        if (x == null) {
            System.out.println("equation system not solved yet");
        } else {
            System.out.println();
            System.out.println("test:");
            BigDecimal[] testB = new BigDecimal[x.length];
            for (int rowIndex = 0; rowIndex < x.length; rowIndex++) {
                testB[rowIndex] = new BigDecimal(0);
                for (int colIndex = 0; colIndex < x.length; colIndex++) {
                    if (x[colIndex] != null) {
                        testB[rowIndex] = testB[rowIndex].add(x[colIndex].multiply(systemOfLinearEquations.getA()[rowIndex][colIndex]));
                    }
                }
                if (!equals(testB[rowIndex], systemOfLinearEquations.getB()[rowIndex])) {
                    System.out.println("equation[" + rowIndex + "]:incorrect");
                    return false;
                }
            }
        }
        System.out.println("solving correct");
        return true;
    }

    private void printResult() {
        if (x == null) {
            System.out.println("equation system not solved yet");
        } else {
            System.out.println();
            System.out.println("result:");
            for (int i = 0; i < x.length; i++) {
                System.out.println("x" + i + " = " + x[i]);
            }
        }
    }

    private BigDecimal[] double1DToBigDecimal1D(double[] x) {
        BigDecimal[] res = new BigDecimal[x.length];
        for (int i = 0; i < x.length; i++) {
            res[i] = new BigDecimal(x[i]);
        }
        return res;
    }

    private double[] bigDecimal1DToDouble1D(BigDecimal[] b) {
        double[] res = new double[b.length];
        for (int i = 0; i < b.length; i++) {
            res[i] = b[i].doubleValue();
        }
        return res;
    }

    private double[][] bigDecimal2DToDouble2D(BigDecimal[][] a) {
        double[][] res = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                res[i][j] = a[i][j].doubleValue();
            }
        }
        return res;
    }

    private boolean equals(BigDecimal a, BigDecimal b) {
        // a approximately equals b
        return a.subtract(b).abs().compareTo(minDividor) < 0;
    }
}
