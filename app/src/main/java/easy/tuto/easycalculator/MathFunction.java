package easy.tuto.easycalculator;

import java.lang.Math;
public class MathFunction {
    public static String power(double base, double exponent) {
        if (base == 0 && exponent == 0) {
            return "Undefined";
        } else {
            double a = Math.pow(base, exponent);

            return String.valueOf(a);
        }
    }
}
