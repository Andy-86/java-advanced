public class Runner2 implements Runnable {
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("runner1 " + i);
        }

        boolean isInterrupted1 = Thread.currentThread().isInterrupted();
        System.out.println("isInterrupted1 : " + isInterrupted1);

        boolean isInterrupted2 = Thread.interrupted();
        System.out.println("isInterrupted2 : " + isInterrupted2);

        boolean isInterrupted3 = Thread.currentThread().isInterrupted();
        System.out.println("isInterrupted3 : " + isInterrupted3);
    }
}
