package G.CountDownLatch.c.pr;

public class ThreadRaceContext {
	private String teamName;
	private int runnerID;

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setRunnerID(int runnerID) {
		this.runnerID = runnerID;
	}

	@Override
	public String toString() {
		return "Team " + teamName + " RUNNER " + " ID -> " + runnerID;
	}

}
