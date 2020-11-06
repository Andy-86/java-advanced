package atomic;

public class SyncCounter {
    private int count;

    public synchronized int add() {
        return count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
