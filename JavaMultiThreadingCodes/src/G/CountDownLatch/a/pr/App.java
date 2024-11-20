package G.CountDownLatch.a.pr;

import java.util.LinkedList;
import java.util.Queue;

public class App {
    public static void main(String[] args) {

        Queue<String> buffer = new LinkedList<>();
        int maxSize = 10;
        Thread sourceOne = new Node(buffer, maxSize, "SOURCE ONE");
        Thread sourceTwo = new Node(buffer, maxSize, "SOURCE TWO");
        Thread sourceThree = new Node(buffer, maxSize, "SOURCE THREE");
        Thread sourceFour = new Node(buffer, maxSize, "SOURCE FOUR");
        Thread sourceFive = new Node(buffer, maxSize, "SOURCE FIVE");
        Thread sourceSix = new Node(buffer, maxSize, "SOURCE SIX");
        Thread sourceSeven = new Node(buffer, maxSize, "SOURCE SEVEN");
        Thread sourceEight = new Node(buffer, maxSize, "SOURCE EIGHT");
        Thread sourceNine = new Node(buffer, maxSize, "SOURCE NINE");
        Thread sourceTen = new Node(buffer, maxSize, "SOURCE TEN");
        Thread consumer = new Server(buffer, maxSize, "CONSUMER");
        sourceOne.start();
        sourceTwo.start();
        sourceThree.start();
        sourceFour.start();
        sourceFive.start();
        sourceSix.start();
        sourceSeven.start();
        sourceEight.start();
        sourceNine.start();
        sourceTen.start();
        consumer.start();

    }
}
