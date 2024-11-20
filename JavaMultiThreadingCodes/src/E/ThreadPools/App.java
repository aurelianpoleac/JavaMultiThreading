package E.ThreadPools;

/**
 * ThreadPool ("number of workers in a factory")
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {
    private final int id;

    public Processor(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("Starting: " + id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("Completed: " + id);
    }
}

public class App {

    public static void main(String[] args) throws Exception {
        boolean isFinised = false;
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            for (int i = 0; i < 2; i++) {
                executor.submit(new Processor(i));
            }
            executor.shutdown();
            System.out.println("All tasks submitted.");
            try {
                isFinised = executor.awaitTermination(1, TimeUnit.DAYS);
            } catch (InterruptedException ignored) {
            }
        } finally {
            if (isFinised) {
                System.out.println("All tasks completed.");
            } else {
                System.out.println("All tasks failed.");
            }
        }
    }
}
