import Implementations.Sin;
import Implementations.TrigEvaluator;
import Interfaces.ISin;
import Interfaces.ITrigEvaluator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrigEvaluatorTest {
    //sin cos tan cot csc

    @Test
    public void sinCorrect(){
        ISin mockSin = mock(ISin.class);
        when(mockSin.compute(1337.0, 1.0)).thenReturn(1337.0);
        ITrigEvaluator trigEval = new TrigEvaluator(mockSin);

        assertEquals(1337.0, trigEval.sin(1337.0, 1.0));
    }

    @Test
    public void cosCorrect(){
        ISin mockSin = mock(ISin.class);
        when(mockSin.compute(10.0 + Math.PI / 2, 1.0)).thenReturn(313.0);
        ITrigEvaluator trigEval = new TrigEvaluator(mockSin);

        assertEquals(313.0, trigEval.cos(10.0, 1.0));
    }

    @Test
    public void tanCorrect(){
        ISin mockSin = mock(ISin.class);
        when(mockSin.compute(10.0, 1.0)).thenReturn(10.0);
        when(mockSin.compute(10.0 + Math.PI / 2, 1.0)).thenReturn(5.0);
        ITrigEvaluator trigEval = new TrigEvaluator(mockSin);

        assertEquals(2.0, trigEval.tan(10.0, 1.0));
    }

    @Test
    public void cotCorrect(){
        ISin mockSin = mock(ISin.class);
        when(mockSin.compute(10.0, 1.0)).thenReturn(10.0);
        when(mockSin.compute(10.0 + Math.PI / 2, 1.0)).thenReturn(5.0);
        ITrigEvaluator trigEval = new TrigEvaluator(mockSin);

        assertEquals(0.5, trigEval.cot(10.0, 1.0));
    }

    @Test
    public void cscCorrect(){
        ISin mockSin = mock(ISin.class);
        when(mockSin.compute(10.0, 1.0)).thenReturn(10.0);
        ITrigEvaluator trigEval = new TrigEvaluator(mockSin);

        assertEquals(0.1, trigEval.csc(10.0, 1.0));
    }



    // -------------- Validation tests --------------
    @Test
    public void sinValidation(){
        ITrigEvaluator trigEval = new TrigEvaluator(new Sin());
        assertEquals(Double.NaN, trigEval.sin(Double.NaN, 1.0));
        assertEquals(Double.NaN, trigEval.sin(Double.POSITIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.sin(Double.NEGATIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.sin(1.0, Double.NaN));
        assertEquals(Double.NaN, trigEval.sin(1.0, Double.POSITIVE_INFINITY));
        assertEquals(Double.NaN, trigEval.sin(1.0, Double.NEGATIVE_INFINITY));
    }

    @Test
    public void cosValidation(){
        ITrigEvaluator trigEval = new TrigEvaluator(new Sin());
        assertEquals(Double.NaN, trigEval.cos(Double.NaN, 1.0));
        assertEquals(Double.NaN, trigEval.cos(Double.POSITIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.cos(Double.NEGATIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.cos(1.0, Double.NaN));
        assertEquals(Double.NaN, trigEval.cos(1.0, Double.POSITIVE_INFINITY));
        assertEquals(Double.NaN, trigEval.cos(1.0, Double.NEGATIVE_INFINITY));
    }

    @Test
    public void tanValidation(){
        ITrigEvaluator trigEval = new TrigEvaluator(new Sin());
        assertEquals(Double.NaN, trigEval.tan(Double.NaN, 1.0));
        assertEquals(Double.NaN, trigEval.tan(Double.POSITIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.tan(Double.NEGATIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.tan(1.0, Double.NaN));
        assertEquals(Double.NaN, trigEval.tan(1.0, Double.POSITIVE_INFINITY));
        assertEquals(Double.NaN, trigEval.tan(1.0, Double.NEGATIVE_INFINITY));
    }

    @Test
    public void cotValidation(){
        ITrigEvaluator trigEval = new TrigEvaluator(new Sin());
        assertEquals(Double.NaN, trigEval.cot(Double.NaN, 1.0));
        assertEquals(Double.NaN, trigEval.cot(Double.POSITIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.cot(Double.NEGATIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.cot(1.0, Double.NaN));
        assertEquals(Double.NaN, trigEval.cot(1.0, Double.POSITIVE_INFINITY));
        assertEquals(Double.NaN, trigEval.cot(1.0, Double.NEGATIVE_INFINITY));
    }

    @Test
    public void cscValidation(){
        ITrigEvaluator trigEval = new TrigEvaluator(new Sin());
        assertEquals(Double.NaN, trigEval.csc(Double.NaN, 1.0));
        assertEquals(Double.NaN, trigEval.csc(Double.POSITIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.csc(Double.NEGATIVE_INFINITY, 1.0));
        assertEquals(Double.NaN, trigEval.csc(1.0, Double.NaN));
        assertEquals(Double.NaN, trigEval.csc(1.0, Double.POSITIVE_INFINITY));
        assertEquals(Double.NaN, trigEval.csc(1.0, Double.NEGATIVE_INFINITY));
    }
}
