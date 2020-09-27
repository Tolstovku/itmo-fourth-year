import Domain.*;
import Domain.Exceptions.CannotOvertakeException;
import Domain.Exceptions.EngineDiedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DomainTest {
    ICar car;

    @BeforeEach
    public void prepare(){
        car = new Car(new Person());
    }

    @Test
    public void changingGearsSuccess(){
        car.startEngine();
        car.changeGear(1);
        car.changeGear(2);
        car.changeGear(3);
        car.changeGear(4);
        car.changeGear(5);
        assertEquals(EngineStatus.WORKING, car.checkEngine());
        assertEquals(5, car.checkGear());
    }

    @Test
    public void cannotStartCarWithNonNeutralGear(){
        car.changeGear(1);
        assertThrows(EngineDiedException.class, () -> car.startEngine());
    }

    @Test
    public void cannotChangeToSixthsGear(){
        car.startEngine();
        car.changeGear(1);
        car.changeGear(2);
        car.changeGear(3);
        car.changeGear(4);
        car.changeGear(5);
        assertThrows(IllegalArgumentException.class, () -> car.changeGear(6));
    }

    @Test
    public void wrongGearChangingCausesBrainBlowingAndEngineBreaking(){
        car.startEngine();
        car.changeGear(1);
        car.changeGear(5);
        assertEquals(EngineStatus.BROKEN, car.checkEngine());
        assertEquals(Mood.SHOCKED, car.getDriver().getCurrentMood());
        assertEquals(BrainStatus.BLOWN, car.getDriver().getBrainStatus());
    }

    @Test
    public void overtakingMakesDriverGlad(){
        car.startEngine();
        car.changeGear(1);
        car.overtake();
        assertEquals(Mood.GLAD, car.getDriver().getCurrentMood());
    }

    @Test
    public void cannotOvertakeWhileStandingInPlace(){
        assertThrows(CannotOvertakeException.class, () -> car.overtake());
        car.startEngine();
        assertThrows(CannotOvertakeException.class, () -> car.overtake());
    }

    @Test
    public void cannotStartBrokenEngine(){
        car.startEngine();
        car.changeGear(1);
        car.changeGear(5);
        assertThrows(EngineDiedException.class, () -> car.startEngine());
    }
}
