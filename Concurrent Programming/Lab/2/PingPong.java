// javac -d bin *.java && java -cp bin PingPong
public class PingPong extends Thread {
    
    public static void main(String[] args) {
        //new PingPong("ping",33).start();
        //new PingPong("pong",100).start();
        PingPong p1 = new PingPong("ping",33);
        PingPong p2 = new PingPong("PONG",100);
        p1.setName("ping");
        p2.setName("PONG");
        p1.start();
        p2.start();
    }

	private static boolean winner=false;
	String word;
	int time;
	private int counter=0;
	private int counter1=0;

    PingPong(String w,  int t){
        word=w;
        time=t;
	}

    public PingPong(int counter, int counter1) {
        this.counter=counter;
        this.counter1=counter1;
    }

    public void run() {
        while(winner==false) {
            if(counter==21 || counter1==21) {
                winner=true;
                System.out.println("Winner is "+Thread.currentThread().getName());
                break;
            }
            /*else if(counter1==21) {
                winner=true;
                System.out.println("Winner is Pong!");
                break;
            }*/
            for(int i=1;i<=21;i++) {
                System.out.println(word+" ");
                if(word=="ping") 
                    counter++;
                if(word=="pong")
                    counter1++;
            }
            try{
                sleep(time);
            }
            catch(InterruptedException e) {
                //return;
                e.printStackTrace();
            }
        }
    }
     
}