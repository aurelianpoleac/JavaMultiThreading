package G.CountDownLatch.a.pr;

import java.util.Queue;
import java.util.Random;

public class Node extends Thread {
	private final Queue<String> queue;
	private final int maxSize;

	public Node(Queue<String> queue, int maxSize, String name) {
		super(name);
		this.queue = queue;
		this.maxSize = maxSize;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (queue) {
				Random random = new Random();
				while (queue.size() == maxSize) {
					try {
						System.out.println("Server buffer is full ...");
						queue.wait();
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                queue.add(Message.getMessage(random.nextInt(10)));
				System.out.println("SOURCE -> Message IN FROM : " + Thread.currentThread().getName());
				queue.notifyAll();
			}
		}
	}
}
