import java.util.*;
import java.util.concurrent.*;

// javac -d bin *.java && java -cp bin LabLive

public class LabLive2{
    public static ArrayList<Integer> numbers = new ArrayList<Integer>();
    public static ArrayList<Integer> partialResults = new ArrayList<Integer>();

	public static void main(String[] args) {
        CountDownLatch barrier = new CountDownLatch(2); //wait in barrier until reaches 0
        fillList(30);

        new Thread(new SumRunnable(0, 10,barrier)).start();
        new Thread(new SumRunnable(10,20,barrier)).start();
        new Thread(new SumRunnable(20,30,barrier)).start();
        try{
            barrier.await();
        }catch(InterruptedException e){}

        int finalSum = 0;
        for(int i=0; i<partialResults.size(); i++){
            try{
                finalSum += partialResults.get(i);
            }
            catch(Exception e){}
        }
        System.out.println( "Overall sum is " + finalSum );

    }

    public static void fillList(int n){
        Random r = new Random();
        for(int i=0; i<n; i++)
            numbers.add( r.nextInt(10) );
    }


}

class SumRunnable implements Runnable{
    int start, end;
    CountDownLatch c;
    SumRunnable(int start, int end, CountDownLatch b){
        this.start = start;
        this.end= end;
        this.c = b;
    }

    @Override
    public void run() {
        int sum = 0;
        for(int i=start; i<end; i++){
            sum = LabLive2.numbers.get(i);
        }

        LabLive2.partialResults.add(sum);
        System.out.println( "Thread " + Thread.currentThread().getName() + " sum is " + sum );
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){}
        c.countDown();
    }
    
    


}
