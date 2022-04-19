import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// javac -d bin *.java && java -cp bin LabLive

public class LabLive{
	public static void main(String[] args) {
        TimeInitialization task1 = new TimeInitialization();
        TimeInitialization task2 = new TimeInitialization();
        TimeInitialization task3 = new TimeInitialization();
        TimeInitialization task4 = new TimeInitialization();
        TimeInitialization task5 = new TimeInitialization();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        threadPool.execute(task1);
        threadPool.execute(task2);
        threadPool.execute(task3);
        threadPool.execute(task4);
        threadPool.execute(task5);
        threadPool.shutdown();
        //they were free so they took another test to implement.(1and3)
    }
    
}

class TimeInitialization implements Runnable{

    @Override
    public void run(){
        try{
            for(int i=0; i<=1; i++){
                // Initialization
                if(i==0){
                    Date d = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
                    System.out.println( "Time of initialization of " + Thread.currentThread().getName() + " " + df.format(d));
                }
                else{
                    Date d = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
                    System.out.println( "Time of execution of " + Thread.currentThread().getName() + " " + df.format(d));
                }
                Thread.sleep(1000);
            }
            System.out.println( Thread.currentThread().getName() + " done!" );
        }catch(InterruptedException e){}
        
    }

}