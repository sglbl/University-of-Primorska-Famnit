import java.util.Random;

// javac -d bin *.java && java -cp bin LabLive

public class LabLive{
	public static void main(String[] args) {
        int n = 3;
        int[][] a = MatrixGenerator.genMatrix(n);
        System.out.println( MatrixGenerator.toString(a) );
        
    }

}

class MatrixGenerator{
    private static int[][] result;

    public static int[][] genMatrix(int n){
        result = new int[n][n];
        Random rand = new Random();
        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                result[i][j] = rand.nextInt(10);
        return result;
    }

    public static String toString(int[][] a){
        StringBuilder sb = new StringBuilder("[ \n");
        for(int i=0; i<result.length; i++){
            for(int j=0; j<result[i].length; j++)
                sb.append(result[i][j] + " ");
            sb.append("\n");
        }
        sb.append("]");

        return sb.toString();
    }

}