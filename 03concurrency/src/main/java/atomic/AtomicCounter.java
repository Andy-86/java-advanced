package atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger();

    public void add() {
        count.incrementAndGet();
    }

    public AtomicInteger getCount() {
        return count;
    }
}
