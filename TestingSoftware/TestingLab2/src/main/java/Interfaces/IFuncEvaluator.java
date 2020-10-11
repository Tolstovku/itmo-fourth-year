package Interfaces;

import java.io.FileNotFoundException;

public interface IFuncEvaluator {
    Double compute(Double x, Double eps);
    void printCsv(String filename, Double startX, Double step, Double endX) throws FileNotFoundException;
}
