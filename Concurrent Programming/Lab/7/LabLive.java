import java.util.concurrent.atomic.AtomicInteger;

// javac -d bin *.java && java -cp bin LabLive

public class LabLive{
	public static void main(String[] args) {
        IncreaseCounter task = new IncreaseCounter(10000);
        Thread th1 = new Thread(task);
        Thread th2 = new Thread(task);
        th1.start();  th2.start();
        try{
            th1.join();
            th2.join();
        }catch(InterruptedException e){}

        System.out.println( "Overall value of counter is:" + task.getC() );
    }

}

class IncreaseCounter implements Runnable{
    public static AtomicInteger counter = new AtomicInteger(); //value=0
    // int counter;
    int level;
    // atomic integer doesn't mean syncronized.

    IncreaseCounter(int level){
        this.level = level;
    }

    int getC(){
        return counter.get();
        // return counter;
    }

    @Override
    public void run() {
        for(int i=1; i<=level; i++){
            counter.incrementAndGet();
            // counter++;
        }
        
    }

}