package G.CountDownLatch.c.pr;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class ThreadCompetitor implements Callable<ThreadCompetitor> {
    private final CountDownLatch latch;
    private final int runnerID;
    private final String teamName;

    public ThreadCompetitor(String teamName, int id, CountDownLatch latch) {
        this.latch = latch;
        this.runnerID = id;
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "Team " + teamName + " RUNNER " + " ID -> " + runnerID;
    }

    @Override
    public ThreadCompetitor call() throws Exception {
        System.out.println(this + " start running ");
        Thread.sleep(new Random().nextInt(2500));
        latch.countDown();
        System.out.println(this + " end lap");
        return this;
    }
}
