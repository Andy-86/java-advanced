package collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyOnWriteArrayListDemo2 {
    private static final int THREAD_POOL_MAX_NUM = 10;
    private static final List<String> list = new CopyOnWriteArrayList<>();
//    private static final List<String> list = new ArrayList<>();
//    private static final List<String> list = new Vector<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < THREAD_POOL_MAX_NUM; i++) {
            executorService.execute(new Reader(list));
            executorService.execute(new Writer(list, i));
        }
        executorService.shutdown();
    }

    private static void initData() {
        for (int i = 0; i <= THREAD_POOL_MAX_NUM; i++) {
            list.add("...... Line " + (i + 1) + " ......");
        }
    }

    static class Reader implements Runnable {
        private final List<String> list;

        public Reader(List<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            for (String s : list) {
                System.out.println(Thread.currentThread().getName() + " : " + s);
            }
        }
    }

    static class Writer implements Runnable {
        private final List<String> list;
        private final int index;

        public Writer(List<String> list, int index) {
            this.list = list;
            this.index = index;
        }

        @Override
        public void run() {
            this.list.add("...... add " + index + " ......");
        }
    }
}
