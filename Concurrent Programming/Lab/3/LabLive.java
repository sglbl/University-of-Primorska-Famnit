// javac -d bin *.java && java -cp bin LabLive
import java.util.*;

public class LabLive{
    private static Random rand = new Random();

	public static void main(String[] args) {
        System.out.println("PART1: "); part1();
        System.out.println("\nPART2: "); part2();
    }

    public static void part1() {
        int[] arr = {34, 41, 5, 18, 0, 22, -4, 6, -25};    
        System.out.println("SEQUENTIAL");
        System.out.println("Sum is " + sumSequential(arr) );
        System.out.println("RUNNABLE"); //we split the legth and do jobs on subarrays.
        System.out.println( "parallel 2 threads: " + sum2Threads(arr) + " | parallel n threads: " + sumNThreads(arr, 3) );
    }

    public static void part2() {
        int[] b = createArr(1000000);

        long stime1 = System.currentTimeMillis();
        int resSeq = sumSequential(b);
        long etime1 = System.currentTimeMillis();
        long stime2 = System.currentTimeMillis();
        int resP2T = sum2Threads(b);
        long etime2 = System.currentTimeMillis();
        long stime3 = System.currentTimeMillis();
        int resPNT = sumNThreads(b, 8);
        long etime3 = System.currentTimeMillis();
        System.out.println("Sum|  seq: " + resSeq + " p2t: " + resP2T + " pnt: " + resPNT );
        System.out.println("Time| seq: " + (etime1-stime1) + " p2t: " + (etime2-stime2) + " pNt: " + (etime3 - stime3) );
    }

    //Help function to create an array
    public static int[] createArr(int n){
        int[] a = new int[n];
        for(int i=0; i<n; i++)
            a[i] = rand.nextInt(100 /*bound*/);
        return a;
    }

    // sequential part
    public static int sumSequential(int[] arr){
        int counter = 0;
        for(int i=0; i<arr.length; i++)
            counter += arr[i];
        return counter;
    }

    public static int sumSequentialRange(int[] arr, int min, int max){
        int counter = 0;
        for(int i=min; i<max; i++)
            counter += arr[i];
        return counter;
    }

    public static int sum2Threads(int[] arr){
        SumRunnable lsum = new SumRunnable(arr, 0, arr.length/2);
        SumRunnable rsum = new SumRunnable(arr, arr.length/2, arr.length);
        Thread th1 = new Thread(lsum);
        Thread th2 = new Thread(rsum);
        th1.start();
        th2.start();

        try{
            th1.join();
            th2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return lsum.getSum() + rsum.getSum();
        
    }

    public static int sumNThreads(int arr[], int nt/* n threads */){
        int chunk = (int) Math.ceil(1.0 * arr.length/nt); 
        // create array of tasks
        SumRunnable[] tasks = new SumRunnable[nt];
        // create array of threads
        Thread[] threads = new Thread[nt];
        for(int i=0; i<nt; i++){
            tasks[i] = new SumRunnable(arr, i*chunk, Math.min( (i+1)*chunk, arr.length) );
            threads[i] = new Thread( tasks[i] );
            threads[i].start();
        }
        try{
            for(int i=0; i<nt; i++){
                threads[i].join();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        int total = 0;
        for(int i=0; i<nt; i++){
            total += tasks[i].getSum();
        }
        return total;
    }


}

class SumRunnable implements Runnable{
    int[] arr;
    int min, max, sum;

    public SumRunnable(int[] arr, int min, int max) {
        this.arr = arr;
        this.min = min;
        this.max = max;
    }

    public int getSum(){
        return sum;
    }

    public void run(){
        this.sum = LabLive.sumSequentialRange(arr, min, max) ;
    }

    
}
