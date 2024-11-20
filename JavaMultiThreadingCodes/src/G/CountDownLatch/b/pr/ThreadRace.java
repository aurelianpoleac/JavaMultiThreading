package G.CountDownLatch.b.pr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadRace {
	private static final int COMPETITORS_NO = 10;
	private final ExecutorService service;
	private final List<Future<Integer>> orderOfArrival;
	private final CompletionService<Integer> executorCompletionService;

	public ThreadRace() {
		service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		executorCompletionService = new ExecutorCompletionService<>(service);
		orderOfArrival = new ArrayList<>();
		for (int i = 0; i < COMPETITORS_NO; i++) {
			orderOfArrival.add(executorCompletionService.submit(new ThreadRaceCompetitor(i)));
		}
	}

	public List<ThreadRaceContext> getWinner() throws Exception {
		List<ThreadRaceContext> threadRaceContextList = new ArrayList<>();
		for (int i = 0; i < orderOfArrival.size(); i++) {
			ThreadRaceContext threadRaceContext = new ThreadRaceContext();
			threadRaceContext.setId(executorCompletionService.take().get());
			threadRaceContextList.add(threadRaceContext);
		}
		service.shutdown();
		return threadRaceContextList;
	}

	public static void main(String[] args) throws Exception {
		ThreadRace race = new ThreadRace();
		List<ThreadRaceContext> orderOfArrival = race.getWinner();
		System.out.println("Order of Arrival : ");
		for (ThreadRaceContext context : orderOfArrival) {
			System.out.println(context.toString());
		}
		System.out.println("Winner is : " + orderOfArrival.getFirst());

	}
}
