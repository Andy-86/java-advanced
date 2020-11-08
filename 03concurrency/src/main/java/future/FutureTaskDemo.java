package future;

import java.util.concurrent.*;

public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask1 = new FutureTask<>(() -> "sky");
        new Thread(futureTask1).start();
        System.out.println(futureTask1.get());

        FutureTask<String> futureTask2 = new FutureTask<>(() -> "moon");
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(futureTask2);
        System.out.println(futureTask2.get());

        Future future = executorService.submit(() -> "fly");
        System.out.println(future.get());
        executorService.shutdown();
    }
}
