import function.Taylor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaylorTest {
    @Test
    public void LowBorderReturnsCorrect() {
        assertEquals(-Math.PI/2, Taylor.arcsin(-1));
    }

    @Test
    public void HighBorderReturnsCorrect() {
        assertEquals(Math.PI/2, Taylor.arcsin(1));
    }

    @Test
    public void RightOutOfBoundsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Taylor.arcsin(2));
    }

    @Test
    public void LeftOutOfBoundsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Taylor.arcsin(-2));
    }
    @Test
    public void ReturnsCorrect() {
        assertEquals(Math.asin(0.33), Taylor.arcsin(0.33), Taylor.eps);
        assertEquals(Math.asin(0.88), Taylor.arcsin(0.88), Taylor.eps);
        assertEquals(Math.asin(-0.33), Taylor.arcsin(-0.33), Taylor.eps);
        assertEquals(Math.asin(-0.88), Taylor.arcsin(-0.88), Taylor.eps);
    }

    @Test
    public void YAxisInterceptionReturnsCorrect() {
        assertEquals(Math.asin(0), Taylor.arcsin(0), Taylor.eps);
    }

}
