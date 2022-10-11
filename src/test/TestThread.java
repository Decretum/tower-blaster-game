import game.TowerBlaster;
import strategy.Strategy;

public class TestThread extends Thread {

    Strategy strategy1;
    Strategy strategy2;
    int successes;
    int totalTurns;
    int games;
    int player1Wins;
    int player2Wins;

    TestThread(int games, Strategy strategy) {
        super();
        this.strategy1 = strategy;
        this.strategy2 = strategy;
        this.games = games;
        successes = 0;
        totalTurns = 0;
        player1Wins = 0;
        player2Wins = 0;
    }

    TestThread(int games, Strategy strategy1, Strategy strategy2) {
        super();
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
        this.games = games;
        successes = 0;
        totalTurns = 0;
    }


    public void run() {
        for (int x = 0; x < games; x++) {
            TowerBlaster towerBlaster = new TowerBlaster(strategy1, strategy2);
            towerBlaster.initializeGameData();
            towerBlaster.silenceLogging();
            int winner = 0;
            int turn = 0;
            while (winner == 0 && turn < 1000) {
                if (turn % 2 == 0) {
                    towerBlaster.computerTurn(/* handNumber=*/ 1);
                    winner = towerBlaster.hasWon(/* handNumber=*/ 1) ? 1 : 0;
                } else {
                    towerBlaster.computerTurn(/* handNumber=*/ 2);
                    winner = towerBlaster.hasWon(/* handNumber=*/ 2) ? 2 : 0;
                }
                turn++;
            }

            if (winner != 0) {
                successes++;
                totalTurns += (turn / 2);
            }

            if (winner == 1) {
                player1Wins++;
            }
            if (winner == 2) {
                player2Wins++;
            }
        }
    }

}
