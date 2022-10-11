package strategy;

public class Strategy4 implements Strategy {

    // We divide up the 60 possible blocks into 10 groups. 1-6, 7-12, etc.
    // When comparing the blocks, we only assign a block if it is within that group, and if the current block placed
    // there is not within that group.

    // Honor system: No checking the deck block value until you have committed to not using the discard block value.

    // 100% success rate, 12 turns to win

    public Strategy4() {}

    @Override
    public TurnAction decideAction(int discardBlock, int deckBlock, int[] hand) {
        int applyBlockIndex = applyBlock(discardBlock, hand);
        if (applyBlockIndex != -1) {
            return TurnAction.of(TurnAction.Usage.DISCARD, applyBlockIndex);
        } else {
            applyBlockIndex = applyBlock(deckBlock, hand);
            if (applyBlockIndex != -1) {
                return TurnAction.of(TurnAction.Usage.DECK, applyBlockIndex);
            } else {
                return TurnAction.of(TurnAction.Usage.NONE, -1);
            }
        }
    }

    private int applyBlock(int block, int[] hand) {
        int index = (block - 1) / 6;

        // These values are inclusive
        int floor = index * 6 + 1;
        int ceiling = floor + 5;

        if (hand[index] < floor || hand[index] > ceiling) {
            return index;
        }

        return -1;
    }
}
