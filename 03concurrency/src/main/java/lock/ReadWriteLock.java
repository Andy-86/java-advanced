package lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReadWriteLock();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> readWriteLock.get()).start();
//            new Thread(() -> readWriteLock.add()).start();
        }
    }

    public void add() {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " add begin");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " add end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void get() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get begin");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " get end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
}
