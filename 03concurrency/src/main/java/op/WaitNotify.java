package op;

public class WaitNotify {
    private volatile static int count;
    private final int max = 10;

    public static void main(String[] args) {
        WaitNotify t = new WaitNotify();
        Thread p1 = new Thread(() -> t.produce());
        p1.setName("p1");
        Thread c1 = new Thread(() -> t.consume());
        c1.setName("c1");
        Thread c2 = new Thread(() -> t.consume());
        c2.setName("c2");
        p1.start();
        c1.start();
        c2.start();
    }

    private void produce() {
        synchronized (this) {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":::run:::" + count);
                if (count >= max) {
                    System.out.println("满仓，没必要生产！！！");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    count++;
                    System.out.println("生产");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                notifyAll();
            }
        }
    }

    private void consume() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ":::run:::" + count);
            while (true) {
                if (count <= 0) {
                    System.out.println("空仓！！！");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    count--;
                    System.out.println("消费");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                notifyAll();
            }
        }
    }
}
