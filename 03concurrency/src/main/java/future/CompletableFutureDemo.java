package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> " Hello").thenApplyAsync(e -> e + "World");
        System.out.println(completableFuture1.get());

        CompletableFuture.supplyAsync(() -> "sky").thenAcceptAsync(System.out::println);

        String result = CompletableFuture
                .supplyAsync(() -> "moon")
                .thenCombine(CompletableFuture.supplyAsync(() -> "sky"), (a, b) -> a + b).join();
        System.out.println(result);

        CompletableFuture
                .supplyAsync(() -> "java is the best language")
                .thenCompose(e -> CompletableFuture.supplyAsync(e::toUpperCase))
                .thenApply(String::toLowerCase)
                .thenAccept(e -> System.out.println(e + ".")).join();

        // 竞争
        String result3 = CompletableFuture.supplyAsync(() -> {
            LockSupport.parkNanos(1000000000);
            System.out.println("Hi,lucy");
            return "Hi,lucy";
        }).applyToEitherAsync(CompletableFuture.supplyAsync(() -> {
            LockSupport.parkNanos(2000000000);
            System.out.println("Hello,tony");
            return "Hello,tony";
        }), r -> "final :" + r).join();
        System.out.println(result3);

        // 异常
        String result4 = CompletableFuture.supplyAsync(() -> {
            System.out.println("test exception");
            if (true) {
                throw new RuntimeException("exception test!");
            }
            return "Hi";
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return "catch exception";
        }).join();

        System.out.println(result4);
    }
}
