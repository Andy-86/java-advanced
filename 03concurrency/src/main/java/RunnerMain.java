public class RunnerMain {
    public static void main(String[] args) throws InterruptedException {
        Runner1 runner1 = new Runner1();
        Thread t1 = new Thread(runner1);
        t1.start();

        Runner2 runner2 = new Runner2();
        Thread t2 = new Thread(runner2);
        t2.start();
        t2.interrupt();
    }
}
