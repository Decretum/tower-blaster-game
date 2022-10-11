package strategy;

public class Strategy0 implements Strategy {

    // Catherine's submitted homework. Not sure how it works lol

    // Honor system: No checking the deck block value until you have committed to not using the discard block value.

    // 20% success rate, 34 turns to win

    public Strategy0() {}
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
        int brickOneIndex = -1;

        // Check if brick 1 is in the tower
        for (int x = 0; x < hand.length; x++) {
            if (hand[x] == 1) {
                brickOneIndex = x;
                break;
            }
        }

        // if brick 1 is in the right place:
        if (brickOneIndex == 0) {
            // iterate through the hand from index 1 and find a good position to replace a brick.
            for (int x = 1; x < hand.length - 1; x++) {
                // Check if the new brick is in between index i-1 and index i+1
                // Also check if the new brick is less than hand[i]
                if ((hand[x - 1] < block) && (block < hand[x + 1]) && (block < hand[x])) {
                    // If so, replace that block
                    return x;
                }
            }
        } else if (brickOneIndex > 0) {
            return brickOneIndex;
        } else {
            if (block < hand[0] && hand[1] > block) {
                return 0;
            } else {
                for (int x = 1; x < hand.length - 1; x++) {
                    if ((hand[x - 1] < block) && (block < hand[x + 1]) && (block < hand[x])) {
                        return x;
                    }
                }
            }
        }
        return -1;
    }
}
