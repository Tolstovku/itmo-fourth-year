import Implementations.Ln;
import Implementations.Sin;
import Interfaces.ILn;
import Interfaces.ISin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static Constants.Constants.eps;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class SinTest {
    public static ISin sin;

    @BeforeAll
    public static void prepare(){
        sin = new Sin();
    }

    @ParameterizedTest
    @CsvSource({
            "1.0",
            "2.0",
            "3.0",
            "4.0",
            "5.0"
    })
    public void oxIntersection(double i) {
            assertEquals(0.0, sin.compute(Math.PI * i, eps), eps);
            assertEquals(0.0, sin.compute(-Math.PI * i, eps), eps);
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 1",
            "3.0, -1",
            "5.0, 1",
            "7.0, -1",
    })
    public void extremum(double i, double expected){
        assertEquals(expected, sin.compute(Math.PI / 2.0 * i, eps), eps);
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 0.707107",
            "5.0, -0.707107",
            "9.0, 0.707107",
            "13.0, -0.707107",
    })
    public void isPeriodic(double i, double expected) {
        assertEquals(expected, sin.compute(i * Math.PI / 4, eps/100), eps);
        assertEquals(expected, sin.compute((i + 2) * Math.PI / 4, eps/100), eps);
    }

    @ParameterizedTest
    @CsvSource({
            "0,0.00000",
            "0.8,0.71736",
            "1.570796,1.00000",
            "2.3,0.74571",
            "3.141592,0.00000",
            "3.9,-0.68777",
            "4.712388,-1.00000",
            "5.5,-0.70554",
            "0.4,0.38942",
            "1.1854,0.92665",
            "1.9354,0.93427",
            "3.1,0.04158",
            "3.5208,-0.37018",
            "4.3208,-0.92430",
            "5.10619,-0.92346",
    })
    public void returnsCorrectResult(double x, double expected){
        assertEquals(expected, sin.compute(x, eps), eps*10);
    }
}
