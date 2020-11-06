package lock;

import java.util.concurrent.locks.ReentrantLock;

public class Lock1 {
    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Lock1 lock1 = new Lock1();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> lock1.get()).start();
            new Thread(() -> lock1.add()).start();
        }
    }

    public void add() {
//        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " add begin");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " add end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void get() {
//        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get begin");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " get end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
