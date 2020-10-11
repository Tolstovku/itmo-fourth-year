package Implementations;

import Interfaces.ISin;

import static java.lang.Math.abs;

public class Sin implements ISin {
    @Override
    public Double compute(Double x, Double eps) {

        if (x.isNaN() || x.isInfinite() || eps.isNaN() || eps.isInfinite()) return Double.NaN;
        else return s(x, x, eps, 1.0, 0.0);
    }

    private Double s(Double x, Double cur, Double eps, Double n, Double res) {
        if (abs(cur) < eps) return res;
        else return s(x, cur * (-x * x / (2.0 * n * (2.0 * n + 1.0))), eps, n + 1, res + cur);
    }
}
