package G.CountDownLatch.b.pr;

public class ThreadRaceContext {
	private int id;

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ID " + id;
	}

}
