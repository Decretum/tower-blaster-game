package strategy;

public interface Strategy {

    TurnAction decideAction(int discardBlock, int deckBlock, int[] hand);
}
