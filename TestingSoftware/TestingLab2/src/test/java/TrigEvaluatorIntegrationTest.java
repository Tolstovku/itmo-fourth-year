import Implementations.Sin;
import Implementations.TrigEvaluator;
import Interfaces.ITrigEvaluator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static Constants.Constants.eps;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrigEvaluatorIntegrationTest {
    public static ITrigEvaluator trigEval;

    @BeforeAll
    public static void prepare(){
        trigEval = new TrigEvaluator(new Sin());
    }


    @ParameterizedTest
    @CsvSource({
            "0,1.000000",
            "0.8,0.696707",
            "1.570796,0.000000",
            "2.3,-0.666276",
            "3.141592,-1.000000",
            "3.9,-0.725932",
            "4.712388,-0.000001",
            "5.5,0.708670",
            "0.4,0.921061",
            "1.1854,0.375926",
            "1.9354,-0.356579",
            "3.1,-0.999135",
            "3.5208,-0.928958",
            "4.3208,-0.381658",
            "5.10619,0.383701",
    })
    public void cosCorrect(Double x, Double expected){
        assertEquals(expected, trigEval.cos(x, eps), eps);
    }

    @ParameterizedTest
    @CsvSource({
            "0,0.000000",
            "0.8,1.029639",
            "2.3,-1.119214",
            "3.9,0.947425",
            "5.5,-0.995584",
            "0.4,0.422793",
            "1.1854,2.464976",
            "1.9354,-2.620079",
            "3.1,-0.041617",
            "3.5208,0.398494",
            "4.3208,2.421814",
            "5.10619,-2.406709 ",
    })
    public void tanCorrect(Double x, Double expected){
        assertEquals(expected, trigEval.tan(x, eps/10), eps);
    }

    @ParameterizedTest
    @CsvSource({
            "0.8,0.971215",
            "1.570796,0.000000",
            "2.3,-0.893484",
            "3.9,1.055493",
            "4.712388,0.000001",
            "5.5,-1.004436",
            "0.4,2.365222",
            "1.1854,0.405684",
            "1.9354,-0.381668",
            "3.5208,2.509449",
            "4.3208,0.412914",
            "5.10619,-0.415505",
    })
    public void cotCorrect(Double x, Double expected){
        assertEquals(expected, trigEval.cot(x, eps/10), eps);
    }

    @ParameterizedTest
    @CsvSource({
            "0.8,1.394008",
            "1.570796,1.000000",
            "2.3,1.341012",
            "3.9,-1.453983",
            "4.712388,-1.000000",
            "5.5,-1.417353",
            "0.4,2.567932",
            "1.1854,1.079157",
            "1.9354,1.070360",
            "3.5208,-2.701358",
            "4.3208,-1.081895",
            "5.10619,-1.082887",
    })
    public void cscCorrect(Double x, Double expected){
        assertEquals(expected, trigEval.csc(x, eps/10), eps);
    }
}
