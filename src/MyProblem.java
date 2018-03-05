import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MyProblem {
    public static List<Thread> threadList = new ArrayList<>(); //ArrayList for the created Threads.

    public static void main(String[] args) {
        int numThreads = Integer.parseInt(args[0]);

        Counter c = new Counter();

        for (int i = 0; i < numThreads; ++i) {
            threadList.add(i, new Thread(new MyThread(c), Integer.toString(i)));
            threadList.get(i).start();
        }

        //Check threads end
        for (Thread t : threadList) { //For each thread, wait until is ended.
            try {
                t.join(); //join() is a function that waits until Thread t is finished.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nProgram of exercise 3 has terminated."); //Print final message
    }
}

class MyThread implements Runnable {
    private final Counter c;

    MyThread(Counter c) {
        this.c = c;
    }

    //Code going to be executed by the thread.
    @Override
    public void run() {
        Random randomSleep = new Random();
        try {
            Thread.sleep(randomSleep.nextInt(5) * 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Random randomIncrement = new Random();
        int toIncrement = randomIncrement.nextInt(10);
        // synchronized (c) {
        System.out.println(c.getTotal() + "+" + toIncrement + "=" + c.increment(toIncrement));
        // }
    }
}

class Counter {
    private AtomicInteger total = new AtomicInteger();

    public int increment(int inc) {
        return total.addAndGet(inc);
    }

    public int getTotal() {
        return total.get();
    }
}