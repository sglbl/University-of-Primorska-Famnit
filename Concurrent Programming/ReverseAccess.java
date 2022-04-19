// javac ReverseAccess.java && java ReverseAccess
public class ReverseAccess{
	
	public static void main(String[] argc){
		String string = "FAMNIT";
		Print objectToPrint = new Print(string);

		Thread thread1 = new Thread( new Print(string) );
		Thread thread2 = new Thread( new Print(string) );

		thread1.start();
		thread2.start();

		try{
			thread1.join();
			thread2.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println();
	}

}

class Print implements Runnable{
	private String value;

	public Print(String value){
		this.value = value;
	}

	@Override
	public void run(){
		for(int i=0; i<value.length(); i++){
			if(i==value.length()) 
				System.out.print(value.charAt(value.length()-i-1) );
			else 
				System.out.print(value.charAt(value.length()-i-1) + "-");
			Thread.yield();
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}