package parser.sole;

import java.math.BigDecimal;

public class SystemOfLinearEquations {
//    a11x1 + a12x2 + a13x3 + ... a1NxN = b1
//    a21x1 + a22x2 + a23x3 + ... a2NxN = b2
//    a31x1 + a32x2 + a33x3 + ... a3NxN = b3
//...
//    aN1x1 + aN2x2 + aN3x3 + ... aNNxN = bN

    private BigDecimal[][] a;
    private BigDecimal[] b;

    public BigDecimal[][] getA() {
        return a;
    }

    public void setA(BigDecimal[][] a) {
        this.a = a;
    }

    public BigDecimal[] getB() {
        return b;
    }

    public void setB(BigDecimal[] b) {
        this.b = b;
    }

    public int size() {
        return b.length;
    }

    public String view() {
        String view = "";
        for (int rowIndex = 0; rowIndex < a.length; rowIndex++) {
            String row = "";
            int rowLength = a[a[0].length - 1].length;
            for (int colIndex = 0; colIndex < rowLength; colIndex++) {
                BigDecimal c = a[rowIndex][colIndex];
                row += c + "*x" + colIndex;
                if (colIndex < (rowLength - 1)) {
                    row += " + ";
                }
            }
            view += row + " = " + b[rowIndex];
            if (rowIndex < (a.length - 1)) {
                view += "\n";
            }
        }
        return view;
    }
}
