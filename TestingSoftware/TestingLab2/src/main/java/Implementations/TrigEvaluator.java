package Implementations;

import Interfaces.ISin;
import Interfaces.ITrigEvaluator;

public class TrigEvaluator implements ITrigEvaluator {
    private ISin sin;

    public TrigEvaluator(ISin sin) {
        this.sin = sin;
    }

    @Override
    public Double sin(Double x, Double eps) {
        return sin.compute(x, eps);
    }

    @Override
    public Double cos(Double x, Double eps) {
        return sin.compute(x + Math.PI/2, eps);
    }

    @Override
    public Double tan(Double x, Double eps) {
        if (x.isNaN() || x.isInfinite() || eps.isNaN() || eps.isInfinite()) return Double.NaN;
        return sin.compute(x, eps)/sin.compute(x + Math.PI/2, eps);
    }

    @Override
    public Double cot(Double x, Double eps) {
        if (x.isNaN() || x.isInfinite() || eps.isNaN() || eps.isInfinite() ) return Double.NaN;
        return sin.compute(x + Math.PI/2, eps)/sin.compute(x, eps);
    }

    @Override
    public Double csc(Double x, Double eps) {
        if (x.isNaN() || x.isInfinite() || eps.isNaN() || eps.isInfinite()) return Double.NaN;
        return 1/sin.compute(x, eps);
    }
}
