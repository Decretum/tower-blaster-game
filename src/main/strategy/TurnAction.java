package strategy;

public class TurnAction {

    public enum Usage {
        DISCARD,
        DECK,
        NONE
    }

    private final Usage usage;
    private final int replacePosition;

    private TurnAction(Usage usage, int replacePosition) {
        this.usage = usage;
        this.replacePosition = replacePosition;
    }

    public Usage getUsage() {
        return usage;
    }

    public int getReplacePosition() {
        return replacePosition;
    }

    public static TurnAction of(Usage usage, int replacePosition) {
        return new TurnAction(usage, replacePosition);
    }

}
