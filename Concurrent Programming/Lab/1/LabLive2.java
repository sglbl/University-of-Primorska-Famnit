// javac -d bin *.java && java -cp bin LabLive2
public class LabLive2 implements Runnable{
	public static void main(String[] args) {	
		/// HOW TO CREATE THREAD
		Thread thread1 = new Thread(new LabLive2());
		//task
		LabLive2 task = new LabLive2();
		Thread thread2 = new Thread(task);
		thread1.setName("FIRST");
		thread1.start();
		thread2.start();
		
	}
	
	@Override
	public void run() {
		System.out.println( Thread.currentThread().getName() );	
	}
	
}
