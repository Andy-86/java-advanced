package tool;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new ReadNum(i, latch)).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行完毕");
    }

    static class ReadNum implements Runnable {
        private final int id;
        private final CountDownLatch latch;

        public ReadNum(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println("id:" + id + "," + Thread.currentThread().getName());
                System.out.println("线程组任务" + id + "结束，其他任务继续");
            } finally {
                latch.countDown();
            }
        }
    }
}
