import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

// javac -d bin *.java && java -cp bin LabLive

public class LabLive2 implements Runnable{
    public static AtomicInteger counter = new AtomicInteger(); //value=0
    public static int level = 15;
    public static int thpoolsize = 5;
    static int counter1 = 0;

	public static void main(String[] args) {
        LabLive2 task = new LabLive2();
        ExecutorService threadPool = Executors.newFixedThreadPool(thpoolsize);
        for(int i=0; i<thpoolsize; i++)
            threadPool.submit(task);
        threadPool.shutdown();
    }

    @Override
    /*synchronized */  /*eklersen calisir sirayla */ public void run() { //if you use atomic, there is no repetition
        while(counter.get() < level)
            System.out.println( Thread.currentThread().getName() + ": " + counter.incrementAndGet() );
        // if you use this there can be repetition.
        // while(counter1 < level){
        //     counter1++;
        //     System.out.println(Thread.currentThread().getName() + " " + counter1);
        // }

        
    }

}
