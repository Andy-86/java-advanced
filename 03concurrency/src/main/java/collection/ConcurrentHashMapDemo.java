package collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        Map<String, AtomicInteger> map = new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(2);
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                AtomicInteger oldValue = map.get("a");
                if (oldValue == null) {
                    AtomicInteger zero = new AtomicInteger(0);
//                    oldValue = map.putIfAbsent("a", zero);
                    oldValue = map.put("a", zero);
                    if (oldValue == null) {
                        oldValue = zero;
                    }
                }
                oldValue.incrementAndGet();
            }
            latch.countDown();
        };
        new Thread(task).start();
        new Thread(task).start();
        try {
            latch.await();
            System.out.println(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
