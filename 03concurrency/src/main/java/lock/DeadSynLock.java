package lock;

public class DeadSynLock {
    private final byte[] lock1 = new byte[1];
    private final byte[] lock2 = new byte[1];

    public static void main(String[] args) {
        DeadSynLock deadSynLock = new DeadSynLock();
        Thread t1 = new Thread(() -> deadSynLock.test1());
        t1.setName("t1");
        t1.start();

        Thread t2 = new Thread(() -> deadSynLock.test2());
        t2.setName("t2");
        t2.start();
    }

    public void test1() {
        synchronized (lock1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public void test2() {
        synchronized (lock2) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}
