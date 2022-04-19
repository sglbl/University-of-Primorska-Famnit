// javac -d bin *.java && java -cp bin LabLive3
public class LabLive3 implements Runnable{
	public static void main(String[] args) {	
		Runnable char1 = new PrintChar1('a', 10);
        //const.threads
		Thread thread1 = new Thread(char1);
		Thread thread2 = new Thread(new PrintChar1('b', 10));
		PrintNumb thread3 = new PrintNumb(10);
		
		thread1.start();
		thread2.start();
		thread3.start();
	}
	
	@Override
	public void run() {
		System.out.println( "Name" + Thread.currentThread().getName() );	
	}
	
}

class PrintChar1 implements Runnable{
	private char ch;
	private int times;
	//constructor
	PrintChar1(char ch, int times){
		this.ch = ch;
		this.times = times;
	}
	
	@Override
	public void run() {
		for(int i=0; i<=times; i++)
			System.out.print(ch + " ");	
        System.out.println();
	}

}

class PrintNumb extends Thread{
	private int lastnum;
	//constructor
	PrintNumb(int lastnum){
		this.lastnum = lastnum;
	}
	
	@Override
	public void run() {
		for(int i=0; i<=lastnum; i++)
			System.out.print(i + " ");	
        System.out.println();
	}

}