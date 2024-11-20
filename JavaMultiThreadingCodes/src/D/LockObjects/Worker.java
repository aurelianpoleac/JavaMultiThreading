package D.LockObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

/**
 * Multiple locks to speed up complex multi-threaded code. Define shared
 * objects: list1 and list2 then synchronize these objects. Mainly discussing
 * making the method synchronized or making an object inside the method
 * synchronized, By defining two different locks we say that one thread may
 * execute the stageOne while other executes stageTwo.
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
public class Worker {

    private final Random random = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void stageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
                list1.add(random.nextInt(100));
            } catch (InterruptedException e) {
                e.getCause();
            }
        }
    }

    public void stageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
                list2.add(random.nextInt(100));
            } catch (InterruptedException e) {
                e.getCause();
            }
        }
    }

    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void startWithObjectSync() {
        System.out.println("Starting ...");
        long start = currentTimeMillis();
        Thread t1 = new Thread(this::process);
        Thread t2 = new Thread(this::process);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.getCause();
        }

        long end = currentTimeMillis();

        System.out.println("Time taken: " + (end - start));
        System.out.println("List1: " + list1.size() + "; List2: " + list2.size());
    }
}
