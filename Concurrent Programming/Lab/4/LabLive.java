// javac -d bin *.java && java -cp bin LabLive

public class LabLive{
	public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        System.out.println( sf(10) );
        long t2 = System.currentTimeMillis();


        long t3 = System.currentTimeMillis();
        System.out.println( f2T(10) );
        long t4 = System.currentTimeMillis();

        long t5 = System.currentTimeMillis();
        System.out.println( fNT(10, 8) );
        long t6 = System.currentTimeMillis();
        System.out.println( "Seq: " + (t2-t1) + " parallel 2T: " + (t4-t3) + " PNT: " + (t6-t5) );

        // System.out.println( sf(5) );
        // System.out.println( fAB(5,3,4) );
        // System.out.println( f2T(5) );
        // System.out.println( fNT(5, 4 /*threads*/) );
    }

    //sequential
    public static int sf(int n){
        int temp = 1;
        for(int i=1; i<=n; i++){
            temp *= i;
        } 
        return temp; // 5! so it's 120
    }

    public static int fAB(int m, int min, int max){
        int temp = 1;
        for(int i=min; i<=max; i++){
            temp *= i;
        }
        return temp; //because of 3*4 it will be 12.
    }

    public static int f2T(int n){  // factorial 2 threads
        //tasks
        FactRunnable task1 = new FactRunnable(n, 1, n/2);
        FactRunnable task2 = new FactRunnable(n, n/2+1, n);
        Thread th1 = new Thread(task1);
        Thread th2 = new Thread(task2);
        th1.start();
        th2.start();
        try{ //you nee deverytime join this and surround with try-catch
            th1.join();
            th2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return task1.getResult()*task2.getResult();
    }

    public static int fNT(int n, int nt){ //parallel with N threads
        int chunk = (int) Math.ceil( (double)n/nt);
        //array of tasks
        FactRunnable[] tasks = new FactRunnable[nt];
        //array of threads
        Thread[] threads = new Thread[nt];
        for(int i=0; i<nt; i++){
            tasks[i] = new FactRunnable(n, chunk*i+1, Math.min((i+1)*chunk, n) );
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

        int temp = 1;
        for(int i=0; i<nt; i++){ //nt = number of threads
            temp *= tasks[i].getResult();
        }
        return temp;
    }

} // end of main class

class FactRunnable implements Runnable{
    int n, min ,max, temp;

    public FactRunnable(int n, int min, int max) {
        this.n = n;
        this.min = min;
        this.max = max;
    }

    int getResult(){
        return temp;
    }

    @Override
    public void run() {
        this.temp = LabLive.fAB(n, min, max);
    }

    
}
