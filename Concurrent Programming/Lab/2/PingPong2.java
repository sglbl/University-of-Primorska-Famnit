// javac -d bin *.java && java -cp bin PingPong2
public class PingPong2 implements Runnable{
	public static void main(String[] args) {
		PingPong2 th1 = new PingPong2("ping", 33);
		PingPong2 th2 = new PingPong2("ping", 100);

        Thread thrd1 = new Thread(th1);
        Thread thrd2 = new Thread(th2);
		thrd1.start();
		thrd2.start();	
	}
	
	String word;
	int time;
	int counter_pong = 0;
	int counter_ping = 0;
	private static boolean winner = false;
	
	//constructor
	public PingPong2(String word, int time) {
		this.word = word;
		this.time = time;
	}
	
	@Override
	public void run() {
		while(winner == false) {
			if(counter_pong == 11 && counter_ping < 11) {
				winner = true;
				System.out.println("Winner is: " + word);
				break;
			}
			else if(counter_ping == 11 && counter_pong < 11) {
				winner = true;
				System.out.println("Winner is: " + word);
				break;
			}
			
			for(int i=1; i<=11; i++) {
				System.out.println(word);
                if(word == "ping")
					counter_ping++;
				if(word == "pong")
					counter_pong++;
			}
			try {
				Thread.sleep(time);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	
	
}
