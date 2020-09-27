package Domain;

public interface ICar {
    void startEngine();
    void stopEngine();
    void changeGear(int gear);
    void overtake();
    EngineStatus checkEngine();
    int checkGear();
    IPerson getDriver();
}
