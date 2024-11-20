package G.CountDownLatch.b.pr;

import java.util.Random;
import java.util.concurrent.Callable;

public class ThreadRaceCompetitor implements Callable<Integer> {
	private final int id;

	public ThreadRaceCompetitor(int id) {
		this.id = id;
	}

	@Override
	public Integer call() throws Exception {
		Thread.sleep(new Random().nextInt(3));
		return id;
	}

}
