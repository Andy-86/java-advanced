package atomic;

import java.util.concurrent.ForkJoinPool;

public class AtomicCounterMain {
    public static void main(String[] args) throws InterruptedException {
//        testAtomicCounter();
        testSyncCounter();
    }

    private static void testAtomicCounter() throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();

        for (int i = 0; i < 100; i++) {
            ForkJoinPool.commonPool().execute(() -> {
                for (int j = 0; j < 10000; j++) {
                    counter.add();
                }
            });
        }
        Thread.sleep(200);
        System.out.println(counter.getCount());
    }

    private static void testSyncCounter() throws InterruptedException {
        SyncCounter counter = new SyncCounter();

        for (int i = 0; i < 100; i++) {
            ForkJoinPool.commonPool().execute(() -> {
                for (int j = 0; j < 10000; j++) {
                    counter.add();
                }
            });
        }
        Thread.sleep(200);
        System.out.println(counter.getCount());
    }
}
