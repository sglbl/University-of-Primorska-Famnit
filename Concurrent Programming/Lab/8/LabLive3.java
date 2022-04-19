import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LabLive3{
    public static int n= 1000;
    public static int[][] a = MatrixGeneratorWeek5.genMatrix(n);
    public static int[][] b = MatrixGeneratorWeek5.genMatrix(n);
    public static int[][] c = new int[n][n];
    public static int nt = n; //number of threads

	public static void main(String[] args) {
        //CyclicBarrier barrier = new CyclicBarrier(3);
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable(){
            @Override
            public void run() {
                System.out.println("PREPARED");
                
            }
        });
        Meals task = new Meals(barrier);
        Thread th1 = new Thread(task, "BurgerMachine");
        Thread th2 = new Thread(task, "FriesMachine");
        Thread th3 = new Thread(task, "ColaMachine");
        th1.start();
        th2.start();
        th3.start();

        //until everything is preparing, nothing is one. 
        // With the help of barriers, we wait for everything is pepreared. And than everything will be done.
    }

}

class Meals implements Runnable{
    CyclicBarrier barrier;

    public Meals(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run(){
        String thName = Thread.currentThread().getName();
        System.out.println("Machine " + thName + " is starting...");
        switch(thName){
            case "BurgerMachine":
                System.out.println("Preparing burgers");
                break;
            case "FriesMachine":
                System.out.println("Preparing fries");
                break;
            case "ColaMachine":
                System.out.println("Preparing cola");
                break;
        }
        try{
            this.barrier.await();
        }catch(BrokenBarrierException | InterruptedException b){
            b.printStackTrace();
        } 
        System.out.println(thName + " ... done!");

    }




}