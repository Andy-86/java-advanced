package tool;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

public class SemaphoreDemo {

    public static void main(String[] args) {
        int num = 100;
        Semaphore semaphore = new Semaphore(5);
        for (int i = 1; i <= num; i++) {
            new Worker(i, semaphore).start();
        }
    }

    static class Worker extends Thread {
        private final int num;
        private final Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire(1);
                System.out.println("工人" + this.num + "占用一台机器生产");
                LockSupport.parkNanos(1000000 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("工人" + this.num + "释放一台机器");
                semaphore.release(1);
            }
        }
    }
}
