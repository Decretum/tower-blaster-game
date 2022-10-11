import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;
import strategy.*;

public class StrategyVersusTest {

    private static final ImmutableList<Strategy> ALL_STRATEGIES = ImmutableList.of(new Strategy0(), new Strategy1(), new Strategy2(), new Strategy3(), new Strategy4());

    @Test
    public void compareStrategies() throws Exception {
        for (int x = 0; x < ALL_STRATEGIES.size(); x++) {
            for (int y = x + 1; y < ALL_STRATEGIES.size(); y++) {
                compareStrategies(ALL_STRATEGIES.get(x), ALL_STRATEGIES.get(y));
            }
        }
    }

    void compareStrategies(Strategy strategy1, Strategy strategy2) throws Exception {
        int processors = Runtime.getRuntime().availableProcessors();
        int threads = processors - 2;
        long successes = 0;
        long player1Wins = 0;
        long player2Wins = 0;
        int totalGames = 10000;
        int remainingGames = totalGames % threads;

        if (threads < 1) {
            threads = 1;
        }

        TestThread[] threadList = new TestThread[threads];

        for (int x = 0; x < threads; x++) {
            threadList[x] = new TestThread(totalGames / threads, strategy1, strategy2);
            threadList[x].start();
        }

        for (int x = 0; x < threads; x++) {
            threadList[x].join();
            successes += threadList[x].successes;
            player1Wins += threadList[x].player1Wins;
            player2Wins += threadList[x].player2Wins;
        }

        TestThread remainderThread = new TestThread(remainingGames, strategy1, strategy2);
        remainderThread.start();
        remainderThread.join();
        successes += remainderThread.successes;
        player1Wins += remainderThread.player1Wins;
        player2Wins += remainderThread.player2Wins;

        System.out.println(strategy1.getClass().getSimpleName() + " vs " + strategy2.getClass().getSimpleName() + ": " + player1Wins + " / " + player2Wins + " / " + (totalGames - successes));
    }

}
