package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Object[] items = new Object[20];
    private int putIndex, takeIndex, count;

    public void put(Object x) {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[putIndex++] = x;
            System.out.println(Thread.currentThread().getName() + " put " + x.toString());
            if (putIndex == items.length) {
                putIndex = 0;
            }
            count++;
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        Object x = null;
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            x = items[takeIndex++];
            System.out.println(Thread.currentThread().getName() + " take " + x.toString());
            if (takeIndex == items.length) {
                takeIndex = 0;
            }
            count--;
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return x;
    }

    public static void main(String[] args) {
        ConditionDemo conditionDemo = new ConditionDemo();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> conditionDemo.take()).start();
            new Thread(() -> conditionDemo.put(new Object())).start();
        }
    }
}
