package Domain;

public class Person implements IPerson{
    private Mood mood = Mood.BORED;
    private BrainStatus brain = BrainStatus.STABLE;

    @Override
    public Mood getCurrentMood() {
        return mood;
    }

    @Override
    public void brainBlow() {
        brain = BrainStatus.BLOWN;
        mood = Mood.SHOCKED;
    }

    @Override
    public void reactToOvertake() {
        mood = Mood.GLAD;
    }

    @Override
    public BrainStatus getBrainStatus() {
        return brain;
    }
}
