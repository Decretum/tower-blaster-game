package game;

import strategy.Strategy;
import strategy.Strategy4;
import strategy.TurnAction;

import javax.swing.*;
import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class TowerBlaster {

    private Stack<Integer> deck;
    private Stack<Integer> discard;
    private int[] hand1;
    private int[] hand2;
    private boolean silenceLogging;

    private final Strategy computerPlayerStrategy;

    public TowerBlaster() {
        this(new Strategy4());
    }

    public TowerBlaster(Strategy computerPlayerStrategy) {
        this.computerPlayerStrategy = computerPlayerStrategy;
        silenceLogging = false;
    }

    public void silenceLogging() {
        silenceLogging = true;
    }

    public void log(String message) {
        if (!silenceLogging) {
            System.out.println(message);
        }
    }

    public void initializeGameData() {
        deck = new Stack<>();
        discard = new Stack<>();
        hand1 = new int[10];
        hand2 = new int[10];

        // Generate the blocks from 1 to 60 and shuffle them
        List<Integer> allBlocks = new ArrayList<>(60);
        for (int x = 1; x <= 60; x++) {
            allBlocks.add(x);
        }
        Collections.shuffle(allBlocks);

        // Put them all in the deck first
        for (int block : allBlocks) {
            deck.push(block);
        }

        // Deal 10 blocks from the deck to each player's hand
        for (int x = 0; x < 10; x++) {
            hand1[x] = deck.pop();
            hand2[x] = deck.pop();
        }

        // Put one card from the deck into the discard pile
        discard.push(deck.pop());
    }

    private void restockDeckIfNecessary() {
        if (deck.empty()) {
            // Remove all but one blocks from the discard pile
            while (discard.size() != 1) {
                deck.add(discard.pop());
            }

            // Shuffle the blocks
            Collections.shuffle(deck);
        }
    }

    public void computerTurn(int handNumber) {
        int[] hand = getHand(handNumber);

        restockDeckIfNecessary();
        log("Computer turn");
        visualize(hand);

        TurnAction turnAction = computerPlayerStrategy.decideAction(discard.peek(), deck.peek(), hand);

        int block;

        if (turnAction.getUsage() == TurnAction.Usage.DISCARD) {
            block = discard.pop();
        } else if (turnAction.getUsage() == TurnAction.Usage.DECK) {
            block = deck.pop();
        } else {
            // For the none case, just do nothing
            discard.push(deck.pop());
            return;
        }

        discard.push(hand[turnAction.getReplacePosition()]);
        hand[turnAction.getReplacePosition()] = block;

        visualize(hand);
    }

    public boolean hasWon(int handNumber) {
        int[] hand = getHand(handNumber);
        for (int x = 1; x < hand.length; x++) {
            if (hand[x] < hand[x - 1]) {
                return false;
            }
        }
        return true;
    }

    public void visualize(int[] hand) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int x = 0; x < hand.length; x++) {
            stringBuilder.append(hand[x]);
            if (x != hand.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        log(stringBuilder.toString());
    }

    private int[] getHand(int handNumber) {
        if (handNumber == 1) {
            return hand1;
        } else if (handNumber == 2) {
            return hand2;
        }
        throw new InvalidParameterException("Invalid hand number :" + handNumber);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TowerBlaster::new);
    }

}
