// javac -d bin *.java && java -cp bin LabLive
public class LabLive extends Thread{
	public static void main(String[] args) {
		LabLive thread1 = new LabLive();
		LabLive thread2 = new LabLive();
		thread1.setName("FIRST");
		thread1.start();
		thread2.start();	
		
	}
	
	@Override
	public void run() {
		System.out.println( Thread.currentThread().getName() );	
	}
	
}
