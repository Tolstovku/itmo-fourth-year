import Implementations.*;
import Interfaces.IFuncEvaluator;
import Interfaces.ILogEvaluator;
import Interfaces.ITrigEvaluator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import static Constants.Constants.eps;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncEvaluatorIntegrationTest {
    public static IFuncEvaluator funcEvaluator;

    @BeforeAll
    public static void prepare() {
        funcEvaluator = new FuncEvaluator(new LogEvaluator(new Ln()), new TrigEvaluator(new Sin()));
    }

    @Test
    public void oxIntersection() {
        assertEquals(0.0, funcEvaluator.compute(-11.535, eps), 0.01);
        assertEquals(0.0, funcEvaluator.compute(-10.217, eps), 0.01);
        assertEquals(0.0, funcEvaluator.compute(-5.251, eps), 0.01);
        assertEquals(0.0, funcEvaluator.compute(-3.934, eps), 0.01);
    }

    @Test
    public void extremum() {
        assertEquals(0.208, funcEvaluator.compute(-8.413, eps), 0.01);
        assertEquals(0.208, funcEvaluator.compute(-2.13, eps), 0.01);
        assertEquals(0, funcEvaluator.compute(1.0, eps), 0.01);
    }


    @ParameterizedTest
    @CsvSource({
            "0.5,-0.43068",
            "2.5,1.73856",
            "3.500000,8.30555",
            "4.500000,20.71929",
            "6.000000,49.70756",
            "7.000000,75.09964",
            "-0.500000,3.47825",
            "-2.500000,1.47719",
            "-3.500000,-1.61327",
            "-4.400000,7.33341",
            "-6.000000,-2.94111",
            "-7.000000,3.35052",
    })
    public void resultCorrect(Double x, double expected){
        assertEquals(expected, funcEvaluator.compute(x, eps/10), eps*10);
    }
}
