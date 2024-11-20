package G.CountDownLatch.a.pr;

import java.util.Queue;

public class Server extends Thread {
	private final Queue<String> queue;
	private final int maxSize;

	public Server(Queue<String> queue, int maxSize, String name) {
		super(name);
		this.queue = queue;
		this.maxSize = maxSize;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (queue) {
				while (queue.isEmpty()) {
					System.out.println("Queue is empty");
					try {
						queue.wait();
					} catch (Exception ex) {
						System.out.println(ex.getMessage());;
					}
				}
				System.out.println(Thread.currentThread().getName() + " -> Message OUT : " + queue.remove() + " -> SERVER LOAD = " + calculatePercentage(queue.size(), maxSize) + "%");
				queue.notifyAll();
			}
		}
	}

	private double calculatePercentage(double obtained, double total) {
		return obtained * 100 / total;
	}
}
