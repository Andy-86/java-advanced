package lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock2 {
    private final Map<String, String> map = new HashMap<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public String get(String key) {
        String value;
        try {
            System.out.println("1.首先开启读锁去缓存中取数据");
            rwLock.readLock().lock();
            value = map.get(key);
            if (value != null) {
                return value;
            }
            System.out.println("2.数据不存在，则释放读锁，开启写锁");
            rwLock.readLock().unlock();
            rwLock.writeLock().lock();
            try {
                value = map.get(key);
                if (value != null) {
                    return value;
                }
                value = key;
                map.put(key, value);
            } finally {
                System.out.println("3.释放写锁");
                rwLock.writeLock().unlock();
            }
            System.out.println("4.开启读锁");
            rwLock.readLock().lock();
        } finally {
            System.out.println("5.释放读锁");
            rwLock.readLock().unlock();
        }
        return value;
    }

    public static void main(String[] args) {
        ReadWriteLock2 writeLock2 = new ReadWriteLock2();
        System.out.println(writeLock2.get("sky"));
    }
}
