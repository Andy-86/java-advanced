package tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo2 {

    public static void main(String[] args) {
        final int moduleCount = 20;
        CountDownLatch latch = new CountDownLatch(moduleCount);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < moduleCount; i++) {
            final int num = i;
            executor.execute(() -> {
                try {
                    doWork(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==>所有程序员完成任务，项目顺利上线！");
    }

    public static void doWork(int num) throws InterruptedException {
        Thread.sleep(100);
        System.out.println(String.format("程序员[%d]完成任务。。。", num));
        Thread.sleep(100);
    }
}
