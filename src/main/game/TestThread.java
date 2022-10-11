package game;

import strategy.Strategy;

public class TestThread extends Thread {

    Strategy strategy;
    public int successes;
    public int totalTurns;
    int games;

    public TestThread(int games, Strategy strategy) {
        super();
        this.strategy = strategy;
        this.games = games;
        successes = 0;
        totalTurns = 0;
    }


    public void run() {
        for (int x = 0; x < games; x++) {
            TowerBlaster towerBlaster = new TowerBlaster(strategy);
            towerBlaster.initializeGameData();
            towerBlaster.silenceLogging();
            boolean winner = false;
            int turn = 0;
            while (!winner && turn < 1000) {
                if (turn % 2 == 0) {
                    towerBlaster.computerTurn(/* handNumber=*/ 1);
                    // We only care if player 1 has won
                    winner = towerBlaster.hasWon(/* handNumber=*/ 1);
                } else {
                    towerBlaster.computerTurn(/* handNumber=*/ 2);
                }
                turn++;
            }

            if (winner) {
                successes++;
                totalTurns += (turn / 2);
            }
        }
    }

}
