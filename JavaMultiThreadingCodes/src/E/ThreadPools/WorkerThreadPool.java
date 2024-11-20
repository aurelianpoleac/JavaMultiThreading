package E.ThreadPools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This is the implementation of {@link D.LockObjects.Worker} with threadPool
 * <br><br>
 * Codes with minor comments are from
 * <a href="http://www.caveofprogramming.com/youtube/">
 * <em>http://www.caveofprogramming.com/youtube/</em>
 * </a>
 * <br>
 * also freely available at
 * <a href="https://www.udemy.com/java-multithreading/?couponCode=FREE">
 * <em>https://www.udemy.com/java-multithreading/?couponCode=FREE</em>
 * </a>
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
class Worker implements Runnable {
    private final Random random = new Random();
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    public List<Integer> list1 = new ArrayList<>();
    public List<Integer> list2 = new ArrayList<>();

    @Override
    public void run() {
        process();
    }

    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void stageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
                list1.add(random.nextInt(100));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
                list2.add(random.nextInt(100));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

public class WorkerThreadPool {

    public static void main(String[] args) {
        long start;
        Worker worker;
        boolean isFinish = false;
        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
            System.out.println("Starting ...");
            start = System.currentTimeMillis();
            worker = new Worker();
            for (int i = 0; i < 3; i++) {//worker.run is called 2 (threads started) times by two threads
                executor.execute(worker);
            }
            executor.shutdown(); //prevents new tasks from being accepted by the ExecutorService
            try {
                isFinish = executor.awaitTermination(1, TimeUnit.DAYS);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            } finally {
                if (isFinish) {
                    System.out.println("Finished ...");
                } else {
                    System.out.println("Something went wrong ...");
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
        System.out.println("List1: " + worker.list1.size() + "; List2: " + worker.list2.size());
    }

}
