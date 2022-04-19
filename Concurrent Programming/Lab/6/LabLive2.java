import java.util.*;

// javac -d bin *.java && java -cp bin LabLive2
// synchronized kullanimi

public class LabLive2{
	public static void main(String[] args) {
        CRunnable counter = new CRunnable(0);
        Thread th1 = new Thread(counter , "1st");
        Thread th2 = new Thread(counter , "2nd");
        Thread th3 = new Thread(counter , "3rd");
        th1.start(); th2.start(); th3.start();
        try{
            th1.join();
            th2.join();
            th3.join();
        }catch(InterruptedException e){}
    }

}

class CRunnable implements Runnable{
    int counter;
    CRunnable(int counter){
        this.counter = counter;
    }

    public void inc(){
        counter++;
    }
    public void dec(){
        counter--;
    }
    public int getC(){
        return counter;
    }

    synchronized public void run(){  // synchronized kelimesi onemli. bu olmasaydi, duzgun sirayla olmazdi.
        this.inc();
        System.out.println( "After incr: " + Thread.currentThread().getName() + " " + this.getC() );
        this.dec();
        System.out.println( "After decr: " + Thread.currentThread().getName() + " " + this.getC() );
    }
}