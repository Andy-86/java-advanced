package collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        List<Integer> list = new LinkedList<>();
//        List<Integer> list = new Vector<>();
        List<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }

        T1 t1 = new T1(list);
        T2 t2 = new T2(list);
        t1.start();
        t2.start();
    }

    static class T1 extends Thread {
        List<Integer> list;

        public T1(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            for (Integer item : list) {
            }
        }
    }

    static class T2 extends Thread {
        List<Integer> list;

        public T2(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            for (int i = 0; i < list.size(); i++) {
                list.remove(i);
            }
        }
    }
}
