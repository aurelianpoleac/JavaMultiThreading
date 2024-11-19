package C_JoiningAndSynchronizeThreads;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@code synchronized} ("only let one thread in here at a time".) and {@code join} ("wait until
 * thread on which join has called finished") keyword.
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

    private int sum = 0;
    private int count = 0;

    public static void main(String[] args) {
        Worker worker = new Worker();
        //worker.doWork();
        worker.addNumbers();
    }

    /**
     * Run code again by removing the synchronized and join keywords By removing
     * synchronized keyword count variable will vary that is it is not atomic in
     * this case due to the reason that count is shared between the threads or
     * without join keyword count will output wrong.
     */
    public synchronized void increment(String threadName) throws InterruptedException {
        count++;
        //Thread.sleep(1000);
        System.out.println("Thread in Progress: " + threadName + " and count is: " + count);
    }

    public void doWork() {
        Thread thread1 = getThread();
        thread1.start();
        Thread thread2 = getThread();
        thread2.start();
        /**
         * Join Threads: Finish until thread finishes execution, then progress
         * the code Reminder: your method is also a thread so without joining
         * threads System.out.println("Count is: " + count); will work
         * immediately. Join does not terminate Thread 2, just progress of the
         * code stops until Threads terminate.
         */
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ignored) {
        }
        System.out.println("Count is: " + count);
    }

    private Thread getThread() {
        return new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    increment(Thread.currentThread().getName());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    private Thread getSum() {
        return new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                add(i);
            }
        });
    }

    public synchronized int add(int i) {
        return sum += 1;
    }


    public void addNumbers() {
        Thread t1 = getSum();
        t1.start();
        Thread t2 = getSum();
        t2.start();
        Thread t3 = getSum();
        t3.start();
        Thread t4 = getSum();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException ignored) {
        }
        System.out.println("SUM is: " + sum);
    }
}
