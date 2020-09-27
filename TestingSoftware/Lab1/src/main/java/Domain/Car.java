package Domain;

import Domain.Exceptions.CannotOvertakeException;
import Domain.Exceptions.EngineDiedException;

import java.sql.Driver;

public class Car implements ICar{
    public IPerson driver;
    private EngineStatus engineStatus = EngineStatus.OFF;
    private int currentGear = 0;

    public Car(IPerson driver) {
        this.driver = driver;
    }

    @Override
    public void startEngine() {
        if (engineStatus == EngineStatus.WORKING) {
            return;
        }

        if (engineStatus == EngineStatus.BROKEN){
            throw new EngineDiedException("Engine is broken");
        }

        if (currentGear > 0){
            throw new EngineDiedException("You tried to start engine with not neutral gear selected");
        }
        engineStatus = EngineStatus.WORKING;
    }

    @Override
    public void stopEngine(){
        engineStatus = EngineStatus.OFF;
    }

    @Override
    public void changeGear(int gear) {
        if (gear > 5 || gear < 0){
            throw new IllegalArgumentException("Gear can be 0-5");
        }
        if (gear == 0){
            currentGear = gear;
        } else if (Math.abs(currentGear - gear) > 1 && engineStatus == EngineStatus.WORKING) {
            engineStatus = EngineStatus.BROKEN;
            driver.brainBlow();
        } else {
            currentGear = gear;
        }
    }

    @Override
    public void overtake() {
        if (engineStatus == EngineStatus.WORKING && currentGear > 0)
            driver.reactToOvertake();
        else throw new CannotOvertakeException("Cannot overtake while standing");
    }

    @Override
    public EngineStatus checkEngine() {
        return engineStatus;
    }

    @Override
    public int checkGear() {
        return currentGear;
    }

    @Override
    public IPerson getDriver() {
        return driver;
    }
}
