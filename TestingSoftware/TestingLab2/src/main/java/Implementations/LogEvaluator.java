package Implementations;

import Interfaces.ILn;


public class LogEvaluator implements Interfaces.ILogEvaluator {
    private ILn ln;

    public LogEvaluator(ILn ln) {
        this.ln = ln;
    }

    @Override
    public Double log(Double x, Double base, Double eps) {
        if (x.isNaN() || x.isInfinite()
                || eps.isNaN() || eps.isInfinite()
                || base.isNaN() || base.isInfinite()) return Double.NaN;
        else return ln.compute(x, eps / 100) / ln.compute(base, eps / 100);
    }

    @Override
    public Double ln(Double x, Double eps) {
        return ln.compute(x, eps);
    }
}
