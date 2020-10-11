package Implementations;

import Interfaces.IFuncEvaluator;
import Interfaces.ILogEvaluator;
import Interfaces.ITrigEvaluator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FuncEvaluator implements IFuncEvaluator {
    private ILogEvaluator logEval;
    private ITrigEvaluator trigEval;

    public FuncEvaluator(ILogEvaluator logEval, ITrigEvaluator trigEval) {
        this.logEval = logEval;
        this.trigEval = trigEval;
    }

    @Override
    public Double compute(Double x, Double eps) {
        if (x.isNaN() || x.isInfinite() || eps.isNaN() || eps.isInfinite()) return Double.NaN;
        else if (x > 0) return posFunc(x, eps);
        else return negFunc(x, eps);
    }

    private Double posFunc(Double x, Double eps) {
        return ((Math.pow((((logEval.log(x, 10.0, eps) * logEval.ln(x, eps)) * (logEval.ln(x, eps) - logEval.ln(x, eps)))
                - (Math.pow(logEval.log(x, 2.0, eps), 2))),2)) * (((logEval.log(x, 3.0, eps) -
                logEval.log(x, 3.0, eps)) * logEval.log(x, 3.0, eps)) + logEval.log(x, 5.0, eps)));
    };
    private Double negFunc(Double x, Double eps) {
        return ((((Math.pow((trigEval.tan(x, eps) * trigEval.sin(x, eps)), 2)) - (trigEval.cot(x, eps) +
                trigEval.tan(x, eps))) / (trigEval.csc(x, eps) / (trigEval.sin(x, eps) + trigEval.cot(x, eps)))) +
                (Math.pow(trigEval.cos(x, eps), 2)));
    };

    public void printCsv(String filename, Double startX, Double step, Double endX) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(filename));

        pw.write("x,res\n");
        Double currX = startX;
        while (currX < endX) {
            pw.write(currX + "," + compute(currX,1E-6) + "\n");
            currX += step;
        }
        pw.close();
    }
}
