import org.testng.annotations.Test;
import strategy.*;


/**
 * You can manually run these tests to get metrics on each strategy.
 */
public class StrategyTest {

    @Test
    public void strategy0Test() throws Exception {
        calculateAndPrintStrategyMetrics(new Strategy0());
    }

    @Test
    public void strategy1Test() throws Exception {
        calculateAndPrintStrategyMetrics(new Strategy1());
    }

    @Test
    public void strategy2Test() throws Exception {
        calculateAndPrintStrategyMetrics(new Strategy2());
    }

    @Test
    public void strategy3Test() throws Exception {
        calculateAndPrintStrategyMetrics(new Strategy3());
    }

    @Test
    public void strategy4Test() throws Exception {
        calculateAndPrintStrategyMetrics(new Strategy4());
    }

    void calculateAndPrintStrategyMetrics(Strategy strategy) throws Exception {
        int processors = Runtime.getRuntime().availableProcessors();
        int threads = processors - 2;
        long start = System.currentTimeMillis();
        long successes = 0;
        long totalTurns = 0;
        int totalGames = 10000;
        int remainingGames = totalGames % threads;

        System.out.println("Running with " + threads + " threads because we have " + processors + " total processors available.");

        TestThread[] threadList = new TestThread[threads];

        for (int x = 0; x < threads; x++) {
            threadList[x] = new TestThread(totalGames / threads, strategy);
            threadList[x].start();
        }

        for (int x = 0; x < threads; x++) {
            threadList[x].join();
            successes += threadList[x].successes;
            totalTurns += threadList[x].totalTurns;
        }

        TestThread remainderThread = new TestThread(remainingGames, strategy);
        remainderThread.start();
        remainderThread.join();
        successes += remainderThread.successes;
        totalTurns += remainderThread.totalTurns;

        System.out.println("Strategy " + strategy.getClass().getSimpleName());
        System.out.println("Ran " + totalGames + " games, we had a " + (successes * 100 / totalGames) + "% success rate.");
        System.out.println("Successful games had an average of " + (totalTurns / successes) + " turns before someone won.");
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start));
    }
}
