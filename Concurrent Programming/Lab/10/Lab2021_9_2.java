//Philosophers dinner
import java.util.concurrent.Semaphore;
class Lab2021_9_2{
    public static final int N=5; //Number of philosophers
    public static void main(String[] args) {
        int n=N;
        Semaphore[] forks=new Semaphore[n];
        Philosopher[] philosopher=new Philosopher[n];
        for(int i=0;i<n;i++){
            forks[i]=new Semaphore(1);
        }
        for(int i=0;i<n;i++){
            philosopher[i]=new Philosopher(i,n,forks);
        }
        for(int i=0;i<n;i++){
            philosopher[i].start();
        }
    }
}
class Philosopher extends Thread {
    int id;
    int firstodd;
    int secondeven;
    Semaphore[] forks;
    public Philosopher(int id, int n, Semaphore[] forks) {
        this.id = id;
        this.forks = forks;
        if ((id % 2) == 1) {
            // if his id is odd, he is "firstodd"
            firstodd = id;
            // and second one (even) is philosopher from his right side (first even is philosopher from his left side)
            secondeven = (id + 1) % n;
            // on the other hand he is second even "secondeven", and first odd is first one from his right side
        } else {
            firstodd = (id + 1) % n;
            secondeven = id;
        }
    }
    public void run() {
        while (true) {
            think();
            forks[firstodd].acquireUninterruptibly();
            forks[secondeven].acquireUninterruptibly();
            eat();
            forks[secondeven].release();
            forks[firstodd].release();
        }
    }
    private void think() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
        System.out.printf("Philosopher %d thinking...\n", id);
    }
    private void eat() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
        System.out.printf("Philosopher %d eating...\n", id);
    }
}
