package tool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("回调>>" + Thread.currentThread().getName());
            System.out.println("所有线程就绪，干");
        });

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            new Thread(new ReadNum(i, cyclicBarrier)).start();
        }
        executorService.shutdown();
    }

    static class ReadNum implements Runnable {
        private final int id;
        private final CyclicBarrier barrier;

        public ReadNum(int id, CyclicBarrier barrier) {
            this.id = id;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("id:" + id + "," + Thread.currentThread().getName());
                barrier.await();
                System.out.println("线程组任务" + id + "结束，其他任务继续");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
