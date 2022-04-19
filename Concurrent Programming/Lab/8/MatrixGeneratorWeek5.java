// javac -d bin *.java && java -cp bin LabLive
import java.util.*;

public class MatrixGeneratorWeek5{

    public static String print(int[][] array){
        int n = array.length;
        StringBuilder sb = new StringBuilder("[");
        for(int i=0; i<n; i++){
            if(i != 0) sb.append(" ");
            for(int j=0; j<n; j++)
                sb.append(array[i][j] + " ");
            if(i != n-1)    sb.append("\n");
            else            sb.append("]");
        }

        return sb.toString();
    }

    public static int[][] genMatrix(int n){
        int[][] result = new int[n][n];
        Random rand = new Random();
        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                result[i][j] = rand.nextInt(10);
        return result;
    }

    public static int[][] seqMM(int[][] a, int[][] b){
        int n = a.length;
        int[][] result = new int[n][n];
        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++){
                result[i][j] = 0;
                for(int k=0; k<n; k++)
                    result[i][j] +=  ( a[i][k] * b[k][j] );
            }
        return result;
    }

}

class MatrixRunnable implements Runnable{
    int[][] a,b,c;
    int row;
    
    public MatrixRunnable(int[][] a, int[][] b, int[][] c, int row) {
        this.a = a;
        this.b = b; 
        this.c = c;
        this.row = row;
    }

    @Override   //Every row is goning to be seperated
    public void run() {
        for(int i=0; i<a.length; i++){
            c[row][i] = 0;
            for(int k=0; k<a.length; k++){
                c[row][i] += a[row][k] * b[k][i];
            }
        }
        
    }
    
}

class MatrixParallel{
    public static int[][] ParallelMatrixMultiplication(int[][] a, int[][] b){
        int n = a.length; 
        int[][] result = new int [n][n];
        //array of tasks
        MatrixRunnable[] tasks = new MatrixRunnable[n];
        // array of threads
        Thread[] threads = new Thread[n];

        for(int i=0; i<n; i++){
            tasks[i] = new MatrixRunnable(a, b, result, i); // i= row
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }
        try{
            for(Thread th: threads)
                th.join();
        }catch(InterruptedException e){};

        return result;
    }
}