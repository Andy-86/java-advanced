package threadpool;

import java.util.concurrent.*;

public class ExecutorServiceDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newScheduledThreadPool(16);
        Future<String> f = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "call";
            }
        });
        f.cancel(true);
        System.out.println(f.isCancelled());
        System.out.println(f.get());
        executorService.shutdown();
    }
}
