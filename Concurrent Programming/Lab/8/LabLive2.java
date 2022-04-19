import java.util.Date;
// javac -d bin *.java && java -cp bin LabLive
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LabLive2{
    public static int n= 1000;
    public static int[][] a = MatrixGeneratorWeek5.genMatrix(n);
    public static int[][] b = MatrixGeneratorWeek5.genMatrix(n);
    public static int[][] c = new int[n][n];
    public static int nt = n; //number of threads

	public static void main(String[] args) {
        //ornegin 1500den buyuk degerler icin bununla sequentialden daha iyi.
        //every thread has 1 row.

        Date s1 = new Date();
        MatrixGeneratorWeek5.seqMM(a, b);
        Date e1 = new Date();

        Date s2 = new Date();
        MMThreadPool(a, b, nt);
        Date e2 = new Date();

        System.out.println( "S: " + (e1.getTime()-s1.getTime()) + " P: " + (e2.getTime()-s2.getTime()) );

    }

    public static void MMThreadPool(int[][] a, int[][] b, int nt){
        ExecutorService threadPool = Executors.newFixedThreadPool(nt);
        for(int i=0; i<nt; i++){
            ThreadPoolMatrixMultiplication task = new ThreadPoolMatrixMultiplication(i, c);
            threadPool.execute(task);
        }
        threadPool.shutdown();
    }
    
}

class ThreadPoolMatrixMultiplication implements Runnable{
    int index;
    int[][] result;
    int n=LabLive2.n;

    ThreadPoolMatrixMultiplication(int index ,int[][] result){
        this.index = index;
        this.result = result;
    }

    @Override
    public void run(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                result[index][i] += LabLive2.a[index][j] * LabLive2.b[j][i];
            }
        }
        
    }

}