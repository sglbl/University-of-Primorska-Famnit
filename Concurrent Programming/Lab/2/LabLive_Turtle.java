// javac -d bin *.java && java -cp bin LabLive_Turtle
public class LabLive_Turtle implements Runnable{
    
	public static void main(String[] args) {
        LabLive_Turtle task1 = new LabLive_Turtle("snail", 1070,1);
        @SuppressWarnings("unused")
        LabLive_Turtle task2;

        Thread th1 = new Thread(task1);
        Thread th2 = new Thread( task2 = new LabLive_Turtle("Turtle", 1, 10) );
        th1.start();
        th2.start();
    }
	
	String name;
    int speed, position;
	private static boolean winner = false;
	
	//constructor
	public LabLive_Turtle(String name, int position, int speed) {
        this.name = name;
		this.speed = speed;
		this.position = position;
	}
	
	@Override
	public void run() {
		while(winner == false) {
            position += speed;
            System.out.println(name + " is at position: " + position);
            if(position >= 1100){
                winner = true;
                System.out.println("Winner is:" + name);
                break;
            }
	

		}
	}
		
	
}