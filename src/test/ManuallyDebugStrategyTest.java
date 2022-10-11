import game.TowerBlaster;
import org.testng.annotations.Test;
import strategy.*;

public class ManuallyDebugStrategyTest {

    // Manually put your strategy here.
    private final Strategy toTest = new Strategy0();

    @Test
    public void debug() {
        TowerBlaster towerBlaster = new TowerBlaster(toTest);
        towerBlaster.initializeGameData();

        boolean winner = false;
        int turn = 0;
        while (!winner && turn < 50) {
            if (turn % 2 == 0) {
                towerBlaster.computerTurn(/* handNumber=*/ 1);
                // We only care if player 1 has won
                winner = towerBlaster.hasWon(/* handNumber=*/ 1);
            } else {
                towerBlaster.computerTurn(/* handNumber=*/ 2);
            }
            turn++;
        }
        System.out.println(turn + " turns");
    }
}
