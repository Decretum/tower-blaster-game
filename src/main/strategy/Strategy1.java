package strategy;



public class Strategy1 implements Strategy {

    // For any block (either discard or deck), compare it with the hand blocks in ascending order. Any time we find a
    // block that this is smaller than, we replace that block.
    // Only use the deck block if the discard block was bigger than all elements in the hand.
    // Special case: if a hand block is /too/ small, replace it with whatever block we have on hand.
    // /too/ small defined as being less than index * 5

    // Honor system: No checking the deck block value until you have committed to not using the discard block value.

    // 100% success rate, 27 turns to win

    public Strategy1() {}

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
        int ratio = 5;
        for (int x = 0; x < hand.length; x++) {
            // Special case: block too small check
            if (hand[x] < x * ratio) {
                if (block >= x * ratio) {
                    return x;
                }
            }

            // Compare the current block to the current hand block.
            if (block < hand[x]) {
                if (block >= x * ratio) {
                    return x;
                }
            }
        }
        return -1;
    }
}
