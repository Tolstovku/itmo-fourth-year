import Implementations.*;
import Interfaces.IFuncEvaluator;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        IFuncEvaluator funcEval = new FuncEvaluator(new LogEvaluator(new Ln()), new TrigEvaluator(new Sin()));
        funcEval.printCsv("result.csv", -10.0, 0.01, 10.0);
        System.out.println("Successfully printed");
    }
}
