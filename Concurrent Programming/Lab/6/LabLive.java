import java.util.*;

// javac -d bin *.java && java -cp bin LabLive

public class LabLive{
	public static void main(String[] args) {
        part1();
    }

    public static void part1(){
        int n=2;
        int[][] a = Matrix.genMatrix(n);
        int[][] b = Matrix.genMatrix(n);
        int[][] c = intervalMM(a, b, 0, 2);
        int[][] c1 = parallelMatrixMultiplication(a, b, 2);
        System.out.println( Matrix.toString(a) );
        System.out.println( Matrix.toString(b) );
        System.out.println( Matrix.toString(c) );
        System.out.println( Matrix.toString(c1) );
        // printMatrixNotQuadratic(c);

    }

    public static int[][] intervalMM(int[][] a, int[][] b, int min, int max){
        int n = a.length;
        int[][] c = new int[n][n];
        for(int i=min; i<max; i++){
            for(int j=0; j<n; j++){
                c[i][j] = 0;
                for(int k=0; k<n; k++){
                    c[i][j] += (a[i][k] * b[k][j]);
                }
            }
        }
        return c;
    }

    public static int[][] extricate(int[][] a, int min, int max){ //for non quadratic matrixes.
        int n = a.length;
        int[][] res = new int[max-min][n];
        for(int i=0; i<max-min; i++){
            for(int j=0; j<n; j++){
                res[i][j] = a[min+i][j];
            
            }
        }
        return res;
    }

    public static void printMatrixNotQuadratic(int[][] a){ //this method is for not quadratic matrixes.
        int n = a.length;
        int n1 = a[0].length;
        for(int i=0; i<n; i++){
            for(int j=0; j<n1; j++){
                System.out.print( a[i][j] + " " );
            }
            System.out.println();
        }
    }

    ////////////for parallel
    public static int[][] parallelMatrixMultiplication(int[][] a, int[][] b, int nt){
        int n= a.length;
        int chunk = (int)Math.ceil( (double)n/nt );
        //chunk when we you split with #of threads
        int c[][] = new int[n][n];
        MmRun[] tasks = new MmRun[nt];
        Thread[] threads = new Thread[nt];
        for(int i=0; i<nt; i++){
            tasks[i] = new MmRun(a, b, c, i*chunk, Math.min((i+1)*chunk, n) );
            threads[i] =new Thread(tasks[i]);
            threads[i].start();
        }
        try{
            for(Thread th:threads)
                th.join();
        }catch(InterruptedException e){}

        int[][] temp;
        for(int i=0; i<nt; i++){
            temp = extricate(tasks[i].getC(), chunk*i, Math.min((i+1)*chunk, n) );
            System.arraycopy(temp, 0, c, chunk*i, temp.length);
        }
        return c;
    }

}

class MmRun implements Runnable{  //parallel matrix multiplication
    int[][] a,b,c;
    int min,max;

    public MmRun(int[][] a, int[][] b, int[][] c, int min, int max) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.min = min;
        this.max = max;
    }    

    public int[][] getC(){
        return c;
    }

    @Override
    public void run() {
        this.c = LabLive.intervalMM(a, b, min, max);
    }
    
}

class Matrix{
    
    public static int[][] genMatrix(int n){
        int[][] result = new int[n][n];
        Random rand = new Random();
        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                result[i][j] = rand.nextInt(10);
        return result;
    }

    
    public static String toString(int[][] array){
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

}