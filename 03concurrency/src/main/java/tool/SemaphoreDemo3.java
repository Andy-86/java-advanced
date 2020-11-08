package tool;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo3 {
    public static void main(String[] args) {
        // 启动线程
        for (int i = 0; i <= 10; i++) {
            // 生产者
            new Thread(new Producer()).start();
            // 消费者
            new Thread(new Consumer()).start();
        }
    }


    // 仓库
    static Warehouse warehouse = new Warehouse();

    static class Producer implements Runnable {

        static int num = 1;

        @Override
        public void run() {
            int n = num++;
            while (true) {
                try {
                    warehouse.put(n);
                    System.out.println("生产 >" + n);
                    // 速度较快。休息10毫秒
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("消费 <" + warehouse.take());
                    // 速度较慢，休息1000毫秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Warehouse {
        private final Semaphore core = new Semaphore(1);
        private final Semaphore notFull = new Semaphore(10);
        private final Semaphore notEmpty = new Semaphore(0);
        private final Object[] items = new Object[10];
        private int putIndex, takeIndex, count;

        private void put(Object x) {
            try {
                notFull.acquire();
                core.acquire();
                items[putIndex++] = x;
                count++;
                if (putIndex == items.length) {
                    putIndex = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                core.release();
                notEmpty.release();
            }
        }

        private Object take() {
            Object x = null;
            try {
                notEmpty.acquire();
                core.acquire();
                x = items[takeIndex++];
                count--;
                if (takeIndex == items.length) {
                    takeIndex = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                core.release();
                notFull.release();
            }
            return x;
        }
    }
}
