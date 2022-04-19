import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// javac -d bin *.java && java -cp bin LabLive

public class LabLive{

    static int[][] graph = {
                            {0,3,99,5},
                            {2,0,99,4},
                            {99,1,0,5},
                            {99,99,2,0}    
                           };
    static int[][] graph2 = graph.clone();
    static int n = graph2.length;

	public static void main(String[] args) {
        Printer.print( floydSequential(graph) );

        ExecutorService ex = Executors.newFixedThreadPool(n);
        for(int i=0; i<n; i++){
            FloydRunnable task = new FloydRunnable(i);
            ex.execute(task);
        }
        ex.shutdown();

        System.out.println();
        Printer.print( graph2 );


    }

    public static int[][] floydSequential(int[][] graph){
        int n = graph.length;
        for(int k=0; k<n; k++)
            for(int i=0; i<n; i++)
                for(int j=0; j<n; j++)
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
        return graph;
    }
    
}

class FloydRunnable implements Runnable{
    int index;
    int[][] matrix = LabLive.graph2;
    FloydRunnable(int index){
        this.index = index;
    }

    @Override
    public void run() {
        for(int k=0; k<matrix.length; k++)
            for(int j=0; j<matrix.length; j++)
                matrix[index][j] = Math.min( matrix[index][j], matrix[index][k] + matrix[k][j] );
        
    }
}

class Printer{

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
        System.out.println( sb.toString() );
        return sb.toString();
    }
}