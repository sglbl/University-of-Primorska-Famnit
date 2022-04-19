// java program to demonstrate use of semaphores Locks
import java.util.concurrent.*;

class Lab2021_9{
    static int count = 0;
    public static void main(String args[]) throws InterruptedException {
        Semaphore sem = new Semaphore(1);
        SemaphoresThreads mt1 = new SemaphoresThreads(sem, "A");
        SemaphoresThreads mt2 = new SemaphoresThreads(sem, "B");
        mt1.start();
        mt2.start();
        mt1.join();
        mt2.join();
        System.out.println("count: " + Lab2021_9.count);
    }
}
class SemaphoresThreads extends Thread {
    Semaphore sem;
    String threadName;
    public SemaphoresThreads(Semaphore sem, String threadName) {
        super(threadName);
        this.sem = sem;
        this.threadName = threadName;
    }
    @Override
    public void run() {
        // run by thread A
        if(this.getName().equals("A")) {
            System.out.println("Starting " + threadName);
            try {
                // First, get a permit.
                System.out.println(threadName + " is waiting for a permit.");
                // acquiring the lock
                sem.acquire();
                System.out.println(threadName + " gets a permit.");
                for(int i=0; i < 5; i++) {
                    Lab2021_9.count++;
                    System.out.println(threadName + ": " + Lab2021_9.count);
                    Thread.sleep(10);
                }
            } catch (InterruptedException exc) {}
            // Release the permit.
            System.out.println(threadName + " releases the permit.");
            sem.release();
        }
        else {
            System.out.println("Starting " + threadName);
            try {
                System.out.println(threadName + " is waiting for a permit.");
                sem.acquire();
                System.out.println(threadName + " gets a permit.");
                for(int i=0; i < 5; i++) {
                    Lab2021_9.count--;
                    System.out.println(threadName + ": " + Lab2021_9.count);
                    Thread.sleep(10);
                }
            } catch (InterruptedException exc) {}
            System.out.println(threadName + " releases the permit.");
            sem.release();
        }
    }
} 