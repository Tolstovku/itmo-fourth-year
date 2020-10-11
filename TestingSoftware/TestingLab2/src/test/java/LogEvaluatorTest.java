import Implementations.Ln;
import Implementations.LogEvaluator;
import Interfaces.ILn;
import Interfaces.ILogEvaluator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static Constants.Constants.eps;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogEvaluatorTest {
    @Test
    public void coreFunctionalityTest(){
        ILn lnMock = mock(ILn.class);
        when(lnMock.compute(0.313, eps/100)).thenReturn(0.313);
        when(lnMock.compute(0.137, eps/100)).thenReturn(0.137);

        ILogEvaluator logEvaluator = new LogEvaluator(lnMock);
        assertEquals(0.313/0.137, logEvaluator.log(0.313, 0.137, eps), eps);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0, 1E-6",
            "NaN, 0, 1E-6",
            "Infinity, 0, 1E-6",
            "-Infinity, 0, 1E-6",
            "0, 0, NaN",
            "0, 0, Infinity",
            "0, 0, -Infinity",
            "0, -1, 1E-6",
            "0, NaN, 1E-6",
            "0, Infinity, 1E-6",
            "0, -Infinity, 1E-6",
    })
    public void failingValidation(Double x, Double base, Double eps){
        ILn lnMock = mock(ILn.class);
        ILogEvaluator logEval = new LogEvaluator(lnMock);
        assertTrue(logEval.log(x, base, eps).isNaN());

    }

    @ParameterizedTest
    @CsvSource({
            "3,5,0.682606",
            "5,10,0.698970",
            "6,6,1",
            "3.13,2,1.646162"
    })
    public void LnIntegration(Double x, Double base, Double expected){
        LogEvaluator logEval = new LogEvaluator(new Ln());
        assertEquals(expected, logEval.log(x, base, eps), eps);
    }
}
