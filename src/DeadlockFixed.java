import java.util.concurrent.*;

public class DeadlockFixed {
    static final Object LOCK_A = new Object();
    static final Object LOCK_B = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("T1: Esperando LOCK_A");
            synchronized (LOCK_A) {
                System.out.println("T1: Peguei a LOCK_A");
                dormir(50);
                System.out.println("T1: Esperando LOCK_B");
                synchronized (LOCK_B) {
                    System.out.println("T1: Peguei a LOCK_B");
                    System.out.println("T1 concluiu");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("T2: Esperando LOCK_A");
            synchronized (LOCK_A) {
                System.out.println("T2: Peguei a LOCK_A");
                dormir(50);
                System.out.println("T2: Esperando LOCK_B");
                synchronized (LOCK_B) {
                    System.out.println("Peguei a LOCK_B");
                    System.out.println("T2 concluiu");
                }
            }
        });

        t1.start();
        t2.start();
    }

    static void dormir(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); }
    }
}