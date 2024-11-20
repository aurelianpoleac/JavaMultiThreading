package G.CountDownLatch.c.pr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadRelayRaceTeam {
	private static final int COMPETITORS_NO = 10;
	private final ExecutorService service;
	private final List<Future<ThreadRaceTeam>> orderOfArrival;
	private final CompletionService<ThreadRaceTeam> executorCompletionService;

	public ThreadRelayRaceTeam() {
		service = Executors.newFixedThreadPool(10);
		executorCompletionService = new ExecutorCompletionService<>(service);
		orderOfArrival = new ArrayList<>();
		for (int i = 0; i < COMPETITORS_NO; i++) {
			orderOfArrival.add(executorCompletionService.submit(new ThreadRaceTeam()));
		}
	}

	public List<ThreadRaceContext> getWinner() throws Exception {
		List<ThreadRaceContext> threadRaceContextList = new ArrayList<>();

		ThreadRaceTeam threadRaceTeam;
		for (int i = 0; i < orderOfArrival.size(); i++) {
			ThreadRaceContext threadRaceContext = new ThreadRaceContext();
			threadRaceTeam = executorCompletionService.take().get();
			threadRaceContext.setTeamName(threadRaceTeam.getTeamName());
			threadRaceContext.setRunnerID(threadRaceTeam.getId());
			threadRaceContextList.add(threadRaceContext);
		}
		service.shutdown();
		return threadRaceContextList;
	}

	public static void main(String[] args) throws Exception {
		ThreadRelayRaceTeam race = new ThreadRelayRaceTeam();
		List<ThreadRaceContext> orderOfArrival = race.getWinner();
		System.out.println();
		System.out.println("Order of Arrival : ");
		for (ThreadRaceContext context : orderOfArrival) {
			System.out.println(context.toString());
		}
		System.out.println("Winner is : " + orderOfArrival.getFirst());
	}
}
