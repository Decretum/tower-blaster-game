package strategy;

public class Strategy2 implements Strategy {

    // Similar to main.strategy 1, but we first check if the block is > 30, and then apply the main.strategy from the other end
    // if it is.

    // Honor system: No checking the deck block value until you have committed to not using the discard block value.

    // 83% success rate, 17 turns to win

    public Strategy2() {}

    private static final int RATIO = 5;

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
        if (block <= 30) {
            return applyBlockFromLeft(block, hand);
        } else {
            return applyBlockFromRight(block, hand);
        }
    }

    private int applyBlockFromLeft(int block, int[] hand) {
        for (int x = 0; x < hand.length; x++) {
            // Special case: block too small check
            if (hand[x] < x * RATIO) {
                if (block >= x * RATIO) {
                    return x;
                }
            }

            // Compare the current block to the current hand block.
            if (block < hand[x]) {
                if (block >= x * RATIO) {
                    return x;
                }
            }
        }
        return -1;
    }

    private int applyBlockFromRight(int block, int[] hand) {
        for (int x = hand.length - 1; x >= 0; x--) {
            // Special case: block too big check
            if (hand[x] > (60 - (9 - x) * RATIO)) {
                if (block <= (60 - (9 - x) * RATIO)) {
                    return x;
                }
            }

            // Compare the current block to the current hand block.
            if (block > hand[x]) {
                if (block <= (60 - (9 - x) * RATIO)) {
                    return x;
                }
            }
        }
        return -1;
    }
}
