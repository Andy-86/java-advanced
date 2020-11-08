package tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo2 {
    public static void main(String[] args) {
        int threadNum = 20;
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNum; i++) {
            int num = i;
            executor.execute(() -> {
                try {
                    semaphore.acquire(3);
                    test(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(3);
                }
            });
        }
        executor.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        System.out.println("id:" + threadNum + "," + Thread.currentThread().getName());
        Thread.sleep(1000);
    }
}
