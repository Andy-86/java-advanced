package lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public final static Object u = new Object();

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread("t1");
        ChangeObjectThread t2 = new ChangeObjectThread("t2");
        t1.start();
        t2.start();

        t1.interrupt();
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + Thread.currentThread().getName());
                LockSupport.parkNanos(5000 * 1000000L);
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("被打断了");
                }
                System.out.println("继续执行");
            }
        }
    }
}
