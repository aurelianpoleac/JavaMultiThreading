package G.CountDownLatch.c.pr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadRaceTeam implements Callable<ThreadRaceTeam> {
	private final static int COMPETITORS_NO = 4;
	private int id;
	private final String teamName;
	private final List<Future<ThreadCompetitor>> futureTeam;
	private final ExecutorService service;
	private final CountDownLatch latch;
	private final List<ThreadCompetitor> runnerList;

	public String getTeamName() {
		return teamName;
	}

	public ThreadRaceTeam() {
		this.teamName = TeamName.getName();
		futureTeam = new ArrayList<>(COMPETITORS_NO);
		runnerList = new ArrayList<>(COMPETITORS_NO);
		service = Executors.newFixedThreadPool(1);
		latch = new CountDownLatch(4);
	}

	@Override
	public ThreadRaceTeam call() throws Exception {
		System.out.println("Team -> " + teamName + " start");
		for (int i = 0; i < COMPETITORS_NO; i++) {
			this.id = i;
			futureTeam.add(service.submit(new ThreadCompetitor(teamName, i, latch)));
			ThreadCompetitor futureThreadCompetitor = futureTeam.get(i).get();
			runnerList.add(futureThreadCompetitor);
		}
		System.out.println("Team -> " + teamName + " end");
		service.shutdown();
		return this;
	}

	public int getId() {
		return id;
	}

}
