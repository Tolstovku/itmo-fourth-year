import Implementations.FuncEvaluator;
import Interfaces.IFuncEvaluator;
import Interfaces.ILogEvaluator;
import Interfaces.ITrigEvaluator;
import org.junit.jupiter.api.Test;

import static Constants.Constants.eps;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FuncEvaluatorTest {

    @Test
    public void positiveAreaCheck(){
        ILogEvaluator logEvalMock = mock(ILogEvaluator.class);
        when(logEvalMock.log(313.0, 2.0, eps)).thenReturn(1.0);
        when(logEvalMock.log(313.0, 3.0, eps)).thenReturn(1.0);
        when(logEvalMock.log(313.0, 5.0, eps)).thenReturn(1.0);
        when(logEvalMock.log(313.0, 10.0, eps)).thenReturn(1.0);
        when(logEvalMock.ln(313.0,  eps)).thenReturn(1.0);
        ITrigEvaluator trigEvalMock = mock(ITrigEvaluator.class);
        IFuncEvaluator funcEval = new FuncEvaluator(logEvalMock, trigEvalMock);

        funcEval.compute(313.0, eps);

        verify(logEvalMock, times(3)).ln(313.0,  eps);
        verify(logEvalMock, times(1)).log(313.0, 2.0,  eps);
        verify(logEvalMock, times(3)).log(313.0, 3.0,  eps);
        verify(logEvalMock, times(1)).log(313.0, 5.0,  eps);
        verify(logEvalMock, times(1)).log(313.0, 10.0, eps);
    }

    @Test
    public void negativeAreaCheck(){
        ITrigEvaluator trigEvalMock = mock(ITrigEvaluator.class);
        when(trigEvalMock.sin(-313.0, eps)).thenReturn(-313.0);
        when(trigEvalMock.cos(-313.0, eps)).thenReturn(-313.0);
        when(trigEvalMock.csc(-313.0, eps)).thenReturn(-313.0);
        when(trigEvalMock.tan(-313.0, eps)).thenReturn(-313.0);
        when(trigEvalMock.cot(-313.0, eps)).thenReturn(-313.0);
        ILogEvaluator logEvalMock = mock(ILogEvaluator.class);
        IFuncEvaluator funcEval = new FuncEvaluator(logEvalMock, trigEvalMock);

        funcEval.compute(-313.0, eps);

        verify(trigEvalMock, times(2)).sin(-313.0, eps);
        verify(trigEvalMock, times(1)).cos(-313.0, eps);
        verify(trigEvalMock, times(1)).csc(-313.0, eps);
        verify(trigEvalMock, times(2)).tan(-313.0, eps);
        verify(trigEvalMock, times(2)).cot(-313.0, eps);
    }

    @Test
    public void validation() {
        ITrigEvaluator mockTrig = mock(ITrigEvaluator.class);
        ILogEvaluator mockLog = mock(ILogEvaluator.class);

        IFuncEvaluator f = new FuncEvaluator(mockLog, mockTrig);

        assertTrue(f.compute(Double.NaN, 1.0).isNaN());
        assertTrue(f.compute(Double.POSITIVE_INFINITY, 1.0).isNaN());
        assertTrue(f.compute(Double.NEGATIVE_INFINITY, 1.0).isNaN());
        assertTrue(f.compute(1.0, Double.NaN ).isNaN());
        assertTrue(f.compute(1.0, Double.POSITIVE_INFINITY).isNaN());
        assertTrue(f.compute(1.0, Double.NEGATIVE_INFINITY).isNaN());

        verify(mockTrig, never()).sin(anyDouble(), anyDouble());
        verify(mockTrig, never()).cos(anyDouble(), anyDouble());
        verify(mockTrig, never()).csc(anyDouble(), anyDouble());
        verify(mockTrig, never()).tan(anyDouble(), anyDouble());
        verify(mockTrig, never()).cot(anyDouble(), anyDouble());
        verify(mockLog, never()).log(anyDouble(), anyDouble(), anyDouble());
        verify(mockLog, never()).ln(anyDouble(), anyDouble());
    }

}
