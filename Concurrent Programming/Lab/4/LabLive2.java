// javac -d bin *.java && java -cp bin LabLive

public class LabLive2{
	public static void main(String[] args) {
        int a[] = {1,5,-5,3,88,1};
        System.out.println( sMax(a) );
        System.out.println( rangeMax(a, 1, 3) );
        System.out.println("For 2 threads: " + max2T(a) );
        System.out.println("For n threads: " + maxNT(a, 4) );
    }

    //seq
    public static int sMax(int[] a){
        int n = a.length;
        int temp = a[0];
        for(int i=1; i<n; i++){
            if(temp < a[i]){
                temp=a[i];
            }
        }
        return temp;
    }

    //in range max
    public static int rangeMax(int[] a , int min, int max){
        int temp;  
        /* IMPORTANT */
        if(min == a.length) temp = 0;
        else                temp = a[min];

        for(int i=min; i<max; i++)
            if(temp < a[i])
                temp= a[i];
        
        return temp;
    }

    public static int max2T(int[] a){
        MaxRunnable task1 = new MaxRunnable(a, 0, a.length/2);
        MaxRunnable task2 = new MaxRunnable(a, a.length/2, a.length);
        Thread th1 = new Thread(task1);
        Thread th2 = new Thread(task2);
        th1.start();
        th2.start();
        try{
            th1.join();
            th2.join();
        }catch(InterruptedException e){}

        if(task1.getMax() < task2.getMax() )
            return task2.getMax();
        else
            return task1.getMax();
    }

    public static int maxNT(int[] a, int nt){ //parallel with N threads
        int chunk = (int) Math.ceil( (double)a.length/nt);
        //array of tasks
        MaxRunnable[] tasks = new MaxRunnable[nt];
        //array of threads
        Thread[] threads = new Thread[nt];
        for(int i=0; i<nt; i++){
            tasks[i] = new MaxRunnable(a, chunk*i, Math.min((i)*chunk, a.length) );
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
        //array with max values from each thread
        int results[] = new int[nt];
        for(int i=0; i<nt; i++){ //nt = number of threads
            results[i] = tasks[i].getMax(); 
        }
        //1st idea
        return max2T(results);
        //2nd indea return Smax(results)
        //3rd idea use Math.max
        //4th idea is recursion
        //5th idea sort.
    }

}

class MaxRunnable implements Runnable{
    int[] a;
    int min,max,res;
    public MaxRunnable(int[] a, int min, int max) {
        this.a = a;
        this.min = min;
        this.max = max;
    }

    //method to return the value of maximim element in range min-max
    int getMax(){
        return res;
    }

    @Override
    public void run() {
        this.res = LabLive2.rangeMax(a, min, max);
        
    }

    
    
}
