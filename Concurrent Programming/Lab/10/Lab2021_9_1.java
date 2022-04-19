import java.util.*;
import java.util.concurrent.*;

public class Lab2021_9_1 {
    static Semaphore atms = new Semaphore(4);
    static class User extends Thread{
        String name = "";
        User(String name){
            this.name=name;
        }
        public void run() {
            try {
                System.out.println(name +": seek permission ...");
                System.out.println(name +": current number of free ATMs: "+atms.availablePermits());
                atms.acquire();
                System.out.println(name+": Access approved!");
                try {
                    for(int i=1; i<=3; i++) {
                        System.out.println(name+": is working on operation: "+i);
                        Thread.sleep(1000);
                    }
                }
                finally {
                    System.out.println(name+": leaves the ATM.");
                    atms.release();
                    System.out.println(name+": current number of free ATMs: "+atms.availablePermits());
                }
            }
            catch(InterruptedException e) {e.printStackTrace();}
        }
    }
    public static void main(String[] args) {
        System.out.println("Number of available ATMs is: "+atms.availablePermits());
        User u1=new User("User 1");
        User u2=new User("User 2");
        //User u3=new User("User 3");
        //User u4=new User("User 4");
        //User u5=new User("User 5");
        u1.start();
        u2.start();
        //u3.start();
        //u4.start();
        //u5.start();
    }
}
