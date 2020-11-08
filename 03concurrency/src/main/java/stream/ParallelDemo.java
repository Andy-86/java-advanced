package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelDemo {

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        IntStream.range(1, 10000).forEach(integers::add);
        List<Long> longs = integers.stream().parallel().map(Long::valueOf).sorted().collect(Collectors.toList());
        BlockingQueue<Long> queue = new LinkedBlockingQueue<>(10000);
        longs.forEach(e -> {
            try {
                queue.put(e);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        System.out.println(queue.toString());
        queue.clear();
        longs.stream().parallel().forEach(e -> {
            try {
                queue.put(e);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        System.out.println(queue.toString());
        queue.clear();
        longs.parallelStream().forEach(e -> {
            try {
                queue.put(e);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        System.out.println(queue.toString());
    }
}
