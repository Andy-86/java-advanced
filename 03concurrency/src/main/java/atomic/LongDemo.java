package atomic;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongDemo {
    public static void main(String[] args) throws InterruptedException {
        final AtomicLong atomicLong = new AtomicLong();
        final LongAdder longAdder = new LongAdder();

        for (int i = 0; i < 100; i++) {
            ForkJoinPool.commonPool().execute(() -> {
                for (int j = 0; j < 10000; j++) {
                    atomicLong.incrementAndGet();
                    longAdder.increment();
                }
            });
        }

        Thread.sleep(200);
        System.out.println("atomicLong=" + atomicLong.get());
        System.out.println("longAdder=" + longAdder.sum());
    }
}
